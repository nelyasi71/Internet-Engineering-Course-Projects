package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.PurchaseHistoryRecord;
import org.miobook.responses.PurchasedBookItemRecord;
import org.miobook.responses.PurchasedBooksRecord;
import org.miobook.services.UserServices;

@Getter
@Setter
public class ShowPurchasedBooks implements BaseCommand<PurchasedBooksRecord> {

    @NotNull
    private String username;

    @Override
    public void validate() {
    }
    @Override
    public BaseResponse<PurchasedBooksRecord> execute() {
        try {
            this.validate();
            PurchasedBooksRecord data = UserServices.showPurchasedBooks(this);
            return new BaseResponse<>(true, "Purchased books retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
