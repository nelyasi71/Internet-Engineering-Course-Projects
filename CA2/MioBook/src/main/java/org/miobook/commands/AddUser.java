package org.miobook.commands;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.miobook.models.Address;
import org.miobook.repositories.Repositories;
import org.miobook.services.JsonValidator;

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
    public void validate() {
        JsonValidator.validate(this);
        JsonValidator.validate(address);
    }
    @Override
    public void execute() {
        Repositories.userRepository.addUser(this);
    }
}
