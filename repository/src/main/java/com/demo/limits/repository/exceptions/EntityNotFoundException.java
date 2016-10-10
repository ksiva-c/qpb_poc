package com.demo.limits.repository.exceptions;


/**
 * User: lnv
 * Date: 
 * Time: 
 */
public class EntityNotFoundException extends RepositoryException {

    public static final String KEY_TYPE = "type";
    public static final String KEY_ID = "id";

    public static final String CODE = "REPOSITORY.ENTITY_NOT_FOUND";
    public static final String FORMAT = "Element for type [$type$] for ID [$id$] not found.";

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String code, Object[] args, String message) {
        super(code, args, message);
    }

    public EntityNotFoundException(String code, Object[] args, Throwable cause) {
        super(code, args, cause);
    }

    public EntityNotFoundException(String code, Object[] args, String message, Throwable cause) {
        super(code, args, message, cause);
    }
}
