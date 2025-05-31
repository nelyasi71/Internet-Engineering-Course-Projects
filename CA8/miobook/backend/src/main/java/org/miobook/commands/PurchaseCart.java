package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.miobook.Exception.MioBookException;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.PurchaseCartRecord;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.services.CartServices;
import org.miobook.services.Services;

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
    public BaseResponse<PurchaseCartRecord> execute(Services services) {
        try {
            this.validate();
            PurchaseCartRecord data = ((CartServices) services).purchaseCart(this);
            return new BaseResponse<>(true, "Purchase completed successfully.", data);
        } catch (MioBookException exp) {
            return BaseResponse.fromMioBookException(exp);
        }
    }
}
