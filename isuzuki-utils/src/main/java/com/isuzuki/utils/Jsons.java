package com.isuzuki.utils;

import com.alibaba.fastjson2.JSON;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Jsons {

    public static String toString(Object object) {
        return JSON.toJSONString(object);
    }

    public static byte[] toBytes(Object object) {
        return toBytes(object, StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(Object object, Charset charset) {
        return JSON.toJSONBytes(object, charset);
    }

    public static <T> T parseObject(String str, Class<T> clazz) {
        return JSON.parseObject(str, clazz);
    }
}
