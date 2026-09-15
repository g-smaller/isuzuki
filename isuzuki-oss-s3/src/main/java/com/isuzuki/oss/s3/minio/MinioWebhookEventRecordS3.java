package com.isuzuki.oss.s3.minio;

/**
 * @author : Guo QuanYing (guoquanying@cmvalue.com)
 * @date : 2025/11/12
 * @description :
 */
public class MinioWebhookEventRecordS3 {
    private String s3SchemaVersion;
    private String configurationId;
    private MinioWebhookEventRecordS3Bucket bucket;
    private MinioWebhookEventRecordS3Object object;

    public String getS3SchemaVersion() {
        return s3SchemaVersion;
    }

    public void setS3SchemaVersion(String s3SchemaVersion) {
        this.s3SchemaVersion = s3SchemaVersion;
    }

    public String getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
    }

    public MinioWebhookEventRecordS3Bucket getBucket() {
        return bucket;
    }

    public void setBucket(MinioWebhookEventRecordS3Bucket bucket) {
        this.bucket = bucket;
    }

    public MinioWebhookEventRecordS3Object getObject() {
        return object;
    }

    public void setObject(MinioWebhookEventRecordS3Object object) {
        this.object = object;
    }
}
