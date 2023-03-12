package com.xxgl.aop;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Log {

    String modelName() default "";
    String option();

}