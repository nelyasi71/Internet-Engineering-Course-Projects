package org.miobook.infrastructure;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.miobook.Exception.MioBookException;

import java.util.Set;

public class JsonValidator {
    private static final jakarta.validation.ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            ConstraintViolation<T> firstViolation = violations.iterator().next();
            String field = firstViolation.getPropertyPath().toString();
            String message = firstViolation.getMessage();
            throw new MioBookException(field, message);
        }
    }
}
