package com.isuzuki.oss.s3;

import com.isuzuki.oss.api.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Supplier;

public class S3CloudOssClientTemplate extends AbstractCloudOssClientTemplate<S3Client> implements InitializingBean {

    private Map<String, S3Presigner> PRESIGNER_MAP = new HashMap<>();

    public S3CloudOssClientTemplate(CloudOssProperties cloudOssProperties) {
        super(cloudOssProperties);
    }

    @Override
    public UploadObjectResponse uploadObject(UploadObjectRequest request) throws Exception {
        return null;
    }

    @Override
    public UploadObjectResponse putObject(PutObjectRequest request) throws Exception {

        String metaKeyAcl = GetObjectPolicyCondition.ACL;
        String metaValueAcl = (request.isEnablePresigned() ? PRI_ACL : PUB_ACL);

        CloudOssBucketProperties bucketProperties = StringUtils.isBlank(request.getBucketName()) ? getBucketPropertiesByAcl(metaValueAcl) :
                getBucketProperties(request.getBucketName(), request.getObjectKey());

        String bucketName = bucketProperties.getBucketName();
        String objectKey = CloudOssUtils.appendPath(bucketProperties.getCustomContext(), request.getObjectKey());

        software.amazon.awssdk.services.s3.model.PutObjectRequest.Builder requestBuilder = software.amazon.awssdk.services.s3.model.PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .contentType(request.getContentType())
                .acl(metaKeyAcl)
                .metadata(appendMetas(request.getMeta()));

        String contentType = request.getContentType();
        if (StringUtils.isBlank(contentType)) {
            contentType = URLConnection.guessContentTypeFromName(objectKey);
            if (StringUtils.isNotBlank(contentType)) {
                requestBuilder.contentType(contentType);
            }
        }
        RequestBody requestBody = null;
        if (request.getObjectSize() > 0) {
            requestBody = RequestBody.fromInputStream(request.getInputStream(), request.getObjectSize());
        }else {
            requestBody = RequestBody.fromContentProvider(request::getInputStream, contentType);
        }
        S3Client s3Client = getClient(bucketProperties.getBucketName()).get();

        software.amazon.awssdk.services.s3.model.PutObjectResponse response = s3Client.putObject(requestBuilder.build(), requestBody);

        UploadObjectResponse uploadObjectResponse = new UploadObjectResponse();
        uploadObjectResponse.bucketName(bucketName)
                .objectKey(objectKey)
                .fileUrl(generatePresignedUrl(bucketProperties.getBucketName(), objectKey))
                .requestId(response.responseMetadata().requestId())
                .etag(response.eTag())
                .versionId(response.versionId());

        return uploadObjectResponse;

    }

    private String generatePresignedUrl(String bucketName, String objectKey) {
        return generatePresignedUrl(GetPresignedObjectUrlRequest.builder().bucketName(bucketName).objectKey(objectKey));
    }

    @Override
    public String generatePresignedUrl(GetPresignedObjectUrlRequest request) {

        CloudOssObjectKey cloudOssObjectKey = resolveObjectKey(request.getBucketName(), request.getObjectKey());

        CloudOssBucketProperties bucketProperties = getBucketProperties(cloudOssObjectKey.getBucketName(), cloudOssObjectKey.getObjectKey());

        String bucketName = bucketProperties.getBucketName();
        String objectKey = cloudOssObjectKey.getObjectKey();

        String subPath = CloudOssUtils.appendPath(bucketName, objectKey);
        if (bucketProperties.isEnablePresigned()) {

            long signatureExpirationMillis = request.getExpireMillsSeconds() <= 0L ? bucketProperties.getUploadExpireTime() : request.getExpireMillsSeconds();

            S3Presigner presigner = getPresigner(bucketName);

            String bucket = bucketName;
            String key =  objectKey;
            URL signedUrl = presigner.presignGetObject(builder -> {
                builder.signatureDuration(Duration.ofMillis(signatureExpirationMillis));
                builder.getObjectRequest(object -> object.bucket(bucket).key(key));
            }).url();

            subPath = signedUrl.getPath() + "?" + signedUrl.getQuery();
        }
        return CloudOssUtils.appendFullPath(bucketProperties.getCustomEndpoint(), subPath);
    }

    public CloudOssObjectKey resolveObjectKey(String bucketName, String objectKey) {
        String _bucketName = bucketName;
        String _objectKey = objectKey;
        if (StringUtils.isBlank(_bucketName)) {
            CloudOssObjectKey cloudOssObjectKey = CloudOssUtils.parseObjectKey(_objectKey);
            _bucketName = cloudOssObjectKey.getBucketName();
            _objectKey = cloudOssObjectKey.getObjectKey();
        }

        _objectKey = CloudOssUtils.resolveObjectKey(_objectKey);
        return new CloudOssObjectKey(_bucketName, _objectKey);
    }

    @Override
    public GetUploadObjectCredential generateCredential(GetUploadObjectCredentialRequest request) {

        String metaKeyAcl = GetObjectPolicyCondition.ACL;
        String metaValueAcl = (Boolean.TRUE.equals(request.getPrivate()) ? PRI_ACL : PUB_ACL);

        CloudOssBucketProperties bucketProperties = getBucketPropertiesByAcl(metaValueAcl);

        String url = bucketProperties.getCustomEndpoint() + "/" + bucketProperties.getBucketName();
        String objectKey = CloudOssUtils.appendPath(bucketProperties.getCustomContext(), request.getObjectKey());

        int success_action_status = 204;
        GetUploadObjectCredential credential = GetUploadObjectCredential.builder()
                .method("POST")
                .url(url)
                .objectKey(objectKey)
                .successStatusCode(success_action_status)
                .fileUrl(generatePresignedUrl(bucketProperties.getBucketName(), objectKey))
                .putHeader(appendHeader("acl"), metaValueAcl)
                .putMeta(metaKeyAcl, metaValueAcl)
                .putMeta("key", objectKey)
                .putMeta("success_action_status", "" + success_action_status);

        S3PolicyContext policy = S3PolicyContext.builder(bucketProperties.getBucketName(), ZonedDateTime.now().plus(bucketProperties.getUploadExpireTime(), ChronoUnit.MILLIS))
                .accessKey(bucketProperties.getAccessKey())
                .secretKey(bucketProperties.getSecretKey())
                .region(bucketProperties.getRegion())
                .addEqualsCondition(metaKeyAcl, metaValueAcl)
                .addEqualsCondition("key", objectKey)
                .addEqualsCondition("success_action_status", "" + success_action_status)
                .addContentLengthRangeCondition(request.getLowerLimit(), request.getUpperLimit());

        Map<String, String> condition = request.getCondition();
        if (Objects.nonNull(condition)) {
            Set<Map.Entry<String, String>> entries = condition.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                String value = entry.getValue();
                if (StringUtils.isNotBlank(value)) {
                    String metaKey = appendMeta(entry.getKey());
                    policy.addStartsWithCondition(metaKey, value);

                    credential.putMeta(metaKey, value);
                }
            }
        }

        Map<String, String> presignedPostFormData = policy.formData();
        credential.putMetaAll(presignedPostFormData);

        return credential;
    }

    private Map<String, String> appendMetas(Map<String, String> sourceMetas) {
        if (sourceMetas == null || sourceMetas.isEmpty()){
            return Collections.EMPTY_MAP;
        }
        Map<String, String> targetMetas = new HashMap<>();
        Set<Map.Entry<String, String>> entries = sourceMetas.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            targetMetas.put(appendMeta(entry.getKey()), entry.getValue());
        }
        return targetMetas;
    }

    private String appendMeta(String field) {
        return "x-amz-meta-" + field;
    }

    private String appendHeader(String field) {
        return "x-amz-" + field;
    }

    private S3Presigner getPresigner(String bucketName) {
        return PRESIGNER_MAP.get(bucketName);
    }

    @Override
    protected Supplier<S3Client> createClient(CloudOssBucketProperties bucketProperties) {
        Region region = Region.of(bucketProperties.getRegion());

        AwsBasicCredentials credentials = AwsBasicCredentials.create(bucketProperties.getAccessKey(),
                bucketProperties.getSecretKey());

        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);
        S3Configuration serviceConfiguration = S3Configuration.builder()
                .pathStyleAccessEnabled(bucketProperties.isPathStyleAccessEnabled())
                .checksumValidationEnabled(false)
                .build();

        S3Client s3Client = S3Client.builder()
                .endpointOverride(URI.create(bucketProperties.getEndpoint()))
                .region(region)
                .credentialsProvider(credentialsProvider)
                .serviceConfiguration(serviceConfiguration)
                .build();

        S3Presigner s3Presigner = S3Presigner.builder()
                .endpointOverride(URI.create(bucketProperties.getEndpoint()))
                .region(region)
                .credentialsProvider(credentialsProvider)
                .serviceConfiguration(serviceConfiguration)
                .build();

        PRESIGNER_MAP.put(bucketProperties.getBucketName(), s3Presigner);

        return () -> s3Client;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        load();
    }
}
