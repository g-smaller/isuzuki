package com.isuzuki.oss.s3.minio;

/**
 * @author : 
 * @date : 2025/11/12
 * @description :
 */
public class MinioWebhookEventRecord {

    private String eventVersion;
    private String eventSource;
    private String awsRegion;
    private String eventTime;
    private String eventName;
    private MinioWebhookEventRecordUserIdentity userIdentity;
    private MinioWebhookEventRecordRequestParameters requestParameters;
    private MinioWebhookEventRecordResponseElement responseElements;
    private MinioWebhookEventRecordS3 s3;
    private MinioWebhookEventRecordSource source;

    public String getEventVersion() {
        return eventVersion;
    }

    public void setEventVersion(String eventVersion) {
        this.eventVersion = eventVersion;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public String getAwsRegion() {
        return awsRegion;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public MinioWebhookEventRecordUserIdentity getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(MinioWebhookEventRecordUserIdentity userIdentity) {
        this.userIdentity = userIdentity;
    }

    public MinioWebhookEventRecordRequestParameters getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(MinioWebhookEventRecordRequestParameters requestParameters) {
        this.requestParameters = requestParameters;
    }

    public MinioWebhookEventRecordResponseElement getResponseElements() {
        return responseElements;
    }

    public void setResponseElements(MinioWebhookEventRecordResponseElement responseElements) {
        this.responseElements = responseElements;
    }

    public MinioWebhookEventRecordS3 getS3() {
        return s3;
    }

    public void setS3(MinioWebhookEventRecordS3 s3) {
        this.s3 = s3;
    }

    public MinioWebhookEventRecordSource getSource() {
        return source;
    }

    public void setSource(MinioWebhookEventRecordSource source) {
        this.source = source;
    }
}
