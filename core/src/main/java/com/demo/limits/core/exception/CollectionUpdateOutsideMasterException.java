package com.demo.limits.core.exception;
/**
 * Created by lnv.
 */
public class CollectionUpdateOutsideMasterException extends CoreException{
    public static final String CODE = "COLLECTION_OPERATION_NOT_SUPPORTED";
    public static final String FORMAT = "Operation Not supported outside master entity,type [$type$] , name = [$name$] inside name = [$name2$]";

    public CollectionUpdateOutsideMasterException(String code, Object[] args, String message) {
        super(code, args, message);
    }

}

