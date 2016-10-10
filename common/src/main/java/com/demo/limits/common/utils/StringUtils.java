package com.demo.limits.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * User: lnv
 * Date: 
 * Time: 
 */

@Component
public class StringUtils {

    public static final String PACKAGE_ROOT = "com.demo";

    @Inject
    @Named("defaultJacksonMapper")
    ObjectMapper objectMapper;

    public final String toString(Object obj, Class<?> clazz){
        if (obj == null) return "null";
        if (clazz.getName().startsWith(PACKAGE_ROOT)){
            try{
                return objectMapper.writeValueAsString(obj);
            }
            catch(Exception e){
                //If any exception in converting to JSON, use string builder to do that
                try{
                    return ToStringBuilder.reflectionToString(obj, new RecursiveToStringStyleEx());
                }
                catch(Exception e1){
                    //If any exception in reflection method, use default toString impl of object
                    return obj.toString();
                }
            }
        }
        else{
            return String.valueOf(obj);
        }
    }

    private static class RecursiveToStringStyleEx extends RecursiveToStringStyle{
        RecursiveToStringStyleEx(){
            this.setUseShortClassName(true);
            this.setUseIdentityHashCode(false);
        }

        @Override
        protected boolean accept(Class<?> clazz) {
            return clazz.getName().startsWith(PACKAGE_ROOT);
        }
    }
}
