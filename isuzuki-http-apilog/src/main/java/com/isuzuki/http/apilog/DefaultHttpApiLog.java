package com.isuzuki.http.apilog;

import java.util.Map;

public class DefaultHttpApiLog implements HttpApiLog {
    private String traceId;
    private String requestTimeFormat;
    private Long requestTime;
    private String clientIp;
    private String apiId;
    private String apiName;
    private String uri;
    private String method;
    private String host;
    private Object payload;
    private String queryString;
    private Integer contentLength;
    private String contentType;
    private String referer;
    private String ua;
    private Map<String, Object> cookies;
    private Map<String, Object> headers;
    private Map<String, Object> annotations;
    private Map<String, Object> metadata;
    private Map<String, Object> extra;
    private HttpAuthenticator authenticator;
    private Integer statusCode;
    private Object response;
    private Long elapsedTime;
    private String elapsedTimeFormat;

    @Override
    public String getTraceId() {
        return traceId;
    }

    @Override
    public Long getRequestTime() {
        return requestTime;
    }

    @Override
    public String getRequestTimeFormat() {
        return requestTimeFormat;
    }

    @Override
    public String getClientIp() {
        return clientIp;
    }

    @Override
    public String getApiId() {
        return apiId;
    }

    @Override
    public String getApiName() {
        return apiName;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public Object getPayload() {
        return payload;
    }

    @Override
    public Integer getContentLength() {
        return contentLength;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getReferer() {
        return referer;
    }

    @Override
    public String getUa() {
        return ua;
    }

    @Override
    public Map<String, Object> getCookies() {
        return cookies;
    }

    @Override
    public Map<String, Object> getHeaders() {
        return headers;
    }

    @Override
    public Map<String, Object> getAnnotations() {
        return annotations;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    @Override
    public Map<String, Object> getExtra() {
        return extra;
    }

    @Override
    public HttpAuthenticator getAuthenticator() {
        return authenticator;
    }

    @Override
    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public Object getResponse() {
        return response;
    }

    @Override
    public Long getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public String getElapsedTimeFormat() {
        return  elapsedTimeFormat;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public void setRequestTimeFormat(String requestTimeFormat) {
        this.requestTimeFormat = requestTimeFormat;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public void setCookies(Map<String, Object> cookies) {
        this.cookies = cookies;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public void setAnnotations(Map<String, Object> annotations) {
        this.annotations = annotations;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    public void setAuthenticator(HttpAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void setElapsedTimeFormat(String elapsedTimeFormat) {
        this.elapsedTimeFormat = elapsedTimeFormat;
    }
}
