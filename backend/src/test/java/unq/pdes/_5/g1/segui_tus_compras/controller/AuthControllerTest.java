package unq.pdes._5.g1.segui_tus_compras.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import unq.pdes._5.g1.segui_tus_compras.exception.auth.WrongCredentialsException;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.AuthResponseDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.LoginCredentials;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.RegisterData;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.UserDTO;
import unq.pdes._5.g1.segui_tus_compras.service.auth.AuthService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class AuthControllerTestConfiguration {
        @Bean
        public AuthService authService() {
            return Mockito.mock(AuthService.class);
        }
    }

    //  REGISTER
    @Test
    void shouldReturn400WhenRegisterWithInvalidJson() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")) // Using a clearly malformed JSON
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn200WhenRegisterWithValidData() throws Exception {
        RegisterData registerData = new RegisterData("Test", "User", "test@example.com", "password123");
        UserDTO userDTO = new UserDTO("Test", "User", "test@example.com");
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(userDTO, "token");

        when(authService.register(any(RegisterData.class))).thenReturn(authResponseDTO);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerData)))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer token"))
                // Corrected JSONPath to access fields directly from UserDTO
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.firstName").value("Test"));
    }

    @Test
    void shouldReturn400WhenRegisterWithInvalidEmail() throws Exception {
        RegisterData registerData = new RegisterData("Test", "User", "invalid-email", "password123");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenRegisterWithTooShortPassword() throws Exception {
        RegisterData registerData = new RegisterData("Test", "User", "test@example.com", "short");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenRegisterWithMissingFields() throws Exception {
        RegisterData registerData = new RegisterData("Test", "User", null, "password123");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerData)))
                .andExpect(status().isBadRequest());
    }

    //  LOGIN

    @Test
    void shouldReturn400WhenLoginWithInvalidJson() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")) // Using a clearly malformed JSON
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn200WhenLoginWithValidCredentials() throws Exception {
        LoginCredentials loginCredentials = new LoginCredentials("test@example.com", "password123");
        UserDTO userDTO = new UserDTO("Test", "User", "test@example.com");
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(userDTO, "token");

        when(authService.login(any(LoginCredentials.class))).thenReturn(authResponseDTO);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginCredentials)))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer token"))
                // Corrected JSONPath to access fields directly from UserDTO
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void shouldReturn400WhenLoginWithInvalidEmail() throws Exception {
        LoginCredentials loginCredentials = new LoginCredentials("invalid-email", "password123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginCredentials)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenLoginWithWrongPassword() throws Exception {
        LoginCredentials loginCredentials = new LoginCredentials("test@example.com", "wrongpassword");
        when(authService.login(any(LoginCredentials.class))).thenThrow(new WrongCredentialsException("Bad credentials"));

         mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginCredentials)))
                .andExpect(status().isUnauthorized());
    }
}