package com.yeliheng.eventbus.annotations;

import com.yeliheng.eventbus.enums.ThreadType;

import java.lang.annotation.*;

/**
 * Subscribe注解
 * @author Liam Ye
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Subscribe {
    ThreadType threadType() default ThreadType.CURRENT;
}
