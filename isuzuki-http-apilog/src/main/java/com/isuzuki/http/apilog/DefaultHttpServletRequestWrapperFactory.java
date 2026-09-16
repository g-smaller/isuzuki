package com.isuzuki.http.apilog;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.web.util.ContentCachingRequestWrapper;

public class DefaultHttpServletRequestWrapperFactory implements HttpServletRequestWrapperFactory {
    @Override
    public HttpServletRequestWrapper create(HttpServletRequest request) {
        return new ContentCachingRequestWrapper(request, 0);
    }
}
