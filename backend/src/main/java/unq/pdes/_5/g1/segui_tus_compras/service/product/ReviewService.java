package unq.pdes._5.g1.segui_tus_compras.service.product;

import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.exception.NotBoughtYetException;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.product.Review;
import unq.pdes._5.g1.segui_tus_compras.service.purchase.PurchaseService;
import unq.pdes._5.g1.segui_tus_compras.service.user.UserService;

import java.util.List;

@Service
public class ReviewService {

    private final ProductService _productService;
    private final UserService _userService;
    private final PurchaseService _purchaseService;

    public ReviewService(ProductService productService, UserService userService, PurchaseService purchaseService) {
        this._productService = productService;
        this._userService = userService;
        this._purchaseService = purchaseService;
    }

    public List<Review> getReviewsFromProduct(String productId) {
        Product product = _productService.getProductById(productId);
        return product.getReviews();
    }

    public Product addReviewToProduct(String productId, Integer rating, String review, Long userId) {
        Product product = _productService.getProductById(productId);
        User user = _userService.getUserById(userId);
        if(!_userService.userBoughtProduct(userId, productId)) {
            throw new NotBoughtYetException();
        }
        Review newReview = new Review(product, user, rating, review);
        product.addReview(newReview);
        return _productService.updateProduct(product);
    }

}
