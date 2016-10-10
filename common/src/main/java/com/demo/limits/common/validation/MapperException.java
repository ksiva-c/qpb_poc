package com.demo.limits.common.validation;

import javax.validation.ConstraintViolationException;

/**
 * User: lnv
 * Date:
 * Time: 
 */
public class MapperException extends BaseValidationException {

    public static final String CODE = "MAPPER_EXCEPTION";

    public MapperException() {
    }

    public MapperException(String code, Object[] args, String message) {
        super(code, args, message);
    }

    public MapperException(String code, Object[] args, Throwable cause) {
        super(code, args, cause);
    }

    public MapperException(String code, Object[] args, String message, Throwable cause) {
        super(code, args, message, cause);
    }

    public MapperException(ConstraintViolationException cve) {
        super(cve);
    }

    @Override
    public BaseException create(ConstraintViolationException cve) {
        return new MapperException(cve);
    }
}
