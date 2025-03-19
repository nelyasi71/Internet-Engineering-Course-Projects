package org.miobook.commands;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.PurchasedBooksRecord;
import org.miobook.services.Services;
import org.miobook.services.UserServices;

@Getter
@Setter
public class ShowPurchasedBooks implements BaseCommand<PurchasedBooksRecord> {

    @NotNull
    private String username;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }
    @Override
    public BaseResponse<PurchasedBooksRecord> execute(Services services) {
        try {
            this.validate();
            PurchasedBooksRecord data = ((UserServices) services).showPurchasedBooks(this);
            return new BaseResponse<>(true, "Purchased books retrieved successfully.", data);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
