package unq.pdes._5.g1.segui_tus_compras.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.AuthResponseDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.UserDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.LoginCredentials;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.RegisterData;
import unq.pdes._5.g1.segui_tus_compras.service.AuthService;
import unq.pdes._5.g1.segui_tus_compras.util.ApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody RegisterData data) {
        AuthResponseDTO new_user = authService.register(data);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User registered successfully", new_user.getUser(), null);
        return ResponseEntity.ok().header("Authorization", "Bearer " + new_user.getToken()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@Valid @RequestBody LoginCredentials credentials) {
        AuthResponseDTO logged_user = authService.login(credentials);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User logged successfully", logged_user.getUser(), null);
        return ResponseEntity.ok().header("Authorization", "Bearer " + logged_user.getToken()).body(response);
    }
}

