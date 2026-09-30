package com.isuzuki.gateway;

import com.isuzuki.gateway.plugin.PluginHandlerChains;
import com.isuzuki.gateway.handler.HttpApiLogHandler;
import com.isuzuki.gateway.plugin.ServiceDiscoveryPluginHandler;
import com.isuzuki.gateway.plugin.URLPluginHandler;
import com.isuzuki.gateway.handler.WebClientHandler;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * https://silentbalanceyh.gitbooks.io/vert-x/content/chapter01/01-1-functional-programming.html
 * https://vertx.java.net.cn/docs/vertx-core/java/
 * https://www.yuque.com/jiezizhu/r2mo
 * http://www.zerows.io/
 */
public class HttpServerVerticle extends VerticleBase {

    private static final Logger log = LoggerFactory.getLogger(HttpServerVerticle.class);

    @Override
    public Future<?> start() {

        Router apiRouter = Router.router(vertx);
        apiRouter.errorHandler(400, (ctx) -> {
                    log.error("400 Bad Request", ctx.failure());
                    ctx.json(Map.of("success", "false", "code", "400", "message", "Bad Request"));
                })
                .errorHandler(404, (ctx) -> {
                    log.error("404 Not Found, {}", ctx.currentRoute().toString(), ctx.failure());
                    ctx.json(Map.of("success", "false", "code", "404", "message", "Not Found"));
                })
                .errorHandler(500, (ctx) -> {
                    log.error("500 Internal Server Error", ctx.failure());
                    ctx.json(Map.of("success", "false", "code", "500", "message", "Internal Server Error"));
                })
                .route("/api/disease/*")
                .setName("ai.api.sets")
                .enable()
                .putMetadata(Constants.Route.REQUEST_TIMEOUT, "2000")
                .putMetadata(Constants.Route.RESPONSE_TIMEOUT, "2000")
                .failureHandler(HttpApiLogHandler::fail)
                .handler(BodyHandler.create(false))
                .handler(new HttpApiLogHandler())
                .handler(PluginHandlerChains.create()
                        .add(new URLPluginHandler())
                        .add(new ServiceDiscoveryPluginHandler())
                ).handler(new WebClientHandler(WebClient.wrap(vertx.httpClientBuilder().build())));

        Router mainRouter = Router.router(vertx);
        mainRouter.route("/v1/*")
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
