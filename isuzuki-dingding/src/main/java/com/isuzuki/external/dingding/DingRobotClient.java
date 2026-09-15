package com.isuzuki.external.dingding;

import java.util.Map;

public interface DingRobotClient {

    String send(String url, Map<String, Object> params);

}
