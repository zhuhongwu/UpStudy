package com.lagou.edu.annotation;

import java.lang.annotation.*;

/**
 * @author zhuhongwu
 * @date 2020/5/7
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {
}
