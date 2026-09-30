package com.isuzuki.gateway;

public interface Constants {

    interface Http {
        String SERVER_ADDRESS = "http.server-address";
        String DOMAIN = "http.domain";
        String URI = "http.uri";
        String METHOD = "http.method";
        String PATH = "http.path";
        String BODY = "http.body";
        String HEADERS = "http.headers";
        String CONTENT_TYPE = "http.content-type";
        String CONTENT_LENGTH = "http.content-length";
        String HOST = "http.host";
        String SCHEME = "http.scheme";
        String REQUEST_TIMEOUT = "http.request-timeout";
        String RESPONSE_TIMEOUT = "http.response-timeout";
        String RESPONSE_BODY = "http.response-body";
    }

    interface Service {
        String NAME = "service.name";
    }

    interface ServiceDiscovery {
        String TYPE = "serviceDiscovery.type";
        String NAMESPACE = "serviceDiscovery.namespace";
        String url = "serviceDiscovery.url";
        String ACCESS_KEY = "serviceDiscovery.access-key";
        String SECRET_KEY = "serviceDiscovery.secret-key";
        String GROUP = "serviceDiscovery.group";
    }

    interface LoadBalancer {
        String TYPE = "loadBalancer.type";
    }

    interface Route {
        String ENABLED = "route.enabled";
        String HOST =  "route.host";
        String NAME = "route.name";
        String METHOD = "route.method";
        String PATH = "route.path";
        String CONSUMERS =  "route.consumes";
        String REQUEST_TIMEOUT = "route.request-timeout";
        String RESPONSE_TIMEOUT = "route.response-timeout";
    }
}
