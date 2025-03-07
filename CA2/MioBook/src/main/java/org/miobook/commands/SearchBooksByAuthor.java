package org.miobook.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.repositories.Repositories;
import org.miobook.responses.*;
import org.miobook.services.BookServices;

import java.util.List;

@Getter
@Setter
public class SearchBooksByAuthor implements BaseCommand<SearchedBooksRecord> {

    @NotNull
    private String name;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<SearchedBooksRecord> execute() {
        try {
            this.validate();
            SearchedBooksRecord data = BookServices.searchBooksByAuthor(this);
            SearchedBooksRecord responseData = new SearchedBooksRecord(this.name, data.books());
            return new BaseResponse<>(true, "Books by '" + this.name + "' :", responseData);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
