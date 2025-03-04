package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.repositories.Repositories;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.UserRecord;
import org.miobook.services.UserServices;

@Getter
@Setter
public class ShowUserDetails implements BaseCommand<UserRecord> {

    @NotNull
    private String username;
    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<UserRecord> execute() {
        try {
            this.validate();
            UserRecord data = UserServices.showUserDetails(this);
            return new BaseResponse<>(true, "User details retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
