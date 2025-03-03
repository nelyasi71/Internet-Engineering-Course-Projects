package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.responses.BaseResponse;

@Getter
@Setter
public class ShowPurchaseHistory implements BaseCommand<Void> {

    @NotNull
    private String username;

    @Override
    public void validate() {
    }
    @Override
    public BaseResponse<Void> execute() {

        return null;
    }
}
