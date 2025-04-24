package org.miobook.commands;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.miobook.Exception.MioBookException;
import org.miobook.responses.BaseResponse;
import org.miobook.infrastructure.IntDeserializer;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.services.Services;
import org.miobook.services.UserServices;


@Getter
@Setter
public class AddCredit implements BaseCommand<Void> {

    @NotNull
    private String username;

    @NotNull
    @Range(min = 1000)
    @JsonDeserialize(using = IntDeserializer.class)
    private Integer credit;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<Void> execute(Services services) {
        try {
            this.validate();
            ((UserServices)services).addCredit(this);
            return new BaseResponse<Void>(true, "Credit added successfully.", null);
        } catch (MioBookException exp) {
            return BaseResponse.fromMioBookException(exp);
        }
    }
}
