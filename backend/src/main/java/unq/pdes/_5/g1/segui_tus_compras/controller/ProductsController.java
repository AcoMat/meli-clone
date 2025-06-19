package unq.pdes._5.g1.segui_tus_compras.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.model.dto.ReviewDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.SearchDTO;
import unq.pdes._5.g1.segui_tus_compras.model.product.Commentary;
import unq.pdes._5.g1.segui_tus_compras.model.dto.CommentDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.PagingDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.product.Review;
import unq.pdes._5.g1.segui_tus_compras.security.annotation.NeedsAuth;
import unq.pdes._5.g1.segui_tus_compras.service.product.CommentService;
import unq.pdes._5.g1.segui_tus_compras.service.product.ProductService;
import unq.pdes._5.g1.segui_tus_compras.service.product.ReviewService;
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
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<SearchDTO> searchProductByName(
            @RequestParam String q,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        List<Product> productsSearch = productService.searchProducts(q, offset, limit);
        PagingDto paging = new PagingDto(offset, limit, productsSearch.size());
        return ResponseEntity.ok(new SearchDTO(paging, q, productsSearch));
    }

    @GetMapping("/{productId}/comments")
    public ResponseEntity<List<Commentary>> getCommentsFromProduct(@PathVariable String productId) {
        return ResponseEntity.ok(commentService.getProductCommentaries(productId));
    }

    @NeedsAuth
    @PostMapping("/{productId}/comments")
    public ResponseEntity<String> addCommentToProduct(
            @PathVariable String productId,
            @Valid @RequestBody CommentDto commentDto,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("userId");
        commentService.addCommentToProduct(productId, commentDto.comment, userId);
        return ResponseEntity.ok("Comment added successfully");
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewsFromProduct(@PathVariable String productId) {
        return ResponseEntity.ok(reviewService.getProductReviews(productId));
    }

    @NeedsAuth
    @PostMapping("/{productId}/reviews")
    public ResponseEntity<String> postReviewToProduct(
            @PathVariable String productId,
            @Valid @RequestBody ReviewDto reviewDto,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("userId");
        reviewService.addReviewToProduct(productId, reviewDto.rating, reviewDto.review , userId);
        return ResponseEntity.ok("Review added successfully");
    }
}
