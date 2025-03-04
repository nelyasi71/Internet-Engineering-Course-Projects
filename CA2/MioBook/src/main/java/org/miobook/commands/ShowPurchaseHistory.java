package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.PurchaseHistoryRecord;

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
            PurchaseHistoryRecord data = Repositories.userRepository.showPurchaseHistory(this);
            return new BaseResponse<>(true, "Purchase history retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
