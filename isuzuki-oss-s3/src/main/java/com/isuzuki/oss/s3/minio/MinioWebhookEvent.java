package com.isuzuki.oss.s3.minio;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

/**
 * @author : Guo QuanYing (guoquanying@cmvalue.com)
 * @date : 2025/11/12
 * @description :
 */
public class MinioWebhookEvent {

    @JSONField(name = "EventName")
    private String eventName;
    @JSONField(name = "Key")
    private String key;
    @JSONField(name = "Records")
    private List<MinioWebhookEventRecord> records;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<MinioWebhookEventRecord> getRecords() {
        return records;
    }

    public void setRecords(List<MinioWebhookEventRecord> records) {
        this.records = records;
    }
}
