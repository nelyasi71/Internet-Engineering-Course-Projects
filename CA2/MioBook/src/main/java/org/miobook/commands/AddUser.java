package org.miobook.commands;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.miobook.models.Address;
import org.miobook.repositories.Repositories;
import org.miobook.repositories.UserRepository;
import org.miobook.services.JsonValidator;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddUser extends BaseCommand {

    @Pattern(regexp = "^(customer|admin)$", message = "Role must be either 'customer' or 'admin'")
    @NotNull
    private String role;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Username can only contain letters, numbers, dash and underscores")
    private String username;

    @Size(min = 4)
    private String password;

    @Email
    @NotNull
    private String email;

    @NotNull
    private Address address;

    public AddUser() {
        super();
    }

    @Override
    public boolean validate() {
        JsonValidator.validate(this);
        JsonValidator.validate(address);
        return true;
    }
    @Override
    public void execute() {
        Repositories.userRepository.addUser(this);
    }
}
