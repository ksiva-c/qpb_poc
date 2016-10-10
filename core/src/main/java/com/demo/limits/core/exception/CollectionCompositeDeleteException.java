package com.demo.limits.core.exception;

/**
 * Created by lnv.
 */
public class CollectionCompositeDeleteException extends CoreException{
    public static final String CODE = "COLLECTION_OPERATION_NOT_SUPPORTED";
    public static final String FORMAT = "Delete operation Not supported on composite, entity has other entities referenced " +
            ", entity type [$type$] and id = [$id$]";

    public CollectionCompositeDeleteException(String code, Object[] args, String message) {
        super(code, args, message);
    }
}
