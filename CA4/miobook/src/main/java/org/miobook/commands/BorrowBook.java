package org.miobook.commands;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.miobook.Exception.MioBookException;
import org.miobook.responses.BaseResponse;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.services.CartServices;
import org.miobook.services.Services;


@Getter
@Setter
public class BorrowBook implements BaseCommand<Void> {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Username can only contain letters, numbers, dash and underscores")
    private String username;

    @NotNull
    private String title;

    @NotNull
    @Range(min = 1, max = 9)
    private Integer days;

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<Void> execute(Services services) {
        this.validate();
        try {
            this.validate();
            ((CartServices) services).borrowBook(this);
            return new BaseResponse<>(true, "Added borrowed book to cart.", null);
        } catch (MioBookException exp) {
            return BaseResponse.fromMioBookException(exp);
        }
    }
}
