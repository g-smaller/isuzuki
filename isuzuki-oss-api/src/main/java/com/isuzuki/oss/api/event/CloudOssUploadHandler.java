package com.isuzuki.oss.api.event;

public interface CloudOssUploadHandler {

    CloudOssUploadObjectResult handle(CloudOssUploadEvent event);

    boolean support(CloudOssUploadEvent event);
}
