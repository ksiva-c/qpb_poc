package com.demo.limits.common.validation;

import javax.validation.ConstraintViolationException;
public interface ValidationExceptionFactory {
    public BaseException create(ConstraintViolationException cve);
}
