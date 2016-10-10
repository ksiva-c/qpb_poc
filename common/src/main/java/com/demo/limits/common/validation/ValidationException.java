package com.demo.limits.common.validation;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidationException {

    public static final String KEY_VIOLATION = "CONSTRAINT_VIOLATION";

    public Set<? extends ConstraintViolation> getViolations();
}
