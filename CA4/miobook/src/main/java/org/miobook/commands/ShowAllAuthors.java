package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.AllAuthorsRecord;
import org.miobook.responses.AuthorRecord;
import org.miobook.responses.BaseResponse;
import org.miobook.services.AuthorServices;
import org.miobook.services.Services;

@Getter
@Setter
public class ShowAllAuthors implements BaseCommand<AllAuthorsRecord> {

    @NotNull
    private String username;
    @Override
    public void validate() {
        JsonValidator.validate(this);
    }
    @Override
    public BaseResponse<AllAuthorsRecord> execute(Services services) {
        try {
            this.validate();
            AllAuthorsRecord data = ((AuthorServices) services).showAllAuthors(this);
            return new BaseResponse<>(true, "Author details retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
