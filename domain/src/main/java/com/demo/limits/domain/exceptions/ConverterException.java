package com.demo.limits.domain.exceptions;

/**
 * Created by lnv.
 */
public class ConverterException extends DomainException {
    public static final String FROM_TYPE = "fromType";
    public static final String FROM_INSTANCE = "instance";
    public static final String TO_TYPE = "toType";

    public static final String CODE = "CONVERTER_EXCEPTION";
    String message = "{$} : Failed to convert from {$$}";
    public static final String FORMAT = "Could not convert from {$fromType$} instance {$instance$} to {$toType$}";
    public ConverterException(String code, Object[] args, String message) {
        super(code, args, message);
    }
}
