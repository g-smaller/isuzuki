package com.isuzuki.examples.oss.api;

import com.isuzuki.examples.oss.service.FileStoreService;
import com.isuzuki.oss.api.event.CloudOssUploadEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 
 * @date : 2025/11/12
 * @description :
 */
@RestController
@RequestMapping(value = "/fs/s3")
public class FileCallbackController {

    private Logger logger = LoggerFactory.getLogger(FileCallbackController.class);

    @Autowired
    private FileStoreService fileStoreService;

    @PostMapping(value = "/minio/webhook")
    public String minioWebhook(
            HttpServletRequest request) throws IOException{

        CloudOssUploadEvent event = createEvent(request);
        event.setProvider("MinIO");

        fileStoreService.store(event);
        return "SUCCESS!";
    }

    @PostMapping(value = "/aliyun/webhook")
    public Map<String, String> aliyunWebhook(HttpServletRequest request) throws IOException{

        CloudOssUploadEvent event = createEvent(request);
        event.setProvider("AliYun");

        try {
            fileStoreService.store(event);
            return Collections.singletonMap("Status", "OK");
        }catch (Exception e){
            logger.error("AliYun Webhook Error!", e);
            return Collections.singletonMap("Status", "verdify not ok");
        }
    }

    private CloudOssUploadEvent createEvent(HttpServletRequest request) throws IOException {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }

        CloudOssUploadEvent event = new CloudOssUploadEvent();
        event.setHeaders(headers);
        event.setBody(readBody(request.getInputStream(), request.getContentLength()));
        event.setQueryString(request.getQueryString());
        event.setUri(request.getRequestURI());
        return event;
    }

    public String readBody(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return new String(message);
            } catch (IOException e) {
            }
        }
        return "";
    }
}
