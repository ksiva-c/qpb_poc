package com.demo.limits.common.utils;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
public enum TriState {
    TRUE, FALSE, UNSET;

    public static TriState valueOf(Boolean value){
        return (value == null)? TriState.UNSET :
                (value? TriState.TRUE: TriState.FALSE);

    }
}
