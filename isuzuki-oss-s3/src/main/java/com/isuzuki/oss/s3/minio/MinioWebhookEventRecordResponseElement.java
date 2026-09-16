package com.isuzuki.oss.s3.minio;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 * @author : 
 * @date : 2025/11/12
 * @description :
 */
public class MinioWebhookEventRecordResponseElement {

    @JSONField(name = "x-amz-id-2")
    private String amzId;
    @JSONField(name = "x-amz-request-id")
    private String amzRequestId;
    @JSONField(name = "x-minio-deployment-id")
    private String minioDeploymentId;
    @JSONField(name = "x-minio-origin-endpoint")
    private String minioOriginEndpoint;

    public String getAmzId() {
        return amzId;
    }

    public void setAmzId(String amzId) {
        this.amzId = amzId;
    }

    public String getAmzRequestId() {
        return amzRequestId;
    }

    public void setAmzRequestId(String amzRequestId) {
        this.amzRequestId = amzRequestId;
    }

    public String getMinioDeploymentId() {
        return minioDeploymentId;
    }

    public void setMinioDeploymentId(String minioDeploymentId) {
        this.minioDeploymentId = minioDeploymentId;
    }

    public String getMinioOriginEndpoint() {
        return minioOriginEndpoint;
    }

    public void setMinioOriginEndpoint(String minioOriginEndpoint) {
        this.minioOriginEndpoint = minioOriginEndpoint;
    }
}
