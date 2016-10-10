package com.demo.limits.core.exception;

/**
 * Created by lnv.
 */
public class CollectionDuplicateException extends CoreException {
    public static final String CODE = "COLLECTION.OPTIMIZATION.DUPLICATE";

    public static final String FORMAT = "Optimization for scenario, scenario id = [$scenarioId$] already exists.";

    public CollectionDuplicateException(String code, Object[] args, String message) {
        super(code, args, message);
    }
}
