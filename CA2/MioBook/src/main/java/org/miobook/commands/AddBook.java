package org.miobook.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.miobook.exceptions.*;
import org.miobook.model.Book;
import org.miobook.validation.ValidationService;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddBook extends BaseCommand {

    public AddBook(JSONObject inputJson) {

        super(inputJson);

    }

    @Override
    public boolean validate() {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = objectMapper.readValue(inputJson.toString(), Book.class);
        List<String> errorsMassages = new ArrayList<>();

        try {
            ValidationService.validateBookName(book.getTitle());
        } catch (BookNameAlreadyExistsException e) {
            errorMessages.add(e.getMessage());
        }
        try {
            ValidationService.validateAuthor(book.getAuthor());
        } catch (AuthorNotFoundException e) {
            errorMessages.add(e.getMessage());
        }
        try {
            ValidationService.validatePublisher(book.getPublisher());
        } catch (InvalidPublisherException e) {
            errorMessages.add(e.getMessage());
        }
        try {
            ValidationService.validateYear(book.getYear());
        } catch (InvalidYearException e) {
            errorMessages.add(e.getMessage());
        }
        try {
            ValidationService.validateGenres(book.getGenres());
        } catch (InvalidGenresException e) {
            errorMessages.add(e.getMessage());
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation failed: \n" + String.join("\n", errors));
        }
        return true;
    }

    @Override
    public void execute(){
        if (!validate()) {
            return;
        }
        return;//add logic later
    };
}