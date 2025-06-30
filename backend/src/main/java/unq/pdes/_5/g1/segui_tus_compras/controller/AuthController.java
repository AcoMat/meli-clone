package unq.pdes._5.g1.segui_tus_compras.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.auth.AuthResponseDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.auth.LoginCredentials;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.auth.RegisterData;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.user.BasicUserDto;
import unq.pdes._5.g1.segui_tus_compras.service.auth.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<BasicUserDto> register(@Valid @RequestBody RegisterData data) {
        AuthResponseDTO new_user = authService.register(data);
        return ResponseEntity.ok().header("Authorization", "Bearer " + new_user.token()).body(new_user.user());
    }

    @PostMapping("/login")
    public ResponseEntity<BasicUserDto> login(@Valid @RequestBody LoginCredentials credentials) {
        AuthResponseDTO logged_user = authService.login(credentials);
        return ResponseEntity.ok().header("Authorization", "Bearer " + logged_user.token()).body(logged_user.user());
    }
}