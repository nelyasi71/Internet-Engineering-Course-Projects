package org.miobook.exceptions;

public class ValidationException extends RuntimeException {
    public CommandProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
