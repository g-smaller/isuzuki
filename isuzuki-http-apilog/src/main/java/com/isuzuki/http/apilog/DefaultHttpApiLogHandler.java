package com.isuzuki.http.apilog;

import com.isuzuki.utils.Jsons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultHttpApiLogHandler implements HttpApiLogHandler {

    private Logger logger = LoggerFactory.getLogger(HttpApiLog.class);

    @Override
    public void handle(HttpApiLogBuilder builder, Throwable throwable) {
        HttpApiLog log = builder.build();
        logger.info("{}", Jsons.toString(log));
    }
}
