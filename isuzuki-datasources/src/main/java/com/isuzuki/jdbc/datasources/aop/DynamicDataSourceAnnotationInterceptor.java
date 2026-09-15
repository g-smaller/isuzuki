package com.isuzuki.jdbc.datasources.aop;

import com.isuzuki.jdbc.datasources.DataSourceClassResolver;
import com.isuzuki.jdbc.datasources.DynamicDataSourceContextHolder;
import com.isuzuki.jdbc.datasources.annotation.DynamicLookup;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * @author : 
 * @date : 2023/9/15
 * @description :
 */
public class DynamicDataSourceAnnotationInterceptor implements MethodInterceptor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            String lookupKey = determineCurrentLookupKey(invocation);
            DynamicDataSourceContextHolder.push(lookupKey);
            logger.info("DynamicDataSource @Lookup Push Deque: {} - Size: {}", lookupKey, DynamicDataSourceContextHolder.keySize());
            return invocation.proceed();
        } finally {
            String lookupKey = DynamicDataSourceContextHolder.poll();
            logger.info("DynamicDataSource @Lookup Poll Deque: {} - Size: {}", lookupKey, DynamicDataSourceContextHolder.keySize());
        }
    }

    private String determineCurrentLookupKey(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        DynamicLookup lookup = method.isAnnotationPresent(DynamicLookup.class) ? method.getAnnotation(DynamicLookup.class) : AnnotationUtils.findAnnotation(DataSourceClassResolver.targetClass(invocation), DynamicLookup.class);
        return lookup.value();
    }
}
