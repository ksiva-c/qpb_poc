package com.demo.limits.core.exception;

import com.demo.limits.common.validation.BaseException;

/**
 * Created by lnv.
 */
public class CoreException extends BaseException {
    public CoreException() {
    }

    public CoreException(String code, Object[] args, String message) {
        super(code, args, message);
    }

}
