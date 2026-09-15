package com.isuzuki.jdbc.datasources.annotation;


import com.isuzuki.jdbc.datasources.DynamicDataSourceConstants;

import java.lang.annotation.*;

/**
 * @author : 
 * @date : 2023/9/15
 * @description :
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DynamicLookup {

    String value() default DynamicDataSourceConstants.MASTER;

}
