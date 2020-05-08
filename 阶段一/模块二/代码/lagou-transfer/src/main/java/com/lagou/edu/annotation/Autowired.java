package com.lagou.edu.annotation;

import java.lang.annotation.*;

/**
 * @author zhuhongwu
 * @date 2020/5/7
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
}
