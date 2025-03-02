package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class AddCredit extends BaseCommand {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Username can only contain letters, numbers, dash and underscores")
    private String username;

    @NotNull
    @Range(min = 1000)
    private int title;

    @Override
    public void validate() {
    }

    @Override
    public void execute() {

    }
}
