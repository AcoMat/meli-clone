package unq.pdes._5.g1.segui_tus_compras.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.AuthResponseDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.UserDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.LoginCredentials;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.RegisterData;
import unq.pdes._5.g1.segui_tus_compras.service.auth.AuthService;
import unq.pdes._5.g1.segui_tus_compras.controller.model.ApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody RegisterData data) {
        AuthResponseDTO new_user = authService.register(data);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User registered successfully", new_user.user, null);
        return ResponseEntity.ok().header("Authorization", "Bearer " + new_user.token).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@Valid @RequestBody LoginCredentials credentials) {
        AuthResponseDTO logged_user = authService.login(credentials);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User logged successfully", logged_user.user, null);
        return ResponseEntity.ok().header("Authorization", "Bearer " + logged_user.token).body(response);
    }
}