package unq.pdes._5.g1.segui_tus_compras.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginCredentials {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    public String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    public String password;
}
