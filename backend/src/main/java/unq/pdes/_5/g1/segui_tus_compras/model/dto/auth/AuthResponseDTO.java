package unq.pdes._5.g1.segui_tus_compras.model.dto.auth;

public class AuthResponseDTO {
    private final UserDTO user;
    private final String token;

    public AuthResponseDTO(UserDTO user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}