package com.isuzuki.oss.s3.minio;

/**
 * @author : 
 * @date : 2025/11/12
 * @description :
 */
public class MinioWebhookEventRecordS3BucketOwnerIdentity {

    private String principalId;

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }
}
