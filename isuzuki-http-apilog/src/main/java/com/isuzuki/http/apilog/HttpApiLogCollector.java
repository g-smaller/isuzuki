package com.isuzuki.http.apilog;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class HttpApiLogCollector {

    private final HttpServletRequest httpRequest;
    private final HttpServletResponse httpResponse;
    private final List<HttpApiLogCustomizer> customizers;

    public HttpApiLogCollector(HttpServletRequest httpRequest, HttpServletResponse httpResponse, List<HttpApiLogCustomizer> customizers) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
        this.customizers = customizers;
    }

    public void preHandle(HttpApiLogBuilder builder) {
        if (customizers == null || customizers.isEmpty()) {
            return;
        }
        customizers.forEach(customizer -> customizer.customize(httpRequest, builder));
    }

    public void postHandle(HttpApiLogBuilder builder) {
        if (customizers == null || customizers.isEmpty()) {
            return;
        }
        customizers.forEach(customizer -> customizer.customize(httpRequest, httpResponse, builder));
    }
}
