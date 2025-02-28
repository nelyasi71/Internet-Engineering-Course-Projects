package org.miobook.exceptions;

public class InvalidPublisherException extends ValidationException {
    public InvalidPublisherException(String publisher) {
        super("The publisher '" + publisher + "' is not valid.");
    }
}