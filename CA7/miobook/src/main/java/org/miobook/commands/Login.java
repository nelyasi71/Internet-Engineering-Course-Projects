package org.miobook.commands;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.Exception.MioBookException;
import org.miobook.responses.BaseResponse;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.UserLoggedIn;
import org.miobook.services.AuthServices;
import org.miobook.services.Services;

@Getter
@Setter
public class Login implements BaseCommand<UserLoggedIn> {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    public BaseResponse<UserLoggedIn> execute(Services authServices) {
        try {
            this.validate();
            UserLoggedIn data = ((AuthServices)authServices).login(this);
            return new BaseResponse<>(true, "User logged In successfully", data);
        } catch (MioBookException exp) {
            return BaseResponse.fromMioBookException(exp);
        }
    }
}
