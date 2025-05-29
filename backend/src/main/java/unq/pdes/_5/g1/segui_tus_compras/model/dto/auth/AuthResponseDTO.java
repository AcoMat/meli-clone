package unq.pdes._5.g1.segui_tus_compras.model.dto.auth;

public class AuthResponseDTO {
    public final UserDTO user;
    public final String token;

    public AuthResponseDTO(UserDTO user, String token) {
        this.user = user;
        this.token = token;
    }
}