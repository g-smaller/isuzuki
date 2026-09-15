package com.isuzuki.examples.oss.api;

import com.isuzuki.examples.oss.service.FileStoreService;
import com.isuzuki.oss.api.event.CloudOssUploadEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Guo QuanYing (guoquanying@cmvalue.com)
 * @date : 2025/11/12
 * @description :
 */
@RestController
@RequestMapping(value = "/fs/s3")
public class FileCallbackController {

    @Autowired
    private FileStoreService fileStoreService;

    @PostMapping(value = "/minio/webhook")
    public String minioWebhook(
            HttpServletRequest request,
            @RequestBody Map<String, Object> data) {

        CloudOssUploadEvent event = createEvent(request, data);
        event.setProvider("MinIO");

        fileStoreService.store(event);
        return "SUCCESS!";
    }

    @PostMapping(value = "/aliyun/webhook")
    public String aliyunWebhook(
            HttpServletRequest request,
            @RequestBody Map<String, Object> data) {

        CloudOssUploadEvent event = createEvent(request, data);
        event.setProvider("AliYun");

        fileStoreService.store(event);
        return "SUCCESS!";
    }

    private CloudOssUploadEvent createEvent(HttpServletRequest request,
                                            Map<String, Object> data) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }

        CloudOssUploadEvent event = new CloudOssUploadEvent();
        event.setHeaders(headers);
        event.setBody(data);
        event.setQueryString(request.getQueryString());
        event.setUri(request.getRequestURI());
        return event;
    }
}
