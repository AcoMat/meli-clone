package unq.pdes._5.g1.segui_tus_compras.service.user;

import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.exception.UserNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.purchase.Purchase;
import unq.pdes._5.g1.segui_tus_compras.model.User;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.purchase.PurchaseItem;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;
import unq.pdes._5.g1.segui_tus_compras.service.product.ProductService;

import java.util.List;

@Service
public class UserService {

    private final UsersRepository _usersRepository;
    private final ProductService _productService;

    public UserService(UsersRepository usersRepository, ProductService productService) {
        this._usersRepository = usersRepository;
        this._productService = productService;
    }

    public User getUserById(Long id) {
        return _usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<Product> getFavorites(Long userId) {
        User user = getUserById(userId);
        return user.getFavorites();
    }

    public List<Purchase> getPurchases(Long userId) {
        User user = getUserById(userId);
        return user.getPurchases();
    }

    public List<Product> toggleFavorite(Long userId, String productId) {
        Product product = _productService.getProductById(productId);
        User user = getUserById(userId);
        user.toggleFavorite(product);
        _usersRepository.save(user);
        return user.getFavorites();
    }

    public void addPurchase(Long userId, List<PurchaseItem> items) {
        User user = getUserById(userId);
        user.addPurchase(new Purchase(user, items));
        _usersRepository.save(user);
    }

    public Boolean userBoughtProduct(Long userId, String productId) {
        User user = getUserById(userId);
        return user.getPurchases().stream()
                .anyMatch(purchase -> purchase.getItems().stream()
                        .anyMatch(item -> item.getProductId().equals(productId)));
    }
}
