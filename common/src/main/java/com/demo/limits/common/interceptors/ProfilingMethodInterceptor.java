
package com.demo.limits.common.interceptors;

import com.demo.limits.common.utils.StringUtils;
import com.demo.limits.common.validation.Assertions;
import com.demo.limits.common.validation.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * User: lnv
 * Date:
 * Time: 
 */

public class ProfilingMethodInterceptor implements ThrowsAdvice
{
    private static final Logger log = LoggerFactory.getLogger(ProfilingMethodInterceptor.class);
    private Assertions assertions;
    private StringUtils stringUtils;

    public ProfilingMethodInterceptor(Assertions assertions, StringUtils stringUtils) {
        this.assertions = assertions;
        this.stringUtils = stringUtils;
    }

    public void afterThrowing(Method method,
                              Object[] args,
                              Object target,
                              BaseException be)
    {
        be.setMethodInfo(toString(method, args, target, be));
        log.error(String.format("Call to %s failed due to %s", be.getMethodInfo(), be.getLocalizedMessage()));
    }

    public void afterThrowing(Method method,
                              Object[] args,
                              Object target,
                              Throwable subclass)
    {
        BaseException be = assertions.wrap(subclass, BaseException.class, "INTERNAL_ERROR", new Object[]{"cause", subclass.getMessage()});
        be.setMethodInfo(toString(method, args, target, subclass));
        log.error(String.format("Call to %s failed due to %s", be.getMethodInfo(), be.getLocalizedMessage()));
    }

    private String toString(Method method,
                            Object[] args,
                            Object target,
                            Throwable subclass){
        try{
            String className = target.toString();
            className = className.substring(0, className.indexOf("@"));

            String format = "%s.%s(%s)";
            StringBuilder builder = new StringBuilder();

            final Class<?>[] parameterTypes = method.getParameterTypes();

            for(int i=0; i<parameterTypes.length; i++){
                if (i >0) builder.append(", ");
                builder.append("args").append(i).append("::");
                builder.append(stringUtils.toString(args[i], parameterTypes[i]));
            }
            return String.format(format, className, method.getName(),
                    builder.toString());
        }
        catch(Throwable e){
            //Eat it.. no need to throw it back
            return "METHOD_INFO_CANNOT_BE_RETRIEVED";
        }
    }
}