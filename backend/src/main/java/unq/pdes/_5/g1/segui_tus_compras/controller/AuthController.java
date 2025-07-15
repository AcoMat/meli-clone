package unq.pdes._5.g1.segui_tus_compras.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.metrics.auth.AuthMetricsService;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.auth.AuthResponseDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.auth.LoginCredentials;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.auth.RegisterData;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.user.BasicUserDto;
import unq.pdes._5.g1.segui_tus_compras.service.auth.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthMetricsService authMetricsService;

    public AuthController(AuthService authService, AuthMetricsService authMetricsService) {
        this.authService = authService;
        this.authMetricsService = authMetricsService;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user and returns the user data with an authorization token.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid registration data or user already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<BasicUserDto> register(@Valid @RequestBody RegisterData data) {
        AuthResponseDTO newUser = authService.register(data);
        authMetricsService.incrementUserRegistration();
        return ResponseEntity.ok().header("Authorization", "Bearer " + newUser.token()).body(newUser.user());
    }

    @Operation(summary = "Login user", description = "Authenticates a user and returns the user data with an authorization token.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User logged in successfully"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<BasicUserDto> login(@Valid @RequestBody LoginCredentials credentials) {
        AuthResponseDTO loggedUser = authService.login(credentials);
        authMetricsService.incrementUserLogin();
        return ResponseEntity.ok().header("Authorization", "Bearer " + loggedUser.token()).body(loggedUser.user());
    }
}