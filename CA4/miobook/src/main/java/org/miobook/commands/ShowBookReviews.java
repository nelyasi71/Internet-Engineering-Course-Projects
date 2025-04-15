package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.BookReviewRecord;
import org.miobook.services.BookServices;
import org.miobook.services.Services;

@Getter
@Setter
public class ShowBookReviews implements BaseCommand<BookReviewRecord> {

    @NotNull
    private String title;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<BookReviewRecord> execute(Services services) {
        try {
            this.validate();
            BookReviewRecord data = ((BookServices) services).showBookReviews(this);
            return new BaseResponse<>(true, "Book reviews retrieved successfully.", data);
        } catch (IllegalArgumentException exp){
            return new BaseResponse<>(false, exp.getMessage(), null);
        }

    }
}
