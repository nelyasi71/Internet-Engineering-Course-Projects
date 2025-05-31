package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.Exception.MioBookException;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.UserRecord;
import org.miobook.services.Services;
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

    public ShowUserDetails(String username) {
        this.username = username;
    }

    @Override
    public BaseResponse<UserRecord> execute(Services services) {
        try {
            this.validate();
            UserRecord data = ((UserServices) services).showUserDetails(this);
            return new BaseResponse<>(true, "User details retrieved successfully.", data);
        } catch (MioBookException exp) {
            return BaseResponse.fromMioBookException(exp);
        }
    }
}
