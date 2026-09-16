package com.isuzuki.jdbc.datasources;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Proxy;

/**
 * @author : 
 * @date : 2023/9/15
 * @description :
 */
public class DataSourceClassResolver {

    public static Class<?> targetClass(MethodInvocation invocation) throws IllegalAccessException {
        Object target = invocation.getThis();
        Class<?> targetClass = target.getClass();
        return Proxy.isProxyClass(targetClass) ? targetClass : invocation.getMethod().getDeclaringClass();
    }

}
