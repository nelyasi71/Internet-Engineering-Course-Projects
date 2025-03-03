package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.PurchaseCartRecord;
import org.miobook.services.JsonValidator;

@Getter
@Setter
public class PurchaseCart implements BaseCommand<PurchaseCartRecord> {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Username can only contain letters, numbers, dash and underscores")
    private String username;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<PurchaseCartRecord> execute() {
        try {
            this.validate();
            PurchaseCartRecord data = Repositories.userRepository.purchaseCart(this);
            return new BaseResponse<>(true, "Purchase completed successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
