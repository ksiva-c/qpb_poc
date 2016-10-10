package com.demo.limits.common.validation;

import java.lang.annotation.*;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidationGroup {

    Class<?>[] value() default {};
}
