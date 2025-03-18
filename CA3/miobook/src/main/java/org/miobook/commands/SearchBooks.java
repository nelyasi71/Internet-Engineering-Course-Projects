package org.miobook.commands;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.*;
import org.miobook.services.BookServices;
import org.miobook.services.BookServices;


import java.lang.reflect.Method;
import java.util.*;

@Getter
@Setter
public class SearchBooks implements BaseCommand<SearchedBooksRecord> {
    private String title;
    private String author;
    private String genre;
    private Integer from;
    private Integer to;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<SearchedBooksRecord> execute() {
        try {
            this.validate();
            SearchedBooksRecord data = BookServices.searchBooks(this);
            return new BaseResponse<>(true, "Common books found:", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}