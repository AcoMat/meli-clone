package unq.pdes._5.g1.segui_tus_compras.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import unq.pdes._5.g1.segui_tus_compras.exception.auth.AlreadyExistingUserException;
import unq.pdes._5.g1.segui_tus_compras.exception.auth.WrongCredentialsException;
import unq.pdes._5.g1.segui_tus_compras.mapper.Mapper;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.AuthResponseDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.LoginCredentials;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.RegisterData;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.UserDTO;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;
import unq.pdes._5.g1.segui_tus_compras.security.JwtTokenProvider;
import unq.pdes._5.g1.segui_tus_compras.service.auth.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private Mapper mapper;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(usersRepository, mapper, jwtTokenProvider);
    }

    @Test
    void testRegisterSuccess() {
        // Arrange
        RegisterData registerData = new RegisterData(
            "John", 
            "Doe", 
            "john@example.com", 
            "password123"
        );

        User savedUser = new User("John", "Doe", "john@example.com", "encoded_password");
        // Need to set ID using reflection since there's no setter
        try {
            var idField = User.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(savedUser, 1L);
        } catch (Exception e) {
            fail("Failed to set up test data");
        }

        UserDTO userDTO = new UserDTO( "John", "Doe", "john@example.com");
        String token = "jwt_token";

        when(usersRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(usersRepository.save(any(User.class))).thenReturn(savedUser);
        when(mapper.toDTO(any(User.class))).thenReturn(userDTO);
        when(jwtTokenProvider.generateToken(1L)).thenReturn(token);

        // Act
        AuthResponseDTO response = authService.register(registerData);

        // Assert
        assertNotNull(response);
        assertEquals(userDTO, response.user());
        assertEquals(token, response.token());
        verify(usersRepository).existsByEmail("john@example.com");
        verify(usersRepository).save(any(User.class));
        verify(jwtTokenProvider).generateToken(1L);
    }

    @Test
    void testRegisterWithExistingEmail() {
        // Arrange
        RegisterData registerData = new RegisterData(
            "Existing", 
            "User", 
            "existing@example.com", 
            "password123"
        );

        when(usersRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(AlreadyExistingUserException.class, () -> authService.register(registerData));
        verify(usersRepository).existsByEmail("existing@example.com");
        verify(usersRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        LoginCredentials credentials = new LoginCredentials("john@example.com", "password123");

        User user = new User("John", "Doe", "john@example.com", new BCryptPasswordEncoder().encode("password123"));
        // Set ID using reflection
        try {
            var idField = User.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(user, 1L);
        } catch (Exception e) {
            fail("Failed to set up test data");
        }

        UserDTO userDTO = new UserDTO("John", "Doe", "john@example.com");
        String token = "jwt_token";

        when(usersRepository.findByEmail("john@example.com")).thenReturn(user);
        when(mapper.toDTO(user)).thenReturn(userDTO);
        when(jwtTokenProvider.generateToken(1L)).thenReturn(token);

        // Act
        AuthResponseDTO response = authService.login(credentials);

        // Assert
        assertNotNull(response);
        assertEquals(userDTO, response.user());
        assertEquals(token, response.token());
        verify(usersRepository).findByEmail("john@example.com");
        verify(jwtTokenProvider).generateToken(1L);
    }

    @Test
    void testLoginWithIncorrectPassword() {
        // Arrange
        LoginCredentials credentials = new LoginCredentials("john@example.com", "wrongpassword");

        User user = new User("John", "Doe", "john@example.com", new BCryptPasswordEncoder().encode("password123"));

        when(usersRepository.findByEmail("john@example.com")).thenReturn(user);

        // Act & Assert
        assertThrows(WrongCredentialsException.class, () -> authService.login(credentials));
        verify(usersRepository).findByEmail("john@example.com");
        verify(jwtTokenProvider, never()).generateToken(any());
    }

    @Test
    void testLoginWithNonExistentEmail() {
        // Arrange
        LoginCredentials credentials = new LoginCredentials("nonexistent@example.com", "password123");

        when(usersRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // Act & Assert
        assertThrows(WrongCredentialsException.class, () -> authService.login(credentials));
        verify(usersRepository).findByEmail("nonexistent@example.com");
        verify(jwtTokenProvider, never()).generateToken(any());
    }
}

