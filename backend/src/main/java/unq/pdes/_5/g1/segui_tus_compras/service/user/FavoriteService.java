package unq.pdes._5.g1.segui_tus_compras.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.pdes._5.g1.segui_tus_compras.service.product.ProductService;

@Service
public class FavoriteService {

    private final UserService userService;
    private final ProductService productService;

    public FavoriteService(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    public boolean toggleFavorite(Long userId, String productId) {
        var user = userService.getUserById(userId);
        var product = productService.getProductById(productId);

        boolean added = user.toggleFavorite(product);
        userService.updateUser(user);
        return added;
    }

}
