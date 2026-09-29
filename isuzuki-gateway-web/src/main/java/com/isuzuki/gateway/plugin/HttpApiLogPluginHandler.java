package com.isuzuki.gateway.plugin;

import com.alibaba.fastjson2.JSON;
import com.isuzuki.core.http.logs.HttpApiCookie;
import com.isuzuki.core.http.logs.HttpApiLog;
import com.isuzuki.core.http.logs.HttpApiLogBuilder;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpApiLogPluginHandler implements PriorityPluginHandler<RoutingContext> {

    private final static Logger logger = LoggerFactory.getLogger(HttpApiLog.LOGGER_NAME);

    @Override
    public void handle(RoutingContext ctx) {
        HttpApiLogBuilder builder = HttpApiLogBuilder.builder()
                .uri(ctx.request().uri())
                .queryString(ctx.request().query())
                .host(ctx.request().getHeader("Host"))
                .method(ctx.request().method().name())
                .contentLength(ctx.request().getHeader(HttpHeaders.CONTENT_LENGTH))
                .contentType(ctx.request().getHeader(HttpHeaders.CONTENT_TYPE))
                .ua(ctx.request().getHeader(HttpHeaders.USER_AGENT))
                .referer(ctx.request().getHeader(HttpHeaders.REFERER))
                .clientIp(ctx.request().remoteAddress().host());

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
        ctx.put(HttpApiLog.ATTRIBUTE, builder);
        ctx.next();
        end(ctx);
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
        end(ctx);
    }

    private static void end(RoutingContext ctx) {
        HttpApiLogBuilder builder = getBuilder(ctx);
        if (builder == null) {
            return;
        }
        HttpApiLog apiLog = builder.build();
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

    @Override
    public int getOrder() {
        return PriorityPluginHandler.O_1;
    }
}
