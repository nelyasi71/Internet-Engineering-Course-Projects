package org.miobook.commands;

import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.miobook.Exception.MioBookException;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.AllAuthorsRecord;
import org.miobook.responses.AllBooksRecord;
import org.miobook.responses.BaseResponse;
import org.miobook.services.AuthorServices;
import org.miobook.services.BookServices;
import org.miobook.services.Services;


@Setter
public class ShowAllBooks implements BaseCommand<AllBooksRecord> {
    @NotNull
    private String username;
    @Override
    public void validate() {
        JsonValidator.validate(this);
    }
    @Override
    public BaseResponse<AllBooksRecord> execute(Services services) {
        try {
            this.validate();
            AllBooksRecord data = ((BookServices) services).showAllBooks(this);
            return new BaseResponse<>(true, "Book details retrieved successfully.", data);
        } catch (MioBookException exp) {
            return BaseResponse.fromMioBookException(exp);
        }
    }
}
