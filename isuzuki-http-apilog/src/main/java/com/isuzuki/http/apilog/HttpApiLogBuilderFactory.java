package com.isuzuki.http.apilog;

import com.isuzuki.core.logs.http.HttpApiLogBuilder;
import jakarta.servlet.http.HttpServletRequest;

public interface HttpApiLogBuilderFactory {

    HttpApiLogBuilder create(HttpServletRequest request);

}
