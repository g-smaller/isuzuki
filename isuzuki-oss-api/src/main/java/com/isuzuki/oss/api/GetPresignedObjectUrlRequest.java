package com.isuzuki.oss.api;

public class GetPresignedObjectUrlRequest {

    private String bucketName;
    private String objectKey;
    private int expireMillsSeconds;
    private String styleProcess;

    public String getBucketName() {
        return bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public int getExpireMillsSeconds() {
        return expireMillsSeconds;
    }

    public String getStyleProcess() {
        return styleProcess;
    }

    public static GetPresignedObjectUrlRequest builder() {
        return new GetPresignedObjectUrlRequest();
    }

    public GetPresignedObjectUrlRequest bucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public GetPresignedObjectUrlRequest bucketName(String bucketName, String objectKey) {
        this.bucketName = bucketName;
        this.objectKey = objectKey;
        return this;
    }

    public GetPresignedObjectUrlRequest objectKey(String objectKey) {
        this.objectKey = objectKey;
        return this;
    }

    public GetPresignedObjectUrlRequest expireMillsSeconds(int expireMillsSeconds) {
        this.expireMillsSeconds = expireMillsSeconds;
        return this;
    }

    public GetPresignedObjectUrlRequest styleProcess(String styleProcess) {
        this.styleProcess = styleProcess;
        return this;
    }
}
