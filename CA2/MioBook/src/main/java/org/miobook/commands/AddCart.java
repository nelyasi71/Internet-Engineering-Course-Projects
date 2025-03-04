package org.miobook.commands;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.BaseResponse;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.services.CartServices;


@Getter
@Setter
public class AddCart implements BaseCommand<Void> {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Username can only contain letters, numbers, dash and underscores")
    private String username;

    @NotNull
    private String title;


    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<Void> execute() {
        try {
            this.validate();
            CartServices.addCart(this);
            return new BaseResponse<>(true, "Added book to cart.", null);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
