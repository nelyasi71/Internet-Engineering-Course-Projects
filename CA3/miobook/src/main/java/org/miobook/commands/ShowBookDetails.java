package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.repositories.Repositories;
import org.miobook.responses.AuthorRecord;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.BookRecord;
import org.miobook.services.BookServices;

@Getter
@Setter
public class ShowBookDetails implements BaseCommand<BookRecord> {

    @NotNull
    private String title;
    @Override
    public void validate() {
        JsonValidator.validate(this);
    }
    @Override
    public BaseResponse<BookRecord> execute() {
        try {
            this.validate();
            BookRecord data = BookServices.showBookDetails(this);
            return new BaseResponse<>(true, "Book details retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
