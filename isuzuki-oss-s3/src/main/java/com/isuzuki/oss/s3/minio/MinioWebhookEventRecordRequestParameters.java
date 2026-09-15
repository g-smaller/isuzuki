package com.isuzuki.oss.s3.minio;

/**
 * @author : Guo QuanYing (guoquanying@cmvalue.com)
 * @date : 2025/11/12
 * @description :
 */
public class MinioWebhookEventRecordRequestParameters {
    private String principalId;
    private String region;
    private String sourceIPAddress;

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSourceIPAddress() {
        return sourceIPAddress;
    }

    public void setSourceIPAddress(String sourceIPAddress) {
        this.sourceIPAddress = sourceIPAddress;
    }
}
