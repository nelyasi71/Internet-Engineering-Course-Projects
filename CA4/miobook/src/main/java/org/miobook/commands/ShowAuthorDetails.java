package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.AuthorRecord;
import org.miobook.responses.BaseResponse;
import org.miobook.services.AuthorServices;
import org.miobook.services.Services;

@Getter
@Setter
public class ShowAuthorDetails implements BaseCommand<AuthorRecord> {

    @NotNull
    private String username;
    @Override
    public void validate() {
        JsonValidator.validate(this);
    }
    @Override
    public BaseResponse<AuthorRecord> execute(Services services) {
        try {
            this.validate();
            AuthorRecord data = ((AuthorServices) services).showAuthorDetails(this);
            return new BaseResponse<>(true, "Author details retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
