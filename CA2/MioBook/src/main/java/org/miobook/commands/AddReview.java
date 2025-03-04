package org.miobook.commands;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.miobook.infrastructure.IntDeserializer;
import org.miobook.repositories.Repositories;
import org.miobook.infrastructure.JsonValidator;
import jakarta.validation.constraints.Digits;
import org.miobook.responses.BaseResponse;
import org.miobook.services.BookServices;

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
    @JsonDeserialize(using = IntDeserializer.class)
    private Integer rate;

    public AddReview() {
        super();
    }

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<Void> execute() {
        try {
            this.validate();
            BookServices.addReview(this);
            return new BaseResponse<>(true, "Review added successfully.", null);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }

    }
}
