package org.miobook.exceptions;

public class InvalidGenresException extends ValidationException {
    public InvalidGenresException(String genre) {
        super("The genre '" + genre + "' is not valid.");
    }
}