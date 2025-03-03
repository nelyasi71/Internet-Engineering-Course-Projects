package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.AuthorRecord;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.BookRecord;

@Getter
@Setter
public class ShowBookDetails implements BaseCommand<BookRecord> {

    @NotNull
    private String title;
    @Override
    public void validate() {
    }
    @Override
    public BaseResponse<BookRecord> execute() {
        try {
            this.validate();
            BookRecord data = Repositories.bookRepository.showBookDetails(this);
            return new BaseResponse<>(true, "Book details retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }

    }
}
