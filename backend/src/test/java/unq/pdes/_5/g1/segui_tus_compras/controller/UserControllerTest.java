package unq.pdes._5.g1.segui_tus_compras.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api.ExternalProductDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;
import unq.pdes._5.g1.segui_tus_compras.repository.ProductsRepository;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;
import unq.pdes._5.g1.segui_tus_compras.security.JwtTokenProvider;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        // Clear repositories before each test
        usersRepository.deleteAll();
        productsRepository.deleteAll();
    }

    @Test
    void shouldReturn200WhenGetUserByIdWithValidToken() throws Exception {
        // Arrange
        User testUser = new User("John", "Doe", "test@email.com", passwordEncoder.encode("password123"));
        usersRepository.save(testUser);
        String token = jwtTokenProvider.generateToken(testUser.getId());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/profile")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUser.getId()));
    }

    @Test
    void shouldReturn401WhenGetUserByIdWithInvalidToken() throws Exception {
        // Arrange
        User testUser = new User("Jane", "Doe", "test@email.com", passwordEncoder.encode("password123"));
        usersRepository.save(testUser);
        String token = "Bearer eyJhbGciOiJIU.zI1NiIsInRX.DYLN6ZK8E";

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/profile")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn401WhenGetUserByIdWithoutToken() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/profile"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Missing Authorization header"));
    }

    @Test
    void shouldReturn200WhenGetUserFavoritesWithValidToken() throws Exception {
        // Arrange
        User testUser = new User("Jane", "Doe", "test@email.com", passwordEncoder.encode("password123"));
        ExternalProductDto externalProduct = new ExternalProductDto();
        externalProduct.id = "1L";
        externalProduct.name = "Test Product";
        Product favoriteProduct = new Product(externalProduct);
        testUser.toggleFavorite(favoriteProduct);
        productsRepository.save(favoriteProduct);
        usersRepository.save(testUser);

        String token = jwtTokenProvider.generateToken(testUser.getId());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/favorites")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(favoriteProduct.getId()));
    }

}