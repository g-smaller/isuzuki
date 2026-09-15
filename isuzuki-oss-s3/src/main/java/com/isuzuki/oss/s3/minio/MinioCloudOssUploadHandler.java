package com.isuzuki.oss.s3.minio;

import com.isuzuki.oss.api.event.CloudOssUploadEvent;
import com.isuzuki.oss.api.event.CloudOssUploadHandler;
import com.isuzuki.oss.api.event.CloudOssUploadObjectResult;
import com.isuzuki.utils.Jsons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MinioCloudOssUploadHandler implements CloudOssUploadHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public CloudOssUploadObjectResult handle(CloudOssUploadEvent event) {
        String bodyJson = Jsons.toString(event.getBody());
        logger.info("Minio Webhook: {} | {}", Jsons.toString(event.getHeaders()), bodyJson);

        MinioWebhookEvent s3Event = Jsons.parseObject(bodyJson, MinioWebhookEvent.class);

        MinioWebhookEventRecord firstRecord = s3Event.getRecords().get(0);
        MinioWebhookEventRecordS3 s3 = firstRecord.getS3();

        CloudOssUploadObjectResult objectResult = new CloudOssUploadObjectResult();
        objectResult.setProvider("MinIO");
        objectResult.setFileId(s3.getObject().getUserMetadata().getFileId());
        objectResult.setAppId(Objects.toString(s3.getObject().getUserMetadata().getAppId(), ""));
        objectResult.setFilename(Objects.toString(s3.getObject().getUserMetadata().getFilename(), ""));
        objectResult.setBizType("");
        objectResult.setBucket(s3.getBucket().getName());
        objectResult.setObjectKey(decode(s3.getObject().getKey()));
        objectResult.setSize(s3.getObject().getSize());
        objectResult.setWidth(0);
        objectResult.setHeight(0);
        objectResult.setFormat("");
        objectResult.setMimeType(s3.getObject().getContentType());
        objectResult.setEtag(s3.getObject().getEtag());
        objectResult.setFileMd5(s3.getObject().getEtag());
        objectResult.setVersionId(s3.getObject().getSequencer());
        objectResult.setAcl(Objects.toString(s3.getObject().getUserMetadata().getAcl(), ""));
        objectResult.setClientIp(Objects.toString(firstRecord.getSource().getHost()));
        objectResult.setPrincipal(Objects.toString(s3.getObject().getUserMetadata().getPrincipal(), ""));
        objectResult.setTraceId(Objects.toString(s3.getObject().getUserMetadata().getTraceId(), ""));
        objectResult.setS3RequestId(firstRecord.getResponseElements().getAmzRequestId());
        return objectResult;
    }

    @Override
    public boolean support(CloudOssUploadEvent event) {
        return "MinIO".equals(event.getProvider());
    }

    private String decode(String str) {
        try {
            return URLDecoder.decode(str, StandardCharsets.UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return str;
    }
}
