package com.isuzuki.http.apilog;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpApiLogUtils {

    public final static String[] IP_HEADERS = {
            // 依次尝试从这些请求头中获取真实IP，按照优先级顺序排列
            "x-gateway-ip",
            "X-Forwarded-For", // 代理服务器最常用请求头，格式：client, proxy1, proxy2, proxy3... 以英文逗号和空格分隔
            "x-forwarded-for",
            "Proxy-Client-IP", // Apache会用
            "WL-Proxy-Client-IP", // WebLogic会用
            "HTTP_CLIENT_IP", // 某些代理服务器会用
            "HTTP_X_FORWARDED_FOR", // 某些代理服务器会用
            "X-Real-IP", // Nginx一般会用
            "x-real-ip"
    };

    public static Map<String, Object> getHeaders(HttpServletRequest request) {
        Map<String, Object> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (headerName.equalsIgnoreCase(HttpHeaders.HOST)
                    || headerName.equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH)
                    || headerName.equalsIgnoreCase(HttpHeaders.USER_AGENT)
                    || headerName.equalsIgnoreCase(HttpHeaders.REFERER)
                    || headerName.equalsIgnoreCase("auth")) {
                continue;
            }
            headerMap.put(headerName, getHeader(request, headerName));
        }
        return headerMap;
    }

    public static Map<String, Object> getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            Map<String, Object> cookiesMap = new HashMap<>(cookies.length,1.0F);
            for (Cookie cookie : cookies) {
                cookiesMap.put(cookie.getName(), cookie.getValue());
            }
            return cookiesMap;
        }
        return Collections.EMPTY_MAP;
    }

    public static String getHeader(HttpServletRequest request, String key) {
        String value = request.getHeader(key);
        return value == null ? "" : value;
    }

    public static String getUa(HttpServletRequest request) {
        return getHeader(request, HttpHeaders.USER_AGENT);
    }

    public static String getReferer(HttpServletRequest request) {
        return getHeader(request, HttpHeaders.REFERER);
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        for (String header : IP_HEADERS) {
            String ip = request.getHeader(header);
            if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip.trim())) {
                return ip.split(",", 2)[0].trim();
            }
        }
        // 从所有可能的HTTP HEADER中都没有找到客户端真实IP，采用request.getRemoteAddr()来兜底
        return request.getRemoteAddr();
    }

    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {

        }
        return "unknown host name";
    }

    public static String getLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

        }
        return "unknown host name";
    }
}
