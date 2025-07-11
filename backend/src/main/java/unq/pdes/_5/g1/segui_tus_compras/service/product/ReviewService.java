package unq.pdes._5.g1.segui_tus_compras.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.pdes._5.g1.segui_tus_compras.exception.purchase.NotBoughtYetException;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.product.Review;
import unq.pdes._5.g1.segui_tus_compras.repository.ReviewsRepository;
import unq.pdes._5.g1.segui_tus_compras.service.purchase.PurchaseService;
import unq.pdes._5.g1.segui_tus_compras.service.user.UserService;

import java.util.List;


@Service
public class ReviewService {

    private final ProductService productService;
    private final UserService userService;
    private final PurchaseService purchaseService;
    private final ReviewsRepository reviewsRepository;

    public ReviewService(
            ProductService productService,
            UserService userService,
            PurchaseService purchaseService,
            ReviewsRepository reviewsRepository
    ) {
        this.productService = productService;
        this.userService = userService;
        this.purchaseService = purchaseService;
        this.reviewsRepository = reviewsRepository;
    }

    public List<Review> getProductReviews(String productId) {
        return productService.getProductById(productId).getReviews();
    }

    @Transactional
    public  void addReviewToProduct(String productId, Integer rating, String review, Long userId) {
        Product product = productService.getProductById(productId);
        User user = userService.getUserById(userId);
        if(!purchaseService.userBoughtProduct(userId, productId)) {
            throw new NotBoughtYetException();
        }
        product.getReviews().stream()
                .filter(r -> r.getUser().getId().equals(userId))
                .findFirst().ifPresent(reviewsRepository::delete);
        product.addReview(new Review(product, user, rating, review));
        productService.updateProduct(product);
    }

}
