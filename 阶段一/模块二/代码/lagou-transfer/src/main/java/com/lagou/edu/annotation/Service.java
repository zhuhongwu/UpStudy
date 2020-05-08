package com.lagou.edu.annotation;

import java.lang.annotation.*;

/**
 * @author zhuhongwu
 * @date 2020/5/7
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {


    String value() default "";

}

