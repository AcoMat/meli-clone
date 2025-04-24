package unq.pdes._5.g1.segui_tus_compras.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.model.dto.UserDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.UserLoginDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.UserRegisterDTO;
import unq.pdes._5.g1.segui_tus_compras.service.AuthService;
import unq.pdes._5.g1.segui_tus_compras.util.ApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@Valid @RequestBody UserLoginDTO userDTO) {
        UserDTO loggued_user = authService.login(userDTO);
        // Implement login logic here
        return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", loggued_user, null));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody UserRegisterDTO userDTO) {
        UserDTO new_user = authService.register(userDTO);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User registered successfully", new_user, null);
        return ResponseEntity.ok(response);
    }
}

