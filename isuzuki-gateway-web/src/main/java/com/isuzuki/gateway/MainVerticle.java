package com.isuzuki.gateway;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.Map;

public class MainVerticle extends VerticleBase {

    @Override
    public Future<?> start() {

        vertx.eventBus().send("", "");
        vertx.eventBus().consumer("").handler((s) -> {

        }).completion();

        HttpServer httpServer = vertx.createHttpServer();
        Router mainRouter = Router.router(vertx);
        Route route = mainRouter.errorHandler(400, (ctx) -> {
                    ctx.json(Map.of("succeed", "false"));
                })
                .route("/**")
                .handler(BodyHandler.create(false))
                .failureHandler(ctx -> {
                    ctx.fail(503);
                });

        Router apiRouter = Router.router(vertx);
        apiRouter.route("/api")
                .handler(ctx -> {
                    ctx.json(Map.of("succeed", "true"));
                });

        route.subRouter(apiRouter);

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
