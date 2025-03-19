package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.CartRecord;
import org.miobook.services.CartServices;
import org.miobook.services.Services;

@Getter
@Setter
public class ShowCart implements BaseCommand<CartRecord> {

    @NotNull
    private String username;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }
    @Override
    public BaseResponse<CartRecord> execute(Services services) {
        try {
            this.validate();
            CartRecord data = ((CartServices) services).showCart(this);
            return new BaseResponse<>(true, "Buy cart retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
