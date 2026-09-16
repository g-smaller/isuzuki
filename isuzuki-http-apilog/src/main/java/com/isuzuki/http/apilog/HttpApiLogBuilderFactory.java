package com.isuzuki.http.apilog;

import jakarta.servlet.http.HttpServletRequest;

public interface HttpApiLogBuilderFactory {

    HttpApiLogBuilder create(HttpServletRequest request);

}
