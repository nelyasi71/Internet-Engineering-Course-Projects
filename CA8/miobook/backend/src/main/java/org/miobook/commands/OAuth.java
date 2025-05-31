package org.miobook.commands;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.Exception.MioBookException;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.JwtRecord;
import org.miobook.services.AuthServices;
import org.miobook.services.Services;

@Getter
@Setter
public class OAuth implements BaseCommand<JwtRecord> {

    @NotNull
    private String code;
    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    public BaseResponse<JwtRecord> execute(Services authServices) {
        try {
            this.validate();
            JwtRecord data = ((AuthServices)authServices).handleOauth(this);
            return new BaseResponse<>(true, "User logged In successfully", data);
        } catch (MioBookException exp) {
            return BaseResponse.fromMioBookException(exp);
        }
    }
}
