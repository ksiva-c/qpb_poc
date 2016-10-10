package com.demo.limits.common.validation;

import java.lang.annotation.*;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateException {
    Class<? extends ValidationExceptionFactory> value() default BaseValidationException.class;
}
