package com.isuzuki.oss.support;

public class ObjectSignedRequest {

    private String bucketName;
    private String objectKey;
    private int signatureExpirationMillis;

    public String getBucketName() {
        return bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public int getSignatureExpirationMillis() {
        return signatureExpirationMillis;
    }

    public ObjectSignedRequest bucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public ObjectSignedRequest objectKey(String objectKey) {
        this.objectKey = objectKey;
        return this;
    }

    public ObjectSignedRequest signatureExpirationMillis(int signatureExpirationMillis) {
        this.signatureExpirationMillis = signatureExpirationMillis;
        return this;
    }

    public static ObjectSignedRequest builder(String objectKey) {
        return new ObjectSignedRequest().objectKey(objectKey);
    }
}
