package org.miobook.commands;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.miobook.models.Address;
import org.miobook.repositories.Repositories;
import org.miobook.services.JsonValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AddAuthor extends BaseCommand {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Username can only contain letters, numbers, underscores, and dots")
    private String username;

    @NotNull
    private String name;

    @NotNull
    private String penName;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate born;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate death;

    public AddAuthor() {
        super();

    }

    @Override
    public boolean validate() {
        JsonValidator.validate(this);
        return true;
    }

    @Override
    public void execute() {
        Repositories.authorRepository.addAuthor(this);
    }
}