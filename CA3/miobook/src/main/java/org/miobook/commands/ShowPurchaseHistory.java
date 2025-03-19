package org.miobook.commands;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.PurchaseHistoryRecord;
import org.miobook.services.Services;
import org.miobook.services.UserServices;

@Getter
@Setter
public class ShowPurchaseHistory implements BaseCommand<PurchaseHistoryRecord> {

    @NotNull
    private String username;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }
    @Override
    public BaseResponse<PurchaseHistoryRecord> execute(Services services) {
        try {
            this.validate();
            PurchaseHistoryRecord data = UserServices.showPurchaseHistory(this);
            return new BaseResponse<>(true, "Purchase history retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
