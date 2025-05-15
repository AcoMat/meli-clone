package unq.pdes._5.g1.segui_tus_compras.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unq.pdes._5.g1.segui_tus_compras.exception.UserNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.purchase.Purchase;
import unq.pdes._5.g1.segui_tus_compras.model.User;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;
import unq.pdes._5.g1.segui_tus_compras.service.product.ProductService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UsersRepository usersRepository;
    private ProductService productService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        productService = mock(ProductService.class);
        userService = new UserService(usersRepository, productService);
    }

    @Test
    void getFavorites_UserExists_ReturnsFavorites() {
        User user = new User("John", "Doe", "john.doe@example.com", "password");
        Product product = mock(Product.class);
        user.getFavorites().add(product);

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

        List<Product> favorites = userService.getFavorites(1L);

        assertEquals(1, favorites.size());
        assertTrue(favorites.contains(product));
        verify(usersRepository, times(1)).findById(1L);
    }

    @Test
    void getFavorites_UserNotFound_ThrowsException() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getFavorites(1L));
        verify(usersRepository, times(1)).findById(1L);
    }

    @Test
    void getPurchases_UserExists_ReturnsPurchases() {
        User user = new User("John", "Doe", "john.doe@example.com", "password");
        Purchase purchase = mock(Purchase.class);
        user.getPurchases().add(purchase);

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

        List<Purchase> purchases = userService.getPurchases(1L);

        assertEquals(1, purchases.size());
        assertTrue(purchases.contains(purchase));
        verify(usersRepository, times(1)).findById(1L);
    }

    @Test
    void getPurchases_UserNotFound_ThrowsException() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getPurchases(1L));
        verify(usersRepository, times(1)).findById(1L);
    }

    @Test
    void toggleFavorite_UserAndProductExist_TogglesFavorite() {
        User user = new User("John", "Doe", "john.doe@example.com", "password");
        Product product = mock(Product.class);

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productService.getProductById("product1")).thenReturn(product);

        List<Product> favorites = userService.toggleFavorite(1L, "product1");

        assertTrue(favorites.contains(product));
        verify(usersRepository, times(1)).findById(1L);
        verify(productService, times(1)).getProductById("product1");
        verify(usersRepository, times(1)).save(user);
    }

    @Test
    void toggleFavorite_UserNotFound_ThrowsException() {
        Product product = mock(Product.class);

        when(usersRepository.findById(1L)).thenReturn(Optional.empty());
        when(productService.getProductById("product1")).thenReturn(product);

        assertThrows(UserNotFoundException.class, () -> userService.toggleFavorite(1L, "product1"));
        verify(usersRepository, times(1)).findById(1L);
    }

    //TODO: purchase tests
}
