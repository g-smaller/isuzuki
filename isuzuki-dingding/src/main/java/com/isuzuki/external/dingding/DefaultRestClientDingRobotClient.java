package com.isuzuki.external.dingding;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.Map;

public class DefaultRestClientDingRobotClient implements DingRobotClient {

    private final RestClient restClient;

    public DefaultRestClientDingRobotClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public String send(String url, Map<String, Object> params) {
        return restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(params)
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }

}
