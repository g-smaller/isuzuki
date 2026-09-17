package com.isuzuki.http.apilog;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = HttpApiLogProperties.PREFIX)
public class HttpApiLogProperties {

    public static final String PREFIX = "isuzuki.http.apilog";

    private boolean enabled;
    private boolean addTraceIdResponseHeader = true;
    private String traceIdResponseHeaderName = "X-Http-Trace-Id";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAddTraceIdResponseHeader() {
        return addTraceIdResponseHeader;
    }

    public void setAddTraceIdResponseHeader(boolean addTraceIdResponseHeader) {
        this.addTraceIdResponseHeader = addTraceIdResponseHeader;
    }

    public String getTraceIdResponseHeaderName() {
        return traceIdResponseHeaderName;
    }

    public void setTraceIdResponseHeaderName(String traceIdResponseHeaderName) {
        this.traceIdResponseHeaderName = traceIdResponseHeaderName;
    }
}
