package com.isuzuki.http.apilog;

import java.util.Map;

public interface HttpApiLog {
    String getApiId();

    String getApiName();

    Map<String, Object> getAnnotations();

    Map<String, Object> getMetadata();

    Map<String, Object> getExtra();

    HttpAuthenticator getAuthenticator();

    String getClientIp();

    Integer getContentLength();

    String getHost();

    Map<String, Object> getCookies();

    Map<String, Object> getHeaders();

    String getMethod();

    String getReferer();

    Object getPayload();

    String getQueryString();

    String getRequestTimeFormat();

    Long getRequestTime();

    String getServiceName();

    Integer getStatusCode();

    String getRequestId();

    String getResponse();

    Integer getElapsedMilliSecond();

    String getUa();

    String getUri();

    String getHostname();
}

