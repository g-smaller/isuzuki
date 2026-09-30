package com.isuzuki.gateway.plugin;

import io.vertx.core.Handler;


public interface PriorityPluginHandler<E> extends Handler<E>, Ordered {

    int O_1 = Ordered.HIGHEST_PRECEDENCE + 1;
    int O_2 = Ordered.HIGHEST_PRECEDENCE + 2;
    int O_3 = Ordered.HIGHEST_PRECEDENCE + 3;
    int O_4 = Ordered.HIGHEST_PRECEDENCE + 4;
    int O_5 = Ordered.HIGHEST_PRECEDENCE + 5;
    int O_6 = Ordered.HIGHEST_PRECEDENCE + 6;
    int O_7 = Ordered.HIGHEST_PRECEDENCE + 7;

    default void afterHandler(E event) {

    }
}
