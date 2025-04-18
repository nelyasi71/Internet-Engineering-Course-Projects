package org.miobook.commands;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.LoggedInUserRecord;
import org.miobook.services.AuthServices;
import org.miobook.services.Services;


@Getter
@Setter
public class FetchUser implements BaseCommand<LoggedInUserRecord> {
    private HttpSession session;
    @Override
    public void validate() {
        return;
    }

    public BaseResponse<LoggedInUserRecord> execute(Services authServices) {
        try {
            this.validate();
            LoggedInUserRecord data = ((AuthServices)authServices).fetchUser(this);
            return new BaseResponse<>(true, "User fetched successfully", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
