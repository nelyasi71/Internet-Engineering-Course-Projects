package org.miobook.commands;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.BaseResponse;
import org.miobook.services.JsonValidator;


@Getter
@Setter
public class RemoveCart extends BaseCommand {

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
    public BaseResponse execute() {
        try {
            this.validate();
            Repositories.userRepository.removeCart(this);
            return new BaseResponse(true, "Removed book from cart.", null);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse(false, exp.getMessage(), null);
        }
    }
}
