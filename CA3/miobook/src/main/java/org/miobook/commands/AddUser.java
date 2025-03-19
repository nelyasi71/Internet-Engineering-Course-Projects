package org.miobook.commands;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.miobook.models.Address;
import org.miobook.responses.BaseResponse;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.services.Services;
import org.miobook.services.UserServices;

@Getter
@Setter
public class AddUser implements BaseCommand<Void> {

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
    }

    @Override
    public void validate() {
        JsonValidator.validate(this);
        JsonValidator.validate(address);
    }
    @Override
    public BaseResponse<Void> execute(Services services) {
        try {
            this.validate();

            ((UserServices)services).addUser(this);
            return new BaseResponse<>(true, "User added successfully.", null);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
