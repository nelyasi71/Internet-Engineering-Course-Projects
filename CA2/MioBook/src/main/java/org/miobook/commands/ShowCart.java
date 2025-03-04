package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.BookRecord;
import org.miobook.responses.CartRecord;
import org.miobook.services.CartServices;

@Getter
@Setter
public class ShowCart implements BaseCommand<CartRecord> {

    @NotNull
    private String username;

    @Override
    public void validate() {
    }
    @Override
    public BaseResponse<CartRecord> execute() {
        try {
            this.validate();
            CartRecord data = CartServices.showCart(this);
            return new BaseResponse<>(true, "Buy cart retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
