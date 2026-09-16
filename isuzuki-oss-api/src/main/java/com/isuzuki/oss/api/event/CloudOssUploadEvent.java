package com.isuzuki.oss.api.event;

import java.util.Map;

public class CloudOssUploadEvent {
    private String provider;
    private String uri;
    private String queryString;
    private Map<String, String> headers;
    private String body;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeader(String key) {
        if (headers == null) {
            return "";
        }
        return headers.getOrDefault(key, "");
    }
}
