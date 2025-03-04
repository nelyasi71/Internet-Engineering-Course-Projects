package org.miobook.commands;

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
public class SearchBooksByTitle implements BaseCommand<SearchedBooksRecord> {

    @NonNull
    private String title;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<SearchedBooksRecord> execute() {
        try {
            this.validate();
            SearchedBooksRecord data = BookServices.searchBooksByTitle(this);
            SearchedBooksRecord responseData = new SearchedBooksRecord(this.title, data.books());
            return new BaseResponse<>(true, "Books containing '" + this.title + "' in their title:", responseData);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
