package unq.pdes._5.g1.segui_tus_compras.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.model.dto.ReviewDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Commentary;
import unq.pdes._5.g1.segui_tus_compras.model.dto.CommentDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.PagingDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.product.Review;
import unq.pdes._5.g1.segui_tus_compras.security.annotation.NeedsAuth;
import unq.pdes._5.g1.segui_tus_compras.service.product.CommentService;
import unq.pdes._5.g1.segui_tus_compras.service.product.ProductService;
import unq.pdes._5.g1.segui_tus_compras.service.product.ReviewService;
import unq.pdes._5.g1.segui_tus_compras.controller.model.ApiResponse;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;
    private final CommentService commentService;
    private final ReviewService reviewService;

    public ProductsController(ProductService productService, CommentService commentService, ReviewService reviewService) {
        this.productService = productService;
        this.commentService = commentService;
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        ApiResponse<Product> response = new ApiResponse<>(true, "Product retrieved successfully", product, null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Product>>> searchProductByName(
            @RequestParam String q,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        List<Product> productsSearch = productService.searchProducts(q, offset, limit);
        if(productsSearch == null || productsSearch.isEmpty()) {
            ApiResponse<List<Product>> response = new ApiResponse<>(false, "No products found", null, null);
            return ResponseEntity.status(404).body(response);
        }
        ApiResponse<List<Product>> response = new ApiResponse<>(
                true,
                "Products retrieved successfully",
                new PagingDto(productsSearch.size(), limit, offset),
                productsSearch);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}/comments")
    public ResponseEntity<ApiResponse<List<Commentary>>> getCommentsFromProduct(@PathVariable String productId) {
        ApiResponse<List<Commentary>> response =
                new ApiResponse<>(true, "Commentaries retrieved successfully", null, commentService.getCommentariesFromProduct(productId));
        return ResponseEntity.ok(response);
    }

    @NeedsAuth
    @PostMapping("/{productId}/comments")
    public ResponseEntity<ApiResponse<Product>> addCommentToProduct(
            @PathVariable String productId,
            @Valid @RequestBody CommentDto commentDto,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("userId");
        ApiResponse<Product> response =
                new ApiResponse<>(true,
                        "Comment added successfully",
                        null,
                        commentService.addCommentToProduct(productId, commentDto.comment, userId)
                );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<ApiResponse<List<Review>>> getReviewsFromProduct(@PathVariable String productId) {
        ApiResponse<List<Review>> response =
                new ApiResponse<>(true, "Reviews retrieved successfully", null, reviewService.getReviewsFromProduct(productId));
        return ResponseEntity.ok(response);
    }

    @NeedsAuth
    @PostMapping("/{productId}/reviews")
    public ResponseEntity<ApiResponse<Product>> postReviewToProduct(
            @PathVariable String productId,
            @Valid @RequestBody ReviewDto reviewDto,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("userId");
        ApiResponse<Product> response =
                new ApiResponse<>(true, "Review posted successfully", null, reviewService.addReviewToProduct(productId, reviewDto.rating, reviewDto.review , userId));
        return ResponseEntity.ok(response);
    }
}
