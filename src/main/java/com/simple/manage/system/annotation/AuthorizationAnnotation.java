package com.simple.manage.system.annotation;

import java.lang.annotation.*;

/**
 * Description 权限校验自定义注解
 * Author chen
 * CreateTime 2018-08-07 10:56
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorizationAnnotation {
}
