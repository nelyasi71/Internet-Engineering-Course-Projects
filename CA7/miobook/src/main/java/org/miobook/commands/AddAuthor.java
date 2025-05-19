package org.miobook.commands;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.miobook.Exception.MioBookException;
import org.miobook.responses.BaseResponse;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.services.AuthorServices;
import org.miobook.services.Services;

import java.time.LocalDate;

@Getter
@Setter
public class AddAuthor implements BaseCommand<Void> {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Username can only contain letters, numbers, underscores, and dots")
    private String username;

    @NotNull
    private String name;

    @NotNull
    private String penName;

    private String nationality;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate born;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate death;

    public AddAuthor() {
        super();

    }

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<Void> execute(Services services) {
        try {
            this.validate();
            ((AuthorServices) services).addAuthor(this);
            return new BaseResponse<>(true, "Author added successfully.", null);
        } catch (MioBookException exp) {
            return BaseResponse.fromMioBookException(exp);
        }
    }
}