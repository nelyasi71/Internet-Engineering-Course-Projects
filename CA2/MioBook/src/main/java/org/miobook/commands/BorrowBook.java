package org.miobook.commands;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.miobook.repositories.Repositories;
import org.miobook.responses.BaseResponse;
import org.miobook.services.JsonValidator;


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
    private int days;


    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<Void> execute() {
        Repositories.userRepository.borrowBook(this);
        return null;

    }
}
