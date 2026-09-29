package com.isuzuki.gateway.http;

import io.vertx.core.MultiMap;

public interface HttpHeaderFilter {

    MultiMap filter(MultiMap headers);

}
