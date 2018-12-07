package com.simple.manage.system.annotation;

import java.lang.annotation.*;

/**
 * Description 令牌校验自定义注解
 * Author chen
 * CreateTime 2018-07-24 10:18
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenAnnotation {
}
