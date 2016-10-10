package com.demo.limits.common.utils;

import com.demo.limits.common.validation.BaseException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
public class CollectionUtils {

    public static <T extends Object> Map<String, T> arrayToMap(Class<? extends Object> T, Object... args){
        if (args == null) return Collections.emptyMap();
        if (args.length %2 != 0){
            throw new BaseException("UTILITY_ERROR", args, "Number of arguments passed to exception factory shall always be even");
        }
        Map<String, T> map = new HashMap<>();
        for(int i=0; i<args.length; i=i+2){
            if (args[i] instanceof String){
                map.put((String)args[i], (T)args[i+1]);
            }
            else{
                throw new BaseException("UTILITY_ERROR", args, "Argument at odd number shall always be of type string");
            }
        }
        return map;
    }



}
