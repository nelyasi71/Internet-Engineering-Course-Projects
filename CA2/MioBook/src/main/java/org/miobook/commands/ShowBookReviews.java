package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.BookReviewRecord;

@Getter
@Setter
public class ShowBookReviews implements BaseCommand<BookReviewRecord> {

    @NotNull
    private String title;

    @Override
    public void validate() {
    }

    @Override
    public BaseResponse<BookReviewRecord> execute() {
        try {
            this.validate();
            BookReviewRecord data = Repositories.bookRepository.showBookReviews(this);
            return new BaseResponse<>(true, "Book reviews retrieved successfully.", data);
        } catch (IllegalArgumentException exp){
            return new BaseResponse<>(false, exp.getMessage(), null);
        }

    }
}
