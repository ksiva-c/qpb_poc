package com.demo.limits.common.validation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestValidationException extends BaseException implements ValidationException, ValidationExceptionFactory{

    public static final String FORMAT = "";
    public static final String CODE = "TEST_VALIDATION";
    private Set<? extends ConstraintViolation> violations;

    private TestValidationException(ConstraintViolationException cve) {
        super(CODE, new Object[]{KEY_VIOLATION, cve.getConstraintViolations()}, cve.getMessage(), cve);
        this.violations = cve.getConstraintViolations();
        Map<String, Object> argsMap = new HashMap<>(1);
        argsMap.put(KEY_VIOLATION, cve.getConstraintViolations());
        this.setArgsMap(argsMap);
    }

    @Override
    public BaseException create(ConstraintViolationException cve) {
        return new TestValidationException(cve);
    }

    @Override
    public Set<? extends ConstraintViolation> getViolations() {
        return violations;
    }

    public TestValidationException() {
    }

    public TestValidationException(String code, Object[] args, String message) {
        super(code, args, message);
    }

    public TestValidationException(String code, Object[] args, Throwable cause) {
        super(code, args, cause);
    }

    public TestValidationException(String code, Object[] args, String message, Throwable cause) {
        super(code, args, message, cause);
    }
}
