package com.isuzuki.oss.s3.minio;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 * @author : Guo QuanYing (guoquanying@cmvalue.com)
 * @date : 2025/11/12
 * @description :
 */
public class MinioWebhookEventRecordS3Object {

    private String key;
    private Long size;
    @JSONField(name = "eTag")
    private String etag;
    private String contentType;
    private String sequencer;
    private MinioWebhookEventRecordS3ObjectUserMetadata userMetadata;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSequencer() {
        return sequencer;
    }

    public void setSequencer(String sequencer) {
        this.sequencer = sequencer;
    }

    public MinioWebhookEventRecordS3ObjectUserMetadata getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(MinioWebhookEventRecordS3ObjectUserMetadata userMetadata) {
        this.userMetadata = userMetadata;
    }
}
