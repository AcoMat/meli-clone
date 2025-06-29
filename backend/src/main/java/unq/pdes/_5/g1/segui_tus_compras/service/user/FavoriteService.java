package unq.pdes._5.g1.segui_tus_compras.service.user;

import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.service.product.ProductService;

@Service
public class FavoriteService {

    private final UserService _userService;
    private final ProductService _productService;

    public FavoriteService(UserService userService, ProductService productService) {
        this._userService = userService;
        this._productService = productService;
    }

    public boolean toggleFavorite(Long userId, String productId) {
        var user = _userService.getUserById(userId);
        var product = _productService.getProductById(productId);

        boolean added = user.toggleFavorite(product);
        _userService.updateUser(user);
        return added;
    }

}
