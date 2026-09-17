package com.isuzuki.http.apilog;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.List;

public class HttpApiLogFilter  extends OncePerRequestFilter {

    private final HttpApiLogProperties  httpApiLogProperties;
    private final HttpApiLogTraceContext  httpApiLogTraceContext;
    private final HttpApiLogBuilderFactory builderFactory;
    private final HttpApiLogHandler httpApiLogHandler;
    private final HttpServletRequestWrapperFactory requestWrapperFactory;
    private final HttpServletResponseWrapperFactory responseWrapperFactory;
    private final List<HttpApiLogCustomizer> customizers;

    public HttpApiLogFilter(HttpApiLogProperties  httpApiLogProperties,
                            HttpApiLogTraceContext  httpApiLogTraceContext,
                            HttpApiLogBuilderFactory builderFactory,
                            HttpApiLogHandler httpApiLogHandler,
                            HttpServletRequestWrapperFactory requestWrapperFactory,
                            HttpServletResponseWrapperFactory responseWrapperFactory,
                            List<HttpApiLogCustomizer> customizers) {
        this.httpApiLogProperties = httpApiLogProperties;
        this.httpApiLogTraceContext = httpApiLogTraceContext;
        this.builderFactory = builderFactory;
        this.httpApiLogHandler = httpApiLogHandler;
        this.requestWrapperFactory = requestWrapperFactory;
        this.responseWrapperFactory = responseWrapperFactory;
        this.customizers = customizers;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = requestWrapperFactory.create(request);
        HttpServletResponse httpServletResponse = responseWrapperFactory.create(response);
        HttpApiLogBuilder builder = builderFactory.create(httpServletRequest);

        String traceId = builder.getTraceId();
        if (traceId == null || traceId.isEmpty()) {
            traceId = httpApiLogTraceContext.getTraceId();
        }

        if (httpApiLogProperties.isAddTraceIdResponseHeader()) {
            response.addHeader(httpApiLogProperties.getTraceIdResponseHeaderName(), traceId);
        }

        HttpApiLogCollector collector = new HttpApiLogCollector(httpServletRequest, httpServletResponse, customizers);

        try {
            collector.preHandle(builder);
        }catch (Exception e){
            logger.error("日志记录 [collector.preHandle] 出错! ", e);
        }

        Throwable throwable = null;
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (Throwable t) {
            throwable = t;
            throw t;
        }finally {
            try {
                collector.postHandle(builder);
                if (httpServletResponse instanceof ContentCachingResponseWrapper) {
                    ((ContentCachingResponseWrapper) httpServletResponse).copyBodyToResponse();
                }
            }catch (Exception e){
                logger.error("日志记录 [collector.postHandle] 出错! ", e);
            }finally {
                httpApiLogHandler.handle(builder, throwable);
            }
        }
    }

}
