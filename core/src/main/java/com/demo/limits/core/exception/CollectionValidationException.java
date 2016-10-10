package com.demo.limits.core.exception;

import com.demo.limits.common.validation.BaseException;
import com.demo.limits.common.validation.ValidationException;
import com.demo.limits.common.validation.ValidationExceptionFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lnv.
 */
public class CollectionValidationException extends CoreException implements ValidationException, ValidationExceptionFactory {

    public static final String FORMAT = "";
    public static final String CODE = "COLLECTION_VALIDATION";
    private Set<? extends ConstraintViolation> violations;

    private CollectionValidationException(ConstraintViolationException cve) {
        super(CODE, new Object[]{KEY_VIOLATION, cve.getConstraintViolations()}, cve.getMessage());
        this.violations = cve.getConstraintViolations();
        Map<String, Object> argsMap = new HashMap<>(1);
        argsMap.put(KEY_VIOLATION, cve.getConstraintViolations());
        this.setArgsMap(argsMap);
    }

    @Override
    public BaseException create(ConstraintViolationException cve) {
        return new CollectionValidationException(cve);
    }

    @Override
    public Set<? extends ConstraintViolation> getViolations() {
        return violations;
    }

    public CollectionValidationException() {
    }

}
