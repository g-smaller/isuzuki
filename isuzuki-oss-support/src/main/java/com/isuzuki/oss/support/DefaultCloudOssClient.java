package com.isuzuki.oss.support;

import com.isuzuki.oss.api.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DefaultCloudOssClient implements CloudOssClient {

    private Logger logger = LoggerFactory.getLogger(DefaultCloudOssClient.class);
    private Tika tika = new Tika();
    private CloudOssClientTemplate cloudOssClientTemplate;

    public DefaultCloudOssClient(CloudOssClientTemplate cloudOssClientTemplate) {
        this.cloudOssClientTemplate = cloudOssClientTemplate;
    }

    @Override
    public ObjectUploadResult uploadFile(MultipartFile file, String objectKey, boolean privateFile) {
        return uploadFile(file, "", objectKey, privateFile);
    }

    @Override
    public ObjectUploadResult uploadFile(MultipartFile file, String bucketName, String objectKey, boolean privateFile) {
        try {
            return uploadFile(bucketName, objectKey, privateFile, file.getOriginalFilename(), file.getInputStream(), file.getSize(), (putObject) -> {
                putObject.contentType(file.getContentType());
            });

        }catch (Exception e) {
            throw new UploadFileException("[MultipartFile] 文件上传失败,bucketName: " + bucketName + ",objectKey:" + objectKey, e);
        }
    }

    @Override
    public ObjectUploadResult uploadFile(String url, String objectKey, boolean privateFile) {
        return uploadFile(url, "", objectKey, privateFile);
    }

    @Override
    public ObjectUploadResult uploadFile(String url, String bucketName, String objectKey, boolean privateFile) {
        String filename = FilenameUtils.getName(url);
        int index = objectKey.lastIndexOf(".");
        if (index == -1) {
            String extension = FilenameUtils.getExtension(url);
            if (StringUtils.isNotBlank(extension)) {
                objectKey = objectKey + "." + extension;
            }
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try  {

            URL remoteUrl = new URL(url);
            connection = (HttpURLConnection) remoteUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);  // 连接超时 10s
            // connection.setReadTimeout(30000);     // 读取超时 30s
            connection.setInstanceFollowRedirects(true); // 允许重定向

            // 获取文件大小（用于设置 Content-Length，可选但推荐）
            long contentLength = connection.getContentLengthLong();
            String contentType = connection.getContentType();

            inputStream = connection.getInputStream();

            Map<String, List<String>> headerFields = connection.getHeaderFields();

            return uploadFile(bucketName, objectKey, privateFile, filename, inputStream, contentLength, (putObject) -> {
                putObject.contentType(contentType);

                if (headerFields != null) {
                    headerFields.forEach((key, values) -> {
                        putObject.addMeta("x-" + key, values == null || values.isEmpty() ? "" : values.get(0));
                    });
                }
            });
        } catch (Exception e) {
            throw new UploadFileException("[URL] 文件上传失败,bucketName: " + bucketName + ",objectKey:" + objectKey + ",file url:" + url, e);
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("[URL] 文件上传文件流上传失败!" + url, e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public ObjectUploadResult uploadFile(byte[] bytes, String objectKey, boolean privateFile) {
        return uploadFile(bytes, "", objectKey, privateFile);
    }

    @Override
    public ObjectUploadResult uploadFile(byte[] bytes, String bucketName, String objectKey, boolean privateFile) {
        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            return uploadFile(bucketName, objectKey, privateFile, "", inputStream, bytes.length, (putObject) -> {

            });
        }catch (Exception e) {
            throw new UploadFileException("[Byte] 文件上传失败,bucketName: " + bucketName + ",objectKey:" + objectKey, e);
        }
    }

    @Override
    public ObjectUploadResult uploadFile(File file, String objectKey, boolean privateFile) {
        return uploadFile(file, "", objectKey, privateFile);
    }

    @Override
    public ObjectUploadResult uploadFile(File file, String bucketName, String objectKey, boolean privateFile) {
        try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
            return uploadFile(bucketName, objectKey, privateFile, file.getName(), inputStream, file.length(), (putObject) -> {});
        }catch (Exception e) {
            throw new UploadFileException("[File] 文件上传失败,bucketName: " + bucketName + ",objectKey:" + objectKey, e);
        }
    }

    @Override
    public ObjectUploadResult uploadFile(InputStream inputStream, String objectKey, boolean privateFile) {
        return uploadFile(inputStream, "", objectKey, privateFile);
    }

    @Override
    public ObjectUploadResult uploadFile(InputStream inputStream, String bucketName, String objectKey, boolean privateFile) {
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return uploadFile(bytes, bucketName, objectKey, privateFile);
        }catch (Exception e) {
            throw new UploadFileException("[InputStream] 文件上传失败,bucketName: " + bucketName + ",objectKey:" + objectKey, e);
        }
    }

    private ObjectUploadResult uploadFile(String bucketName, String objectKey, boolean privateFile, String filename, InputStream inputStream,
                                          long length, Consumer<PutObjectRequest> consumer) throws Exception {

        PutObjectRequest put = commonObjectArgs()
                .bucketName(bucketName)
                .objectKey(objectKey)
                .inputStream(inputStream)
                .objectSize(length)
                .enablePresigned(privateFile)
                .contentMD5("")
                .addMeta(GetObjectPolicyCondition.META_FILE_NAME, filename);

        consumer.accept(put);

        String contentType = put.getContentType();
        if (StringUtils.isBlank(contentType)) {
            contentType = tika.detect(inputStream);
        }
        if (StringUtils.isBlank(contentType)) {
            contentType = URLConnection.guessContentTypeFromName(objectKey);
        }
        put.contentType(StringUtils.isBlank(contentType) ? "application/octet-stream" : contentType);

        UploadObjectResponse response = cloudOssClientTemplate.putObject(put);
        return convert(response);
    }

    private ObjectUploadResult convert(UploadObjectResponse object) {
        ObjectUploadResult result = new ObjectUploadResult();
        result.setRequestId(object.getVersionId());
        result.setObjectKey(object.getObjectKey());
        result.setObjectUrl(object.getFileUrl());
        return result;
    }

    private PutObjectRequest commonObjectArgs() {
        PutObjectRequest put = PutObjectRequest.builder()
                .addMeta(GetObjectPolicyCondition.META_FILE_Id, "")
                .addMeta(GetObjectPolicyCondition.META_TRACE_ID, "")
                .addMeta(GetObjectPolicyCondition.META_UPLOAD_TYPE, "SDK");
        return put;
    }

    @Override
    public String generateSignedUrl(ObjectSignedRequest request) {
        return cloudOssClientTemplate.generatePresignedUrl(GetPresignedObjectUrlRequest.builder()
                .bucketName(request.getBucketName(), request.getObjectKey())
                .expireMillsSeconds(request.getSignatureExpirationMillis()));
    }
}
