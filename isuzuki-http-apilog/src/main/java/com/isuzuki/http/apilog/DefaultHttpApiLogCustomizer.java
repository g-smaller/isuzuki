package com.isuzuki.http.apilog;

import com.isuzuki.core.logs.http.HttpApiLogBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DefaultHttpApiLogCustomizer implements HttpApiLogCustomizer {

    @Override
    public void customize(HttpServletRequest request, HttpApiLogBuilder builder) {
        String uri = request.getRequestURI();
        builder.uri(uri)
                .queryString(request.getQueryString())
                .host(request.getHeader("Host"))
                .headers(HttpApiLogUtils.getHeaders(request))
                .cookies(HttpApiLogUtils.getCookies(request))
                .method(request.getMethod())
                .contentLength(request.getContentLength())
                .contentType(request.getContentType())
                .ua(HttpApiLogUtils.getUa(request))
                .referer(HttpApiLogUtils.getReferer(request))
                .clientIp(HttpApiLogUtils.getRemoteAddr(request));

    }

    @Override
    public void customize(HttpServletRequest request, HttpServletResponse response, HttpApiLogBuilder builder) {

    }
}
