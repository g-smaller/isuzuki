package com.isuzuki.gateway;

import com.isuzuki.gateway.handle.HandlerChains;
import com.isuzuki.gateway.plugin.HttpApiLogPluginHandler;
import com.isuzuki.gateway.plugin.ServiceDiscoveryPluginHandler;
import com.isuzuki.gateway.plugin.URLPluginHandler;
import com.isuzuki.gateway.plugin.WebClientPluginHandler;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
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

        Router apiRouter = Router.router(vertx);
        apiRouter.route("/ai/api/*")
                .setName("ai.api.sets")
                .enable()
                .putMetadata(Constants.Route.REQUEST_TIMEOUT, "2000")
                .putMetadata(Constants.Route.RESPONSE_TIMEOUT, "2000")
                .handler(HandlerChains.create()
                        .add(new HttpApiLogPluginHandler())
                        .add(new URLPluginHandler())
                        .add(new ServiceDiscoveryPluginHandler())
                        .add(new WebClientPluginHandler(WebClient.wrap(vertx.httpClientBuilder().build())))
                );

        Router mainRouter = Router.router(vertx);
        mainRouter.errorHandler(400, (ctx) -> {
                    ctx.json(Map.of("success", "false", "code", "400", "message", "error"));
                })
                .route("/*")
                .handler(BodyHandler.create(false))
                .failureHandler(HttpApiLogPluginHandler::fail)
                .subRouter(apiRouter);


        return vertx.createHttpServer()
                .requestHandler(mainRouter)
                .exceptionHandler((t) -> {
                    t.printStackTrace();
                })
                .connectionHandler((conn) -> {

                })
                .listen(8080)
                .onSuccess((http) -> {
                    System.out.println("Server listening on http://0.0.0.0:" + http.actualPort());
                });
    }
}
