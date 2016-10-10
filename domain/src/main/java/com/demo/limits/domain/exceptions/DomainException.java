package com.demo.limits.domain.exceptions;

import com.demo.limits.common.validation.BaseException;

/**
 * Created by lnv.
 */
public class DomainException extends BaseException{

    public DomainException() {
    }

    public DomainException(String code, Object[] args, String message) {
        super(code, args, message);
    }

    public DomainException(String code, Object[] args, Throwable cause) {
        super(code, args, cause);
    }

    public DomainException(String code, Object[] args, String message, Throwable cause) {
        super(code, args, message, cause);
    }
}
