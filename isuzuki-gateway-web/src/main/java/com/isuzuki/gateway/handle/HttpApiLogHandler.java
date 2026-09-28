package com.isuzuki.gateway.handle;

import com.isuzuki.core.http.logs.HttpApiCookie;
import com.isuzuki.core.http.logs.HttpApiLog;
import com.isuzuki.core.http.logs.HttpApiLogBuilder;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

public class HttpApiLogHandler implements PriorityHandler {

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
    }

    public static void fail(RoutingContext ctx) {
        if (ctx.failed()) {
            Object o = ctx.get(HttpApiLog.ATTRIBUTE);
            if (o == null) {
                return;
            }
            if (o instanceof HttpApiLogBuilder) {
                ((HttpApiLogBuilder)o)
                        .statusCode(ctx.statusCode())
                        .addExtra("exception.message", ctx.failure().getMessage());
            }
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
