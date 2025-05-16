package unq.pdes._5.g1.segui_tus_compras.service.product;

import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.model.product.Commentary;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.service.user.UserService;

import java.util.List;

@Service
public class CommentService {
    private final ProductService _productService;
    private final UserService _userService;

    public CommentService(ProductService productService, UserService userService) {
        this._productService = productService;
        this._userService = userService;
    }

    public Product addCommentToProduct(String productId, String comment, Long userId) {
        User user = _userService.getUserById(userId);
        Product product = _productService.getProductById(productId);

        Commentary newCommentary = new Commentary(user, product, comment);
        product.addComment(newCommentary);
        return _productService.updateProduct(product);
    }

    public List<Commentary> getCommentariesFromProduct(String productId) {
        Product product = _productService.getProductById(productId);
        return product.getCommentaries();
    }
}
