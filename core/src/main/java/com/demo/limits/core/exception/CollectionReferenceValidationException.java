package com.demo.limits.core.exception;

/**
 * Created by lnv.
 */
public class CollectionReferenceValidationException extends CoreException {
    public static final String CODE = "COLLECTION_REFERENCE_ENTITY_VALIDATION_FAIL";
    public static final String FORMAT = "Operation not supported, usage of entity type [$type$]," +
            " with id = [$id$], not supported with entity of type [$type2$], with id = [$id2$]";

    public CollectionReferenceValidationException(String code, Object[] args, String message) {
        super(code, args, message);
    }
}
