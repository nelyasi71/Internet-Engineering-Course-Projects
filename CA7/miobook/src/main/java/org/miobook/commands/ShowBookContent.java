package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.Exception.MioBookException;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.BookContentRecord;
import org.miobook.services.BookServices;
import org.miobook.services.Services;

@Getter
@Setter
public class ShowBookContent implements BaseCommand<BookContentRecord> {

    @NotNull
    private String username;
    @NotNull
    private String title;
    @Override
    public void validate() {
        JsonValidator.validate(this);
    }
    @Override
    public BaseResponse<BookContentRecord> execute(Services services) {
        try {
            this.validate();
            BookContentRecord data = ((BookServices) services).showBookContent(this);
            return new BaseResponse<>(true, "Book content retrieved successfully.", data);
        } catch (MioBookException exp) {
            return BaseResponse.fromMioBookException(exp);
        }
    }
}
