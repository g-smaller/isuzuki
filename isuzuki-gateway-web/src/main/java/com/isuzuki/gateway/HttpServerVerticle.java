package com.isuzuki.gateway;

import com.isuzuki.gateway.handle.HttpApiLogHandler;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.Map;

/**
 * https://silentbalanceyh.gitbooks.io/vert-x/content/chapter01/01-1-functional-programming.html
 * https://vertx.java.net.cn/docs/vertx-core/java/
 * https://www.yuque.com/jiezizhu/r2mo
 * http://www.zerows.io/
 */
public class HttpServerVerticle extends VerticleBase {

    @Override
    public Future<?> start() {

        HttpServer httpServer = vertx.createHttpServer();
        Router mainRouter = Router.router(vertx);
        Route mainRoute = mainRouter.errorHandler(400, (ctx) -> {
                    ctx.json(Map.of("succeed", "false"));
                })
                .route("/**")
                .handler(new HttpApiLogHandler())
                .handler(BodyHandler.create(false))
                .failureHandler(HttpApiLogHandler::fail);

        Router apiRouter = Router.router(vertx);
        apiRouter.route("/api")
                .setName("")
                .enable()
                .putMetadata(Constants.Route.REQUEST_TIMEOUT, "3000")
                .putMetadata(Constants.Route.RESPONSE_TIMEOUT, "3000")
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
