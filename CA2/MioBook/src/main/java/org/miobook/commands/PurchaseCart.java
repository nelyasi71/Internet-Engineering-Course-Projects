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
public class PurchaseCart extends BaseCommand {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Username can only contain letters, numbers, dash and underscores")
    private String username;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse execute() {
        Repositories.userRepository.purchaseCart(this);
        return null;

    }
}
