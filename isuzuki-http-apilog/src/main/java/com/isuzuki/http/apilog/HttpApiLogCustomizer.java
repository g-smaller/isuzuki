package com.isuzuki.http.apilog;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HttpApiLogCustomizer {

    default void customize(HttpServletRequest request, HttpApiLogBuilder builder) {

    }

    default void customize(HttpServletRequest request, HttpServletResponse response, HttpApiLogBuilder builder) {

    }
}
