package org.miobook.exceptions;

public class InvalidYearException extends ValidationException {
    public InvalidYearException(int year) {
        super("The year '" + year + "' is not valid.");
    }
}
