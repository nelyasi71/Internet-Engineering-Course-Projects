package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.AuthorRecord;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.BookContentRecord;
import org.miobook.services.BookServices;

@Getter
@Setter
public class ShowBookContent implements BaseCommand<BookContentRecord> {

    @NotNull
    private String username;
    @NotNull
    private String title;
    @Override
    public void validate() {
    }
    @Override
    public BaseResponse<BookContentRecord> execute() {
        try {
            this.validate();
            BookContentRecord data = BookServices.showBookContent(this);
            return new BaseResponse<>(true, "Book content retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
