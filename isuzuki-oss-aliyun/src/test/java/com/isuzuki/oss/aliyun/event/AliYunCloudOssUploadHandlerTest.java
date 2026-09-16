package com.isuzuki.oss.aliyun.event;

import com.isuzuki.oss.api.event.CloudOssUploadEvent;
import com.isuzuki.oss.api.event.CloudOssUploadObjectResult;
import com.isuzuki.utils.Jsons;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

public class AliYunCloudOssUploadHandlerTest {

    @org.junit.jupiter.api.Test
    public void handle() {

        String json = "";
        String headers = "";

        Map<String, String> headerMap = new HashMap<>();
        String[] split = headers.split("\n");
        for (String s : split) {
            int i = s.indexOf("=");
            if (i > 0) {
                String key = s.substring(0, i);
                String value = s.substring(i + 1);
                headerMap.put(key, value);
            }
        }

        CloudOssUploadEvent event = new CloudOssUploadEvent();
        event.setUri("");
        event.setQueryString("");
        event.setHeaders(headerMap);
        event.setBody(json);

        RestClient restClient = RestClient.builder().build();
        AliYunCloudOssUploadHandler handler = new AliYunCloudOssUploadHandler(restClient);
        CloudOssUploadObjectResult result = handler.handle(event);
        System.out.println(Jsons.toString(result));
    }
}