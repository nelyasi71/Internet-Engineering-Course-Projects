package org.miobook.commands;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.*;
import org.miobook.services.BookServices;
import org.miobook.services.Services;

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
    public BaseResponse<SearchedBooksRecord> execute(Services services) {
        try {
            this.validate();
            SearchedBooksRecord data = ((BookServices) services).searchBooksByAuthor(this);
            SearchedBooksRecord responseData = new SearchedBooksRecord(this.name, data.books());
            return new BaseResponse<>(true, "Books by '" + this.name + "' :", responseData);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
