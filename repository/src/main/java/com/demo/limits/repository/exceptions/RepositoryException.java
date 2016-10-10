package com.demo.limits.repository.exceptions;

import com.demo.limits.common.validation.BaseException;

/**
 * Created by lnv.
 */
public class RepositoryException extends BaseException{
    public RepositoryException() {
    }

    public RepositoryException(String code, Object[] args, String message) {
        super(code, args, message);
    }

    public RepositoryException(String code, Object[] args, Throwable cause) {
        super(code, args, cause);
    }

    public RepositoryException(String code, Object[] args, String message, Throwable cause) {
        super(code, args, message, cause);
    }
}
