package unq.pdes._5.g1.segui_tus_compras.service;

import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.exception.ErrorDuringPurchaseException;
import unq.pdes._5.g1.segui_tus_compras.exception.UserNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.Purchase;
import unq.pdes._5.g1.segui_tus_compras.model.User;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UsersRepository _usersRepository;
    private final ProductsService _productsService;

    public UserService(UsersRepository usersRepository, ProductsService productsService) {
        this._usersRepository = usersRepository;
        this._productsService = productsService;
    }

    public List<Product> getFavorites(Long userId) {
        User user = _usersRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user.getFavorites();
    }

    public List<Product> toggleFavorite(Long userId, String productId) {
        Product product = _productsService.getProductById(productId);
        if (product == null) {
            throw new ErrorDuringPurchaseException("Product not found");
        }
        User user = _usersRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        user.toggleFavorite(product);
        _usersRepository.save(user);
        return user.getFavorites();
    }

    public void postNewPurchase(Long userId, List<String> productsIds) {
        User user = _usersRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        List<Product> products = productsIds.stream()
                .map(_productsService::getProductById)
                .toList();
        if(products.isEmpty() || products.stream().anyMatch(Objects::isNull)) {
            throw new ErrorDuringPurchaseException("Some products are not valid");
        }
        Purchase purchase = new Purchase(user, products);

    }


}
