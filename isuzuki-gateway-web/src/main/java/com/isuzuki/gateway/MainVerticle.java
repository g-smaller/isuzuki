package com.isuzuki.gateway;

import com.isuzuki.core.logs.http.HttpApiCookie;
import com.isuzuki.core.logs.http.HttpApiLogBuilder;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * https://silentbalanceyh.gitbooks.io/vert-x/content/chapter01/01-1-functional-programming.html
 * https://vertx.java.net.cn/docs/vertx-core/java/
 * https://www.yuque.com/jiezizhu/r2mo
 * http://www.zerows.io/
 */
public class MainVerticle extends VerticleBase {

    @Override
    public Future<?> start() {

        HttpServer httpServer = vertx.createHttpServer();
        Router mainRouter = Router.router(vertx);
        Route mainRoute = mainRouter.errorHandler(400, (ctx) -> {
                    ctx.json(Map.of("succeed", "false"));
                })
                .route("/**")
                .handler((ctx) -> {
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
                    ctx.put(HttpApiLogBuilder.ATTRIBUTE, builder);
                    ctx.next();
                })
                .handler(BodyHandler.create(false))
                .failureHandler(ctx -> {
                    ctx.fail(503);
                });

        Router apiRouter = Router.router(vertx);
        apiRouter.route("/api")
                .handler(ctx -> {
                    ctx.json(Map.of("succeed", "true"));
                });

        mainRoute.subRouter(apiRouter);

        return httpServer
                .requestHandler(mainRouter)
                .exceptionHandler((t) -> {
                    t.printStackTrace();
                })
                .connectionHandler((conn) -> {

                })
                .listen(8080)
                .onSuccess((http) -> {
                    System.out.println("HTTP server started on port " + http.actualPort());
                });
    }
}
