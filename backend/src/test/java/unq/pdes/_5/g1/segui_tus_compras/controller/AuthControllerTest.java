package unq.pdes._5.g1.segui_tus_compras.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // Create a test user for login tests
        usersRepository.save(new User(
                "Test",
                "User",
                "testuser@example.com",
                passwordEncoder.encode("password123")
        ));
    }

    @AfterEach
    void tearDown() {
        usersRepository.deleteAll();
    }

    //  REGISTER

    @Test
    void shouldReturn200WhenRegisterWithValidData() throws Exception {
        // Arrange
        String registerRequestJson = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "johndoe@example.com",
                    "password": "password123"
                }
                """;

        // Act and Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequestJson))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(header().string("Authorization", org.hamcrest.Matchers.startsWith("Bearer ")))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));
    }

    @Test
    void shouldReturn400WhenRegisterWithInvalidJson() throws Exception {
        // Arrange
        String invalidJson = "{invalid json}";

        // Act and Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenRegisterWithAlreadyExistingEmail() throws Exception {
        // Arrange
        String registerRequestJson = """
                {
                    "firstName": "Another",
                    "lastName": "User",
                    "email": "testuser@example.com",
                    "password": "password456"
                }
                """;

        // Act and Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("An user with this email already exists"));
    }

    @Test
    void shouldReturn400WhenRegisterWithInvalidEmail() throws Exception {
        // Arrange
        String registerRequestJson = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "invalid-email",
                    "password": "password123"
                }
                """;

        // Act and Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenRegisterWithTooShortPassword() throws Exception {
        // Arrange
        String registerRequestJson = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "johndoe@example.com",
                    "password": "123"
                }
                """;

        // Act and Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenRegisterWithMissingFields() throws Exception {
        // Arrange
        String registerRequestJson = """
                {
                    "firstName": "John"
                }
                """;

        // Act and Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequestJson))
                .andExpect(status().isBadRequest());
    }


    //  LOGIN

    @Test
    void shouldReturn200WhenLoginWithValidCredentials() throws Exception {
        // Arrange
        String loginRequestJson = """
                {
                    "email": "testuser@example.com",
                    "password": "password123"
                }
                """;

        // Act and Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(header().string("Authorization", org.hamcrest.Matchers.startsWith("Bearer ")))
                .andExpect(jsonPath("$.email").value("testuser@example.com"))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("User"));
    }

    @Test
    void shouldReturn400WhenLoginWithInvalidJson() throws Exception {
        // Arrange
        String invalidJson = "{invalid json format}";

        // Act and Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenLoginWithInvalidEmail() throws Exception {
        // Arrange
        String loginRequestJson = """
                {
                    "email": "nonexistent@example.com",
                    "password": "password123"
                }
                """;

        // Act and Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Email or password is incorrect"));
    }

    @Test
    void shouldReturn400WhenLoginWithWrongPassword() throws Exception {
        // Arrange
        String loginRequestJson = """
                {
                    "email": "testuser@example.com",
                    "password": "wrongpassword"
                }
                """;

        // Act and Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Email or password is incorrect"));
    }

    @Test
    void shouldReturnAdminTokenWhenAdminLogsIn() throws Exception {
        // Arrange
        User adminUser = new User("Admin", "User", "admin@example.com", passwordEncoder.encode("admin123"), true);
        usersRepository.save(adminUser);

        String loginRequestJson = """
                {
                    "email": "admin@example.com",
                    "password": "admin123"
                }
                """;

        // Act and Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(header().string("Authorization", org.hamcrest.Matchers.startsWith("Bearer ")))
                .andExpect(jsonPath("$.email").value("admin@example.com"));
    }
}