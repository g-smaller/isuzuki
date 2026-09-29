package com.isuzuki.gateway;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class VertxApplication {

    public static void main(String[] args) {

        VertxOptions vertxOptions = new VertxOptions();

        Vertx.builder()
                .with(vertxOptions)
                .build()
                .deployVerticle(new HttpServerVerticle(),  new DeploymentOptions())
                .onComplete(r -> {
                    if (r.succeeded()) {
                        System.out.println("Vert.x Application Start Successful!");
                    }else {
                        System.out.println("Vert.x Application Start Failure!");
                    }
                }).onFailure(e -> {
                    System.out.println("Failure = " + e.getMessage());
                    e.printStackTrace();
                });
    }
}
