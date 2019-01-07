package com.ltar.framework.db.mysql.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: changzhigao
 * @date: 2019-01-01
 * @version: 1.0.0
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Index {
    String name() default "";

    String columnList();

    boolean unique() default false;
}
