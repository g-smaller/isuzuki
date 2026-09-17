package com.isuzuki.http.apilog;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = HttpApiLogProperties.PREFIX)
public class HttpApiLogProperties {

    public static final String PREFIX = "isuzuki.http.apilog";

    private boolean enabled;
    private boolean addResponseHeader;
    private String responseHeaderName = "x-trace-id";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAddResponseHeader() {
        return addResponseHeader;
    }

    public void setAddResponseHeader(boolean addResponseHeader) {
        this.addResponseHeader = addResponseHeader;
    }

    public String getResponseHeaderName() {
        return responseHeaderName;
    }

    public void setResponseHeaderName(String responseHeaderName) {
        this.responseHeaderName = responseHeaderName;
    }
}
