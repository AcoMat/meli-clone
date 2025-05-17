package unq.pdes._5.g1.segui_tus_compras.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unq.pdes._5.g1.segui_tus_compras.exception.UserNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.purchase.Purchase;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.product.Review;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UsersRepository usersRepository;
    private UserService userService;
    private User testUser;
    private final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        usersRepository = mock(UsersRepository.class);
        userService = new UserService(usersRepository);
        
        // Create test user
        testUser = new User("John", "Doe", "john@example.com", "password");
        // Use reflection to set the ID since it's normally generated
        try {
            java.lang.reflect.Field idField = User.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(testUser, USER_ID);
        } catch (Exception e) {
            fail("Failed to set user ID: " + e.getMessage());
        }
    }

    @Test
    void getUserById_ExistingUser_ReturnsUser() {
        // Arrange
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        
        // Act
        User result = userService.getUserById(USER_ID);
        
        // Assert
        assertNotNull(result);
        assertEquals(USER_ID, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void getUserById_NonExistingUser_ThrowsException() {
        // Arrange
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.empty());
        
        // Act & Assert
        UserNotFoundException exception = assertThrows(
            UserNotFoundException.class,
            () -> userService.getUserById(USER_ID)
        );
        assertEquals("User not found", exception.getMessage());
        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void updateUser_ValidUser_ReturnsUpdatedUser() {
        // Arrange
        when(usersRepository.existsById(USER_ID)).thenReturn(true);
        when(usersRepository.save(testUser)).thenReturn(testUser);

        // Act
        User result = userService.updateUser(testUser);
        
        // Assert
        assertNotNull(result);
        assertEquals(testUser, result);
        verify(usersRepository).save(testUser);
    }

    @Test
    void getUserFavorites_UserWithFavorites_ReturnsFavorites() {
        // Arrange
        Product product = new Product();
        List<Product> favorites = new ArrayList<>();
        favorites.add(product);
        
        // Use reflection to set favorites
        try {
            java.lang.reflect.Field favoritesField = User.class.getDeclaredField("favorites");
            favoritesField.setAccessible(true);
            favoritesField.set(testUser, favorites);
        } catch (Exception e) {
            fail("Failed to set favorites: " + e.getMessage());
        }
        
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        
        // Act
        List<Product> result = userService.getUserFavorites(USER_ID);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void getUserFavorites_UserWithNoFavorites_ReturnsEmptyList() {
        // Arrange
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        
        // Act
        List<Product> result = userService.getUserFavorites(USER_ID);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void getUserFavorites_NonExistingUser_ThrowsException() {
        // Arrange
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserFavorites(USER_ID));
        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void getUserPurchases_UserWithPurchases_ReturnsPurchases() {
        // Arrange
        Purchase purchase = new Purchase();
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase);
        
        // Use reflection to set purchases
        try {
            java.lang.reflect.Field purchasesField = User.class.getDeclaredField("purchases");
            purchasesField.setAccessible(true);
            purchasesField.set(testUser, purchases);
        } catch (Exception e) {
            fail("Failed to set purchases: " + e.getMessage());
        }
        
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        
        // Act
        List<Purchase> result = userService.getUserPurchases(USER_ID);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(purchase, result.get(0));
        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void getUserPurchases_UserWithNoPurchases_ReturnsEmptyList() {
        // Arrange
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        
        // Act
        List<Purchase> result = userService.getUserPurchases(USER_ID);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void getUserPurchases_NonExistingUser_ThrowsException() {
        // Arrange
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserPurchases(USER_ID));
        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void getUserReviews_UserWithReviews_ReturnsReviews() {
        // Arrange
        Review review = new Review();
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        
        // Use reflection to set reviews
        try {
            java.lang.reflect.Field reviewsField = User.class.getDeclaredField("reviews");
            reviewsField.setAccessible(true);
            reviewsField.set(testUser, reviews);
        } catch (Exception e) {
            fail("Failed to set reviews: " + e.getMessage());
        }
        
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        
        // Act
        List<Review> result = userService.getUserReviews(USER_ID);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(review, result.get(0));
        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void getUserReviews_UserWithNoReviews_ReturnsEmptyList() {
        // Arrange
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        
        // Act
        List<Review> result = userService.getUserReviews(USER_ID);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void getUserReviews_NonExistingUser_ThrowsException() {
        // Arrange
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserReviews(USER_ID));
        verify(usersRepository).findById(USER_ID);
    }
}
