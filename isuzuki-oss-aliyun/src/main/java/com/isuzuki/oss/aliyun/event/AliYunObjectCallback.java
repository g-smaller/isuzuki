package com.isuzuki.oss.aliyun.event;


import com.alibaba.fastjson2.annotation.JSONField;

/**
 * @see com.isuzuki.oss.aliyun.AliYunUploadCallback#userProperties
 * @href {https://help.aliyun.com/zh/oss/developer-reference/callback?spm=a2c4g.11186623.0.0.1e73731cslOzTR#19f2e6eb46b27}
 *
 * @author :
 * @date : 2021/11/16
 * @description :
 */
public class AliYunObjectCallback {
    private String bucket;
    private String object;
    @JSONField(name = "size", defaultValue = "0")
    // @JsonProperty(value = "size", defaultValue = "0")
    private Long size;
    @JSONField(name = "width", defaultValue = "0")
    // @JsonProperty(value = "width", defaultValue = "0")
    private Integer width;
    @JSONField(name = "height", defaultValue = "0")
    // @JsonProperty(value = "height", defaultValue = "0")
    private Integer height;
    private String format;
    private String etag;
    private String mimeType;
    private String clientIp;

    @JSONField(name = "x:bizType")
    // @JsonProperty(value = "x:bizType")
    private String bizType;
    @JSONField(name = "x:principal")
    // @JsonProperty(value = "x:principal")
    private String principal;
    @JSONField(name = "x:app-id")
    // @JsonProperty(value = "x:appId")
    private String appId;
    @JSONField(name = "x:acl")
    // @JsonProperty(value = "x:acl")
    private String acl;
    @JSONField(name = "x:file-id")
    // @JsonProperty(value = "x:file-id")
    private String fileId;
    @JSONField(name = "x:trace-id")
    // @JsonProperty(value = "x:request-id")
    private String traceId;
    @JSONField(name = "x:filename")
    // @JsonProperty(value = "x:request-id")
    private String filename;

    @JSONField(name = "x:previewHost")
    // @JsonProperty(value = "x:previewHost")
    private String previewHost;

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPreviewHost() {
        return previewHost;
    }

    public void setPreviewHost(String previewHost) {
        this.previewHost = previewHost;
    }
}
