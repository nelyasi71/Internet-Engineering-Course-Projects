package org.miobook.commands;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.miobook.responses.BaseResponse;

@Getter
@Setter
public class AddReview implements BaseCommand<Void> {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Username can only contain letters, numbers, dash and underscores")
    private String username;

    @NotNull
    private String title;

    @NotNull
    private String comment;

    @NotNull
    @Range(min = 1, max = 5)
    private int rate;

    @Override
    public void validate() {
    }

    @Override
    public BaseResponse<Void> execute() {
        return null;

    }
}
