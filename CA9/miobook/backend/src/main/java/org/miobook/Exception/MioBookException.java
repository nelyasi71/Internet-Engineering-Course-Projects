package org.miobook.Exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class MioBookException extends RuntimeException {
    private final String field;
    private final String fieldMessage;
    private final String logicError;

    // Field-level error
    public MioBookException(String field, String fieldMessage) {
        super("Validation failed");
        this.field = field;
        this.fieldMessage = fieldMessage;
        this.logicError = null;
    }

    // Logic-level error
    public MioBookException(String logicError) {
        super(logicError);
        this.logicError = logicError;
        this.field = null;
        this.fieldMessage = null;
    }

    public boolean isFieldError() {
        return field != null;
    }

    public Map<String, String> getFieldErrors() {
        return isFieldError() ? Map.of(field, fieldMessage) : null;
    }
}