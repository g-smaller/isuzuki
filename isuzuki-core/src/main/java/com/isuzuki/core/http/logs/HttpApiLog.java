package com.isuzuki.core.http.logs;

import java.util.List;
import java.util.Map;

public interface HttpApiLog {

    String ATTRIBUTE = HttpApiLogBuilder.class.getName();

    String getTraceId();

    Long getRequestTime();

    String getRequestTimeFormat();

    String getClientIp();

    String getApiId();

    String getApiName();

    String getUri();

    String getMethod();

    String getHost();

    String getQueryString();

    Object getPayload();

    Integer getContentLength();

    String getContentType();

    String getReferer();

    String getUa();

    List<HttpApiCookie> getCookies();

    Map<String, Object> getHeaders();

    Map<String, Object> getAnnotations();

    Map<String, Object> getMetadata();

    Map<String, Object> getExtra();

    Map<String, Object> getAuthentication();

    Integer getStatusCode();

    Object getResponse();

    Long getElapsedTime();

    String getElapsedTimeFormat();
}

