package com.isuzuki.oss.aliyun;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import com.isuzuki.oss.api.*;
import com.isuzuki.oss.api.PutObjectRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class AliYunCloudOssClientTemplate extends AbstractCloudOssClientTemplate<OSS> implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(AliYunCloudOssClientTemplate.class);

    private final AliYunCloudOssProperties aliYunCloudOssProperties;

    private static final String COMPLETED_STYLE_REG = "^[a-zA-Z0-9]+/.*$";
    private static final Pattern COMPLETED_STYLE_PATTERN = Pattern.compile(COMPLETED_STYLE_REG);

    public AliYunCloudOssClientTemplate(CloudOssProperties cloudOssProperties,
                                        AliYunCloudOssProperties aliYunCloudOssProperties) {
        super(cloudOssProperties);
        this.aliYunCloudOssProperties = aliYunCloudOssProperties;
    }

    @Override
    public UploadObjectResponse uploadObject(UploadObjectRequest request) throws Exception {
        ObjectMetadata objectMetadata = buildAccessControllerMeta(request);
        return uploadObject(request, (bucketName, objectKey) -> {

            UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, objectKey);
            uploadFileRequest.setUploadFile(request.getFilename());
            uploadFileRequest.setObjectMetadata(objectMetadata);
            OSS ossClient = getClient(bucketName).get();

            try {
                UploadFileResult fileResult = ossClient.uploadFile(uploadFileRequest);
                CompleteMultipartUploadResult multipartUploadResult = fileResult.getMultipartUploadResult();

                PutObjectResult objectWriteResponse = new PutObjectResult();
                objectWriteResponse.setETag(multipartUploadResult.getETag());
                objectWriteResponse.setVersionId(multipartUploadResult.getVersionId());
                objectWriteResponse.setRequestId(multipartUploadResult.getRequestId());
                objectWriteResponse.setClientCRC(multipartUploadResult.getClientCRC());
                objectWriteResponse.setServerCRC(multipartUploadResult.getServerCRC());
                objectWriteResponse.setCallbackResponseBody(multipartUploadResult.getCallbackResponseBody());
                return objectWriteResponse;
            }catch (Throwable e){
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public UploadObjectResponse putObject(PutObjectRequest request) throws Exception {
        ObjectMetadata objectMetadata = buildAccessControllerMeta(request);
        return uploadObject(request, (bucketName, objectKey) -> {

            com.aliyun.oss.model.PutObjectRequest putObjectRequest = new com.aliyun.oss.model.PutObjectRequest(bucketName, objectKey, request.getInputStream());
            putObjectRequest.setMetadata(objectMetadata);
            OSS ossClient = getClient(bucketName).get();
            PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
            return putObjectResult;
        });
    }

    private <T extends PutObjectReqBase<T>> UploadObjectResponse uploadObject(PutObjectReqBase<T> request,
                                                                              UploadFunction<String, String, PutObjectResult> function) throws Exception {
        String metaKeyAcl = GetObjectPolicyCondition.ACL;
        String metaValueAcl = (request.isEnablePresigned() ? PRI_ACL : PUB_ACL);

        CloudOssBucketProperties bucketProperties = StringUtils.isBlank(request.getBucketName()) ? getBucketPropertiesByAcl(metaValueAcl) :
                getBucketProperties(request.getBucketName(), request.getObjectKey());

        String bucketName = bucketProperties.getBucketName();

        String objectKey = request.getObjectKey();

        log.info("AliYun PutObject: {}, {}, {}", bucketName, objectKey, objectKey);

        PutObjectResult objectWriteResponse = function.apply(bucketName, objectKey);

        log.debug("Upload file stream info, requestId:{},", objectWriteResponse.getRequestId());

        UploadObjectResponse response = new UploadObjectResponse();
        response.bucketName(bucketName)
                .objectKey(objectKey)
                .fileUrl(generatePresignedUrl(bucketProperties.getBucketName(), objectKey))
                .requestId(objectWriteResponse.getRequestId())
                .etag(objectWriteResponse.getETag())
                .versionId(objectWriteResponse.getVersionId());

        return response;
    }

    private <T extends PutObjectReqBase<T>> ObjectMetadata buildAccessControllerMeta(PutObjectReqBase<T> request) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        metadata.setObjectAcl(request.isEnablePresigned() ? CannedAccessControlList.Private : CannedAccessControlList.PublicRead);
        if (StringUtils.isNotBlank(request.getContentType())) {
            metadata.setContentType(request.getContentType());
        }
        if (StringUtils.isNotBlank(request.getContentMD5())) {
            metadata.setContentMD5(request.getContentMD5());
        }
        return metadata;
    }

    @Override
    public String generatePresignedUrl(GetPresignedObjectUrlRequest request) {

        String _bucketName = request.getBucketName();
        String _objectKey = request.getObjectKey();

        CloudOssBucketProperties bucketProperties = getBucketProperties(_bucketName, _objectKey);

        String preivewUrl = CloudOssUtils.appendFullPath(bucketProperties.getCustomEndpoint(), _objectKey);

        if (bucketProperties.isEnablePresigned()) {
            long signatureExpirationMillis = request.getExpireMillsSeconds();
            signatureExpirationMillis = signatureExpirationMillis <= 0L ? bucketProperties.getPresignedExpireTime() : signatureExpirationMillis;
            Date expiration = new Date(Instant.now().toEpochMilli() + signatureExpirationMillis);
            GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(_bucketName, _objectKey, HttpMethod.GET);
            presignedUrlRequest.setExpiration(expiration);
            String process = getProcess(request.getStyleProcess(), _objectKey);
            if (StringUtils.isNotBlank(process)) {
                presignedUrlRequest.setProcess(process);
            }

            OSS ossClient = getClient(_bucketName).get();
            URL signedUrl = ossClient.generatePresignedUrl(presignedUrlRequest);
            String signatureQuery = signedUrl.getQuery();

            preivewUrl = preivewUrl + "?" + signatureQuery;
        }
        return preivewUrl;
    }

    private String getProcess(String style, String objectKey) {
        String process = "";
        if (StringUtils.isNotBlank(style)) {
            if (isCompletedStyle(style)) {
                process = style;
            } else {
                process = "style/" + style;
            }
        }

        if (StringUtils.isBlank(process)) {

        }

        return process;
    }

    /**
     * 判断是否是完整的样式参数格式
     *
     * @param styleProcess 样式参数
     * @return 是否是完整的样式参数格式
     */
    private boolean isCompletedStyle(String styleProcess) {
        return COMPLETED_STYLE_PATTERN.matcher(styleProcess).matches();
    }

    public String generatePresignedUrl(String bucketName, String objectKey) {
        return generatePresignedUrl(GetPresignedObjectUrlRequest.builder().bucketName(bucketName).objectKey(objectKey));
    }

    /**
     * @href PostObject {https://help.aliyun.com/zh/oss/developer-reference/postobject?spm=a2c4g.11186623.0.0.11043c02QmTBzt}
     * @href 服务端签名直传 {https://help.aliyun.com/zh/oss/user-guide/obtain-signature-information-from-the-server-and-upload-data-to-oss?spm=a2c4g.11186623.help-menu-31815.d_0_3_2_0_0.332c1ea9E62kLE&scm=20140722.H_31926._.OR_help-T_cn~zh-V_1}
     * @param request
     * @return
     */
    @Override
    public GetUploadObjectCredential generateCredential(GetUploadObjectCredentialRequest request) {
        int success_action_status = 200;

        String metaKeyAcl = GetObjectPolicyCondition.ACL;
        String metaValueAcl = (Boolean.TRUE.equals(request.getPrivate()) ? PRI_ACL : PUB_ACL);

        CloudOssBucketProperties bucketProperties = getBucketPropertiesByAcl(metaValueAcl);
        //
        String newObjectKey = request.getObjectKey();
        String bucketName = bucketProperties.getBucketName();

        Map<String, String> metas = new HashMap<>();
        if (request.getCondition() != null) {
            Set<Map.Entry<String, String>> entries = request.getCondition().entrySet();
            for (Map.Entry<String, String> entry : entries) {
                String value = entry.getValue();
                if (StringUtils.isNotBlank(value)) {
                    String metaKey = appendMeta(entry.getKey());
                    metas.put(metaKey, value);
                }
            }
        }

        metas.put(appendMeta(metaKeyAcl), metaValueAcl);

        Map<String, String> presignedPostFormData = AliYunClientUtils.getPresignedPostFormData(bucketProperties, newObjectKey, request.getLowerLimit(), metas);

        GetUploadObjectCredential credential = GetUploadObjectCredential.builder()
                .method("POST")
                .url(bucketProperties.getCustomEndpoint())
                .objectKey(newObjectKey)
                .successStatusCode(success_action_status)
                .fileUrl(generatePresignedUrl(bucketName, newObjectKey))
                .putMetaAll(presignedPostFormData)
                .putMetaAll(metas)
                .putMeta("key", newObjectKey)
                .putMeta("callback", callback(request))
                .putMeta("OSSAccessKeyId", bucketProperties.getAccessKey())
                .putMeta("success_action_status", "" +  success_action_status)
                .putMeta("x-oss-object-acl", metaValueAcl)
                ;
        return credential;
    }

    private String appendMeta(String field) {
        return "x-oss-meta-" + field;
    }

    private String callback(GetUploadObjectCredentialRequest request) {
        String callbackUrl = aliYunCloudOssProperties.getCallbackUrl();
        if (StringUtils.isBlank(callbackUrl)) {
            return "";
        }
        AliYunUploadCallback callback = AliYunUploadCallback.json()
                .callbackUrl(callbackUrl);

        if (request.getCondition() != null) {
            Set<Map.Entry<String, String>> entries = request.getCondition().entrySet();
            for (Map.Entry<String, String> entry : entries) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (StringUtils.isNotBlank(value)) {
                    callback.put(key, value);
                }
            }
        }
        return callback.getBase64CallbackBody();
    }

    @Override
    protected Supplier<OSS> createClient(CloudOssBucketProperties bucketProperties) {
        OSS ossClient = new OSSClientBuilder().build(bucketProperties.getEndpoint(), bucketProperties.getAccessKey(), bucketProperties.getSecretKey());
        return () -> ossClient;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        load();
    }

    @FunctionalInterface
    public interface UploadFunction<B, O, R> {
        R apply(B b, O o) throws Exception;
    }
}
