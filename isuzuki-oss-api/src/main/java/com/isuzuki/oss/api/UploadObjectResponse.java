package com.isuzuki.oss.api;

import java.util.HashMap;
import java.util.Map;

public class UploadObjectResponse {

    private String bucketName;
    private String objectKey;
    private String fileUrl;
    private String requestId;
    private String etag;
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

    public String getRequestId() {
        return requestId;
    }

    public String getEtag() {
        return etag;
    }

    public String getVersionId() {
        return versionId;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public UploadObjectResponse bucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public UploadObjectResponse objectKey(String objectKey) {
        this.objectKey = objectKey;
        return this;
    }

    public UploadObjectResponse fileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public UploadObjectResponse requestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public UploadObjectResponse etag(String etag) {
        this.etag = etag;
        return this;
    }

    public UploadObjectResponse versionId(String versionId) {
        this.versionId = versionId;
        return this;
    }

    public UploadObjectResponse headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public UploadObjectResponse addHeader(String key, String value) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
        return this;
    }
}
