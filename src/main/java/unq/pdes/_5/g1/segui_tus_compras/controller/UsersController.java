package unq.pdes._5.g1.segui_tus_compras.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.model.dto.UserDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.UserRegisterDTO;
import unq.pdes._5.g1.segui_tus_compras.service.UsersService;
import unq.pdes._5.g1.segui_tus_compras.util.ApiResponse;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody UserRegisterDTO userDTO) {
        UserDTO new_user = usersService.register(userDTO);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User registered successfully", new_user, null);
        return ResponseEntity.ok(response);
    }
}

