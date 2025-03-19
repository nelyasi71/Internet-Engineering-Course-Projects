package org.miobook.commands;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.miobook.responses.BaseResponse;
import org.miobook.services.AuthServices;
import org.miobook.services.Services;


@Getter
@Setter
public class Logout implements BaseCommand<Void> {

    private HttpSession session;
    @Override
    public void validate() {
        return;
    }

    public BaseResponse<Void> execute(Services authServices) {
        try {
            this.validate();
            ((AuthServices)authServices).logout(this);
            return new BaseResponse<>(true, "User logged out successfully", null);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
