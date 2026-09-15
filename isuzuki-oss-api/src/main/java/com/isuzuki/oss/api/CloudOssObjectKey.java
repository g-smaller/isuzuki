package com.isuzuki.oss.api;

public class CloudOssObjectKey {

    public static final CloudOssObjectKey EMPTY = new CloudOssObjectKey("", "");

    private String bucketName;
    private String objectKey;

    public CloudOssObjectKey(String bucketName, String objectKey) {
        this.bucketName = bucketName;
        this.objectKey = objectKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }
}
