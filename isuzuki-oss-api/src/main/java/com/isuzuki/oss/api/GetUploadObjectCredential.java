package com.isuzuki.oss.api;

import java.util.HashMap;
import java.util.Map;

public class GetUploadObjectCredential {
    private String method;
    private String url;
    private String objectKey;
    private String fileUrl;
    private int successStatusCode = 200;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> metas = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public int getSuccessStatusCode() {
        return successStatusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getMetas() {
        return metas;
    }

    public static GetUploadObjectCredential builder() {
        return new GetUploadObjectCredential();
    }

    public GetUploadObjectCredential putMeta(String key, String value) {
        metas.put(key, value);
        return this;
    }

    public GetUploadObjectCredential putMetaAll(Map<String, String> metas) {
        if (metas != null && metas.size() > 0) {
            this.metas.putAll(metas);
        }
        return this;
    }

    public GetUploadObjectCredential putHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public GetUploadObjectCredential putHeaders(Map<String, String> headers) {
        if (headers != null && headers.size() > 0) {
            this.headers.putAll(headers);
        }
        return this;
    }

    public GetUploadObjectCredential method(String method) {
        this.method = method;
        return this;
    }


    public GetUploadObjectCredential url(String url) {
        this.url = url;
        return this;
    }

    public GetUploadObjectCredential fileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public GetUploadObjectCredential objectKey(String objectKey) {
        this.objectKey = objectKey;
        return this;
    }

    public GetUploadObjectCredential successStatusCode(int successStatusCode) {
        this.successStatusCode = successStatusCode;
        return this;
    }
}
