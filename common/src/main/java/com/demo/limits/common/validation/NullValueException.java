package com.demo.limits.common.validation;

public class NullValueException extends BaseException {

    public static final String CODE = "NULL_VALUE_EXCEPTION";
    public static final String FORMAT = "Passed value is null";

    public NullValueException() {
    }

    public NullValueException(String code, Object[] args, String message) {
        super(code, args, message);
    }

    public NullValueException(String code, Object[] args, Throwable cause) {
        super(code, args, cause);
    }

    public NullValueException(String code, Object[] args, String message, Throwable cause) {
        super(code, args, message, cause);
    }
}
