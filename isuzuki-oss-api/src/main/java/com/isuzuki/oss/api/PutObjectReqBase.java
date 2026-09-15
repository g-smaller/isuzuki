package com.isuzuki.oss.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PutObjectReqBase<T extends PutObjectReqBase<T>> {

    private String bucketName;
    private String objectKey;
    private boolean enablePresigned = true;
    private String contentType;
    private String contentMD5;
    private Map<String, String> headers = Collections.EMPTY_MAP;
    private Map<String, String> meta = Collections.EMPTY_MAP;

    public String getBucketName() {
        return bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public boolean isEnablePresigned() {
        return enablePresigned;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentMD5() {
        return contentMD5;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public T bucketName(String bucketName) {
        this.bucketName = bucketName;
        return (T) this;
    }

    public T objectKey(String objectKey) {
        this.objectKey = objectKey;
        return (T) this;
    }

    public T enablePresigned(boolean enablePresigned) {
        this.enablePresigned = enablePresigned;
        return (T) this;
    }

    public T contentType(String contentType) {
        this.contentType = contentType;
        return (T) this;
    }

    public T contentMD5(String contentMD5) {
        this.contentMD5 = contentMD5;
        return (T) this;
    }

    public T meta(Map<String, String> meta) {
        this.meta = meta;
        return (T) this;
    }

    public T addMeta(String key, String value) {
        if (Objects.isNull(key)) {
            return (T) this;
        }
        if (meta.isEmpty()) {
            meta = new HashMap<>();
        }
        meta.put(key, value);
        return (T) this;
    }

    public T headers(Map<String, String> headers) {
        if (headers != null && headers.size() > 0) {
            this.headers = headers;
        }
        return (T) this;
    }

    public T addHeader(String key, String value) {
        if (Objects.isNull(key)) {
            return (T) this;
        }
        if (headers.isEmpty()) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
        return (T) this;
    }

}
