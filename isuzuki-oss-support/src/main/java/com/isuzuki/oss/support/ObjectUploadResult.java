package com.isuzuki.oss.support;

public class ObjectUploadResult {

    /**
     * 上传的请求ID
     */
    private String requestId;

    /**
     * 文件访问url
     */
    private String objectUrl;

    /**
     * 文件目录
     */
    private String objectKey;

    private String bucketName;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getObjectUrl() {
        return objectUrl;
    }

    public void setObjectUrl(String objectUrl) {
        this.objectUrl = objectUrl;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
