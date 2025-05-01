package unq.pdes._5.g1.segui_tus_compras.model.dto.auth;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class RegisterData {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;
    @Length(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    @NotBlank(message = "Password confirmation is required")
    private String passwordConfirmation;
    @AssertTrue
    private boolean isPasswordConfirmed() {
        return password != null && password.equals(passwordConfirmation);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
}
