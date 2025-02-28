package org.miobook.exceptions;

public class BookNameAlreadyExistsException extends ValidationException {
    public BookNameAlreadyExistsException(String bookName) {
        super("The book name '" + bookName + "' already exists.");
    }
}
