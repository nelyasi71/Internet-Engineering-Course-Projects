package org.miobook.commands;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.miobook.repositories.Repositories;
import org.miobook.responses.BaseResponse;
import org.miobook.infrastructure.IntDeserializer;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.services.UserServices;


@Getter
@Setter
public class AddCredit implements BaseCommand<Void> {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Username can only contain letters, numbers, dash and underscores")
    private String username;

    @NotNull
    @Range(min = 1000)
    @JsonDeserialize(using = IntDeserializer.class)
    private int credit;


    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<Void> execute() {
        try {
            this.validate();
            UserServices.addCredit(this);
            return new BaseResponse<Void>(true, "Credit added successfully.", null);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<Void>(false, exp.getMessage(), null);
        }
    }
}
