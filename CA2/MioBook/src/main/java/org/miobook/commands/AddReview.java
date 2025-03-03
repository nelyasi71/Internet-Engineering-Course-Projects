package org.miobook.commands;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.miobook.repositories.Repositories;
import org.miobook.services.JsonValidator;
import jakarta.validation.constraints.Digits;

@Getter
@Setter
public class AddReview extends BaseCommand {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Username can only contain letters, numbers, dash and underscores")
    private String username;

    @NotNull
    private String title;

    @NotNull
    private String comment;

    @NotNull
    @Range(min = 1, max = 5)
    @Digits(integer = 1, fraction = 0)
    private Integer rate;

    public AddReview() {
        super();
    }

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public void execute() {
        Repositories.bookRepository.addReview(this);
    }
}
