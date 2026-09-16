package com.isuzuki.http.apilog;

public interface HttpApiLogFields {

    interface Annotation {

    }

    interface Metadata {
        String SERVER_HOST_NAME = "server.host_name";
        String SERVER_IP = "server.ip";
        String SERVER_USER = "server.user";
        String SERVER_HOME = "server.home";

        String APP_NAME = "application.name";
        String APP_PORT = "application.port";

        String JVM_HEAP_FREE = "jvm.heap.free";
        String JVM_HEAP_MAX = "jvm.heap.max";
        String JVM_HEAP_USAGE = "jvm.heap.usage";
        String JVM_HEAP_COMMITTED = "jvm.heap.committed";

        String JVM_NON_HEAP_FREE = "jvm.nonheap.free";
        String JVM_NON_HEAP_MAX = "jvm.nonheap.max";
        String JVM_NON_HEAP_USAGE = "jvm.nonheap.usage";
        String JVM_NON_HEAP_COMMITTED = "jvm.nonheap.committed";

    }

    interface Extra {

    }

}
