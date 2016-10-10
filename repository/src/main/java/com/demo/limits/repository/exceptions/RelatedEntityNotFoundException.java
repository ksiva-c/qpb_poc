package com.demo.limits.repository.exceptions;


/**
 * User: lnv
 * Date: 
 * Time: 
 */
public class RelatedEntityNotFoundException extends RepositoryException {

    public static final String KEY_TYPE = "type";
    public static final String KEY_REF_TYPE = "refType";
    public static final String KEY_REF_ID = "refId";

    public static final String CODE = "REPOSITORY.RELATED_ENTITY_NOT_FOUND";

    public static final String FORMAT = "Element for type [$type$] related to type [$refType$] for ID [$refId$] not found.";

    public RelatedEntityNotFoundException() {
    }

    public RelatedEntityNotFoundException(String code, Object[] args, String message) {
        super(code, args, message);
    }

    public RelatedEntityNotFoundException(String code, Object[] args, Throwable cause) {
        super(code, args, cause);
    }

    public RelatedEntityNotFoundException(String code, Object[] args, String message, Throwable cause) {
        super(code, args, message, cause);
    }

}
