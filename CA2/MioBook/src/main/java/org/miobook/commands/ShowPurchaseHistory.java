package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.PurchaseHistoryRecord;
import org.miobook.services.UserServices;

@Getter
@Setter
public class ShowPurchaseHistory implements BaseCommand<PurchaseHistoryRecord> {

    @NotNull
    private String username;

    @Override
    public void validate() {
    }
    @Override
    public BaseResponse<PurchaseHistoryRecord> execute() {
        try {
            this.validate();
            PurchaseHistoryRecord data = UserServices.showPurchaseHistory(this);
            return new BaseResponse<>(true, "Purchase history retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
