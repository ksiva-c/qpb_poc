package com.demo.limits.common.validation;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
public class ValidationHelper {

    public static String toMessage(Set<ConstraintViolation<Object>> violations){
        StringBuilder builder = new StringBuilder();
        boolean isFirst = true;
        for (ConstraintViolation<?> violation : violations) {
            if (!isFirst){
                builder.append("\n");
            }
            builder.append(violation.getRootBeanClass().getSimpleName());
            builder.append(".");
            builder.append(violation.getPropertyPath().toString());
            builder.append(": ");
            builder.append(violation.getMessage());
            isFirst = false;
        }
        return builder.toString();
    }
}
