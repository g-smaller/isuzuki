package com.isuzuki.http.apilog;

import java.util.Map;

public interface HttpApiLog {
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

    Map<String, Object> getCookies();

    Map<String, Object> getHeaders();

    Map<String, Object> getAnnotations();

    Map<String, Object> getMetadata();

    Map<String, Object> getExtra();

    HttpAuthenticator getAuthenticator();

    Integer getStatusCode();

    Object getResponse();

    Long getElapsedTime();

    String getElapsedTimeFormat();
}

