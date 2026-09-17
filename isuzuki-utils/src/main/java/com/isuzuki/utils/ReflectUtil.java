package com.isuzuki.utils;


import com.alibaba.fastjson2.util.ParameterizedTypeImpl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class ReflectUtil {


    public static Type[] resolveReturnType(Method method) {
        List<Type> types = new ArrayList<>();
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType != null) {
            types.add(genericReturnType);
        }
        while (genericReturnType != null) {
            genericReturnType = resolveType(types, genericReturnType);
        }
        return types.toArray(new Type[]{});
    }

    private static Type resolveType(List<Type> types, Type type) {
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                types.add(actualTypeArgument);
                return resolveType(types, actualTypeArgument);
            }
        }
        return null;
    }

    public static Type buildFastJsonType(Method method) {
        return buildFastJsonType(resolveReturnType(method));
    }

    /** 为序列化两次以上泛型提供的工具类 BaseResult<List<T>>,作用类似
     *
     * TypeReference< BaseResult<List<T>>> type = new TypeReference<BaseResult<List<T>>>(BaseResult.class, List.class, returnClazz) { };
     * @param types
     * @return
     */
    public static Type buildFastJsonType(Type... types) {
        ParameterizedTypeImpl beforeType = null;
        if (types != null && types.length > 0) {
            for (int i = types.length - 1; i > 0; i--) {
                beforeType = new ParameterizedTypeImpl(new Type[]{beforeType == null ? types[i] : beforeType}, null, types[i - 1]);
            }
        }
        return beforeType;
    }
}
