package com.isuzuki.oss.s3.minio;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 * @author : 
 * @date : 2025/11/12
 * @description :
 */
public class MinioWebhookEventRecordS3ObjectUserMetadata {
    @JSONField(name = "content-type")
    private String contentType;
    @JSONField(name = "X-Amz-Meta-Acl")
    private String acl;
    @JSONField(name = "X-Amz-Meta-File-Id")
    private String fileId;
    @JSONField(name = "X-Amz-Meta-Principal")
    private String principal;
    @JSONField(name = "X-Amz-Meta-Trace-Id")
    private String traceId;
    @JSONField(name = "X-Amz-Meta-App-Id")
    private String appId;
    @JSONField(name = "X-Amz-Meta-Filename")
    private String filename;

    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
