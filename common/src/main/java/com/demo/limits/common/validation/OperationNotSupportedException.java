package com.demo.limits.common.validation;

import javax.validation.ConstraintViolationException;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
public class OperationNotSupportedException extends BaseValidationException {

    public static final String CODE = "MAPPER_EXCEPTION";

    public OperationNotSupportedException() {
    }

    public OperationNotSupportedException(String code, Object[] args, String message) {
        super(code, args, message);
    }

    public OperationNotSupportedException(String code, Object[] args, Throwable cause) {
        super(code, args, cause);
    }

    public OperationNotSupportedException(String code, Object[] args, String message, Throwable cause) {
        super(code, args, message, cause);
    }

    public OperationNotSupportedException(ConstraintViolationException cve) {
        super(cve);
    }

    @Override
    public BaseException create(ConstraintViolationException cve) {
        return new OperationNotSupportedException(cve);
    }
}
