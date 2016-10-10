package com.demo.limits.core.exception;

/**
 * Created by lnv.
 */
public class CollectionNotFoundException extends CoreException {
    public static final String CODE = "COLLECTION_ENTITY_NOT_FOUND";

    public static final String FORMAT = "Element for type [$type$] for ID [$id$] not found.";

    public CollectionNotFoundException(String code, Object[] args, String message) {
        super(code, args, message);
    }
}
