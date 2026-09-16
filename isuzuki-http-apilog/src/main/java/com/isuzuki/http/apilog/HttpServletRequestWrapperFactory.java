package com.isuzuki.http.apilog;

import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface HttpServletRequestWrapperFactory {

    HttpServletRequest create(HttpServletRequest request);

}
