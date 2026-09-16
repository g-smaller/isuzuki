package com.isuzuki.oss.s3.minio;


/**
 * @author : 
 * @date : 2025/11/12
 * @description :
 */
public class MinioWebhookEventRecordS3Bucket {

    private String name;
    private String arn;
    private MinioWebhookEventRecordS3BucketOwnerIdentity ownerIdentity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public MinioWebhookEventRecordS3BucketOwnerIdentity getOwnerIdentity() {
        return ownerIdentity;
    }

    public void setOwnerIdentity(MinioWebhookEventRecordS3BucketOwnerIdentity ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
    }
}
