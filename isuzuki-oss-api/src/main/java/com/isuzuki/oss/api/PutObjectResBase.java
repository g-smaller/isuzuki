package com.isuzuki.oss.api;

import java.util.HashMap;
import java.util.Map;

public class PutObjectResBase<T extends PutObjectResBase<T>> {

    private String bucketName;
    private String objectKey;
    private String fileUrl;
    private String md5;
    private String versionId;
    private Map<String, String> headers;

    public String getBucketName() {
        return bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getMd5() {
        return md5;
    }

    public String getVersionId() {
        return versionId;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public T bucketName(String bucketName) {
        this.bucketName = bucketName;
        return (T) this;
    }

    public T objectKey(String objectKey) {
        this.objectKey = objectKey;
        return (T) this;
    }

    public T fileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return (T) this;
    }

    public T md5(String md5) {
        this.md5 = md5;
        return (T) this;
    }

    public T versionId(String versionId) {
        this.versionId = versionId;
        return (T) this;
    }

    public T headers(Map<String, String> headers) {
        this.headers = headers;
        return (T) this;
    }

    public T addHeader(String key, String value) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
        return (T) this;
    }
}
