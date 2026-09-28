package com.isuzuki.http.apilog;

import com.isuzuki.core.http.logs.HttpApiLogBuilder;
import jakarta.servlet.http.HttpServletRequest;

public interface HttpApiLogBuilderFactory {

    HttpApiLogBuilder create(HttpServletRequest request);

}
