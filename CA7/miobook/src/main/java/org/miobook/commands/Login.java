package org.miobook.commands;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.Exception.MioBookException;
import org.miobook.responses.BaseResponse;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.services.AuthServices;
import org.miobook.services.Services;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class Login implements BaseCommand<Void> {

    @NotNull
    private String username;

    @NotNull
    private String password;

    HttpSession session;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    public BaseResponse<Void> execute(Services authServices) {
        try {
            this.validate();
            ((AuthServices)authServices).login(this);
            return new BaseResponse<>(true, "User logged In successfully", null);
        } catch (MioBookException exp) {
            return BaseResponse.fromMioBookException(exp);
        }
    }
}
