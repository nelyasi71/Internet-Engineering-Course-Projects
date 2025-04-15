package org.miobook.infrastructure;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

public class JsonValidator {
    private static final jakarta.validation.ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();
    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:\n");
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }
}
