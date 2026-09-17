package com.isuzuki.http.apilog;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;

public class HttpRequestHttpApiLogCustomizer implements HttpApiLogCustomizer {

    @Override
    public void customize(HttpServletRequest request, HttpServletResponse response, HttpApiLogBuilder builder) {
        builder.payload(readPayload(request));
    }

    private Object readPayload(HttpServletRequest request) {
        String method = request.getMethod();
        if (HttpMethod.GET.matches(method)) {
            return "";
        }
        if (HttpMethod.POST.matches(method)) {
            String contentType = request.getContentType();
            if (MediaType.APPLICATION_JSON_VALUE.contains(contentType)) {
                byte[] contentAsByteArray = getContentAsByteArray(request);
                if (contentAsByteArray == null ||  contentAsByteArray.length == 0) {
                    return "";
                }
                return new String(contentAsByteArray, StandardCharsets.UTF_8);
            }
        }
        return request.getParameterMap();
    }

    private byte[] getContentAsByteArray(HttpServletRequest request) {
        /**
         * warning
         * {@link ContentCachingRequestWrapper} 默认不会先读流
         * 如果不调用该方法，{@link ContentCachingRequestWrapper#cachedContent} 的数据可能为空
         */
        request.getParameterMap();
        return getCachingRequestWrapper(request).getContentAsByteArray();
    }

    private ContentCachingRequestWrapper getCachingRequestWrapper (HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        }
        if (request instanceof HttpServletRequestWrapper) {
            ServletRequest servletRequest = ((HttpServletRequestWrapper)request).getRequest();
            if (servletRequest instanceof ContentCachingRequestWrapper) {
                return (ContentCachingRequestWrapper) servletRequest;
            }
        }
        return null;
    }
}
