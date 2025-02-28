package org.miobook.exceptions;

public class AuthorNotFoundException extends ValidationException {
    public AuthorNotFoundException(String authorName) {
        super("The author '" + authorName + "' does not exist.");
    }
}