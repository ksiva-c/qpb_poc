package com.demo.limits.common.validation;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
public class LimitsMethodValidationInterceptor extends MethodValidationInterceptor {

    public LimitsMethodValidationInterceptor(Validator validator) {
        super(validator);
    }

    public LimitsMethodValidationInterceptor() {
        super();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try{
            return super.invoke(invocation);
        }catch(ConstraintViolationException cve){
            ValidateException validateException = AnnotationUtils.findAnnotation(invocation.getThis().getClass(), ValidateException.class);
            Class<? extends ValidationExceptionFactory> validateExceptionClass = (validateException != null)?validateException.value():BaseValidationException.class;
            ValidationExceptionFactory validateExceptionFactory = validateExceptionClass.newInstance();
            Set<ConstraintViolation<Object>> violations2 = new HashSet<>(cve.getConstraintViolations().size());
            for (ConstraintViolation<?> violation : cve.getConstraintViolations()) {
                violations2.add((ConstraintViolation<Object>) violation);
            }
            ConstraintViolationException cve2 = new ConstraintViolationException(ValidationHelper.toMessage(violations2), violations2);
            throw validateExceptionFactory.create(cve2);
        }
    }

    @Override
    protected Class<?>[] determineValidationGroups(MethodInvocation invocation) {
        final Class<?>[] classLevelGroups = super.determineValidationGroups(invocation);

        final ValidationGroup validatedGroups = AnnotationUtils.findAnnotation(
                invocation.getMethod(), ValidationGroup.class);

        final Class<?>[] methodLevelGroups = validatedGroups != null ? validatedGroups.value() : new Class<?>[0];
        if (methodLevelGroups.length == 0) {
            return classLevelGroups;
        }

        final int newLength = classLevelGroups.length + methodLevelGroups.length;
        final Class<?>[] mergedGroups = Arrays.copyOf(classLevelGroups, newLength);
        System.arraycopy(methodLevelGroups, 0, mergedGroups, classLevelGroups.length, methodLevelGroups.length);

        return mergedGroups;
    }
}
