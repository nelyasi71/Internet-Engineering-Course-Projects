package org.miobook.commands;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.*;
import org.miobook.services.BookServices;
import org.miobook.services.BookServices;
import org.miobook.services.Services;


import java.lang.reflect.Method;
import java.util.*;

@Getter
@Setter
public class SearchBooks implements BaseCommand<SearchedBooksRecord> {
    private String title;
    private String author;
    private String genre;

    @Max(value = 2100)
    private Integer from;

    @Max(value = 2100)
    private Integer to;

    @Pattern(regexp = "^(review_count|average_rating)$")
    private String sortBy;

    @Pattern(regexp = "^(asc|desc)$")
    private String order;

    @Min(value = 1)
    int page;

    @Min(value = 1)
    @Max(value = 100)
    int size;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<SearchedBooksRecord> execute(Services services) {
        try {
            this.validate();
            SearchedBooksRecord data = ((BookServices) services).searchBooks(this);
            return new BaseResponse<>(true, "Common books found:", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}