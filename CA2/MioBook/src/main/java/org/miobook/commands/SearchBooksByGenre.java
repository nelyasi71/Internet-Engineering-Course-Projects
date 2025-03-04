package org.miobook.commands;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.*;
import org.miobook.services.BookServices;

import java.util.List;

@Getter
@Setter
public class SearchBooksByGenre implements BaseCommand<SearchedBooksRecord> {

    @NonNull
    private String genre;

    @Override
    public void validate() {
    }

    @Override
    public BaseResponse<SearchedBooksRecord> execute() {
        try {
            this.validate();
            SearchedBooksRecord data = BookServices.searchBooksByGenre(this);
            SearchedBooksRecord responseData = new SearchedBooksRecord(this.genre, data.books());
            return new BaseResponse<>(true, "Books in the '" + this.genre + "' genre:", responseData);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
