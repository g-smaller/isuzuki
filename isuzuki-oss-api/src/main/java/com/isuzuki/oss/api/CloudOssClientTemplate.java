package com.isuzuki.oss.api;

public interface CloudOssClientTemplate {

    /**
     * 直接文件上传
     * @param request
     * @return
     * @throws Exception
     */
    UploadObjectResponse uploadObject(UploadObjectRequest request) throws Exception;

    /**
     * 流上传
     * @param request
     * @return
     * @throws Exception
     */
    UploadObjectResponse putObject(PutObjectRequest request) throws Exception;

    String generatePresignedUrl(GetPresignedObjectUrlRequest request);

    default String generatePresignedUrl(String bucketName, String objectKey) {
        return generatePresignedUrl(GetPresignedObjectUrlRequest.builder().bucketName(bucketName).objectKey(objectKey));
    }

    default String generatePresignedUrl(String objectKey) {
        return generatePresignedUrl("", objectKey);
    }
}
