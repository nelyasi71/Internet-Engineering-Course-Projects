package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.AuthorRecord;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.UserRecord;

@Getter
@Setter
public class ShowAuthorDetails implements BaseCommand<AuthorRecord> {

    @NotNull
    private String username;
    @Override
    public void validate() {
    }
    @Override
    public BaseResponse<AuthorRecord> execute() {
        try {
            this.validate();
            AuthorRecord data = Repositories.authorRepository.showAuthorDetails(this);
            return new BaseResponse<>(true, "Author details retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
