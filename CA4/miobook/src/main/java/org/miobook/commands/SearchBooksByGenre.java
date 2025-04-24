package org.miobook.commands;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.Exception.MioBookException;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.*;
import org.miobook.services.BookServices;
import org.miobook.services.Services;

@Getter
@Setter
public class SearchBooksByGenre implements BaseCommand<SearchedBooksRecord> {

    @NotNull
    private String genre;

    @Override
    public void validate() {
        JsonValidator.validate(this);

    }

    @Override
    public BaseResponse<SearchedBooksRecord> execute(Services services) {
        try {
            this.validate();
            SearchedBooksRecord data = ((BookServices) services).searchBooksByGenre(this);
            SearchedBooksRecord responseData = new SearchedBooksRecord(this.genre, data.books());
            return new BaseResponse<>(true, "Books in the '" + this.genre + "' genre:", responseData);
        } catch (MioBookException exp) {
            return BaseResponse.fromMioBookException(exp);
        }
    }
}
