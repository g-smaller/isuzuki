package com.isuzuki.gateway.handler;

import com.alibaba.fastjson2.JSON;
import com.isuzuki.core.InetAddressUtils;
import com.isuzuki.core.http.logs.HttpApiCookie;
import com.isuzuki.core.http.logs.HttpApiLog;
import com.isuzuki.core.http.logs.HttpApiLogBuilder;
import com.isuzuki.core.http.logs.HttpApiLogFields;
import com.isuzuki.gateway.Constants;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpApiLogHandler implements Handler<RoutingContext> {

    private final static Logger logger = LoggerFactory.getLogger(HttpApiLog.LOGGER_NAME);

    @Override
    public void handle(RoutingContext ctx) {
        SpanContext spanContext = Span.current().getSpanContext();

        HttpApiLogBuilder builder = HttpApiLogBuilder.builder()
                .traceId(spanContext.getTraceId())
                .uri(ctx.request().path())
                .queryString(ctx.request().query())
                .host(ctx.request().getHeader("Host"))
                .method(ctx.request().method().name())
                .contentLength(ctx.request().getHeader(HttpHeaders.CONTENT_LENGTH))
                .contentType(ctx.request().getHeader(HttpHeaders.CONTENT_TYPE))
                .ua(ctx.request().getHeader(HttpHeaders.USER_AGENT))
                .referer(ctx.request().getHeader(HttpHeaders.REFERER))
                .clientIp(ctx.request().remoteAddress().hostAddress())
                .addExtra(HttpApiLogFields.Extra.OTEL_TRACE_ID, spanContext.getTraceId())
                .addExtra(HttpApiLogFields.Extra.OTEL_SPAN_ID, spanContext.getSpanId());

        ctx.request().headers().forEach((k, v) -> {
            builder.headers(k, v);
        });

        ctx.request().cookies().forEach((cookie) -> {
            builder.cookies(HttpApiCookie.cookie(cookie.getName(), cookie.getValue())
                    .attribute("path", cookie.getPath())
                    .attribute("domain", cookie.getDomain())
                    .attribute("maxAge", cookie.getMaxAge())
                    .attribute("secure", cookie.isSecure())
                    .attribute("httpOnly", cookie.isHttpOnly())
                    .attribute("sameSite", cookie.getSameSite().name())
                    .attribute("rawValue", cookie.encode())
            );
        });
        addMetadata(ctx, builder);
        ctx.put(HttpApiLog.ATTRIBUTE, builder);
        ctx.next();
    }

    public static void afterHandler(RoutingContext ctx) {
        finish(ctx);
    }

    public static void fail(RoutingContext ctx) {
        if (ctx.failed()) {
            HttpApiLogBuilder builder = getBuilder(ctx);
            if (builder == null) {
                return;
            }
            builder
                    .statusCode(ctx.statusCode())
                    .addExtra("exception.message", ctx.failure() == null ? "" : ctx.failure().getMessage());
        }
        finish(ctx);
    }

    private static void finish(RoutingContext ctx) {
        HttpApiLogBuilder builder = getBuilder(ctx);
        if (builder == null) {
            return;
        }
        RequestBody body = ctx.body();
        if (body != null) {
            builder.payload(body.asString(StandardCharsets.UTF_8.name()));
        }

        HttpApiLog apiLog = builder
                .response(ctx.get(Constants.Http.RESPONSE_BODY, ""))
                .build();
        logger.info("{}", JSON.toJSONString(apiLog));
    }

    private static HttpApiLogBuilder getBuilder(RoutingContext ctx) {
        Object o = ctx.get(HttpApiLog.ATTRIBUTE);
        if (o == null) {
            return null;
        }
        if (o instanceof HttpApiLogBuilder) {
            return (HttpApiLogBuilder) o;
        }
        return null;
    }

    public void addMetadata(RoutingContext ctx, HttpApiLogBuilder builder) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put(HttpApiLogFields.Metadata.SERVER_HOST_NAME, InetAddressUtils.getLocalHostName());
        metadata.put(HttpApiLogFields.Metadata.SERVER_IP, InetAddressUtils.getLocalIP());
        metadata.put(HttpApiLogFields.Metadata.SERVER_USER, System.getenv("USER"));
        metadata.put(HttpApiLogFields.Metadata.SERVER_HOME, System.getenv("HOME"));

        metadata.put(HttpApiLogFields.Metadata.APP_NAME, "");
        metadata.put(HttpApiLogFields.Metadata.APP_PORT, "");


        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryMXBean.getNonHeapMemoryUsage();

        metadata.put(HttpApiLogFields.Metadata.JVM_HEAP_MAX, heapUsage.getMax());
        metadata.put(HttpApiLogFields.Metadata.JVM_HEAP_COMMITTED, heapUsage.getCommitted());
        metadata.put(HttpApiLogFields.Metadata.JVM_HEAP_USAGE, heapUsage.getUsed());
        metadata.put(HttpApiLogFields.Metadata.JVM_HEAP_FREE, (heapUsage.getMax() - heapUsage.getUsed()));

        metadata.put(HttpApiLogFields.Metadata.JVM_NON_HEAP_MAX, nonHeapUsage.getMax());
        metadata.put(HttpApiLogFields.Metadata.JVM_NON_HEAP_COMMITTED, nonHeapUsage.getCommitted());
        metadata.put(HttpApiLogFields.Metadata.JVM_NON_HEAP_USAGE, nonHeapUsage.getUsed());
        metadata.put(HttpApiLogFields.Metadata.JVM_NON_HEAP_FREE, (nonHeapUsage.getMax() -  nonHeapUsage.getUsed()));

        builder.metadata(metadata);
    }
}
