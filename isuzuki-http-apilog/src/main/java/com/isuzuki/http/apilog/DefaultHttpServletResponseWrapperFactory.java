package com.isuzuki.http.apilog;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class DefaultHttpServletResponseWrapperFactory implements HttpServletResponseWrapperFactory {
    @Override
    public HttpServletResponseWrapper create(HttpServletResponse response) {
        return new ContentCachingResponseWrapper(response);
    }
}
