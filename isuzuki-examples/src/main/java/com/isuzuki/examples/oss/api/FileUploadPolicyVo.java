package com.isuzuki.examples.oss.api;

import java.util.Map;

public class FileUploadPolicyVo {

    private String uploadMethod;
    private String uploadUrl;
    private Long uploadMaxSize;
    private String objectKey;
    private String fileId;
    private String fileUrl;
    private int successStatusCode;
    private Map<String, String> headers;
    private Map<String, String> metas;

    public String getUploadMethod() {
        return uploadMethod;
    }

    public void setUploadMethod(String uploadMethod) {
        this.uploadMethod = uploadMethod;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public Long getUploadMaxSize() {
        return uploadMaxSize;
    }

    public void setUploadMaxSize(Long uploadMaxSize) {
        this.uploadMaxSize = uploadMaxSize;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getSuccessStatusCode() {
        return successStatusCode;
    }

    public void setSuccessStatusCode(int successStatusCode) {
        this.successStatusCode = successStatusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getMetas() {
        return metas;
    }

    public void setMetas(Map<String, String> metas) {
        this.metas = metas;
    }
}
