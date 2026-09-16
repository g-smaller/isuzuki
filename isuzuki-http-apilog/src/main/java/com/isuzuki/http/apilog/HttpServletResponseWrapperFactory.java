package com.isuzuki.http.apilog;

import jakarta.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface HttpServletResponseWrapperFactory {

    HttpServletResponse create(HttpServletResponse response);

}
