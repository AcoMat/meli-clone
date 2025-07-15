package unq.pdes._5.g1.segui_tus_compras.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.user.ReviewDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.search.SearchDTO;
import unq.pdes._5.g1.segui_tus_compras.model.product.Question;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.user.QuestionsDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.search.PagingDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.product.Review;
import unq.pdes._5.g1.segui_tus_compras.security.annotation.NeedsAuth;
import unq.pdes._5.g1.segui_tus_compras.service.product.QuestionsService;
import unq.pdes._5.g1.segui_tus_compras.service.product.ProductService;
import unq.pdes._5.g1.segui_tus_compras.service.product.ReviewService;
import unq.pdes._5.g1.segui_tus_compras.metrics.product.ProductMetricsService;
import jakarta.validation.Valid;
import unq.pdes._5.g1.segui_tus_compras.service.search.SearchService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;
    private final QuestionsService questionsService;
    private final ReviewService reviewService;
    private final ProductMetricsService productMetricsService;
    private final SearchService searchService;

    public ProductsController(
            ProductService productService,
            QuestionsService questionsService,
            ReviewService reviewService,
            ProductMetricsService productMetricsService,
            SearchService searchService
    ) {
        this.productService = productService;
        this.questionsService = questionsService;
        this.reviewService = reviewService;
        this.productMetricsService = productMetricsService;
        this.searchService = searchService;
    }

    @Operation(summary = "Get product by ID", description = "Returns a product by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@Parameter(description = "Product ID") @PathVariable String id) {
        Product product = productService.getProductById(id);
        productMetricsService.incrementProductView(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Search products by name", description = "Searches products using keywords.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search successful")
    })
    @GetMapping("/search")
    public ResponseEntity<SearchDTO> searchProductsByName(
            @Parameter(description = "Search query") @RequestParam String q,
            @Parameter(description = "Offset for pagination") @RequestParam(required = false, defaultValue = "0") Integer offset,
            @Parameter(description = "Limit for pagination") @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        List<Product> productsSearch = searchService.searchProducts(q, offset, limit);
        PagingDto paging = new PagingDto(offset, limit, productsSearch.size());
        productMetricsService.incrementSearch(q);
        return ResponseEntity.ok(new SearchDTO(paging, q, productsSearch));
    }

    @Operation(summary = "Get questions for a product", description = "Returns all questions for a given product.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Questions retrieved")
    })
    @GetMapping("/{productId}/questions")
    public ResponseEntity<List<Question>> getQuestionsFromProduct(@Parameter(description = "Product ID") @PathVariable String productId) {
        return ResponseEntity.ok(questionsService.getProductQuestions(productId));
    }

    @NeedsAuth
    @PostMapping("/{productId}/questions")
    @Operation(summary = "Add a question to a product", description = "Adds a new question to a product. Requires authentication.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Question added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<String> addQuestionToProduct(
            @Parameter(description = "Product ID") @PathVariable String productId,
            @Valid @RequestBody QuestionsDto questionsDto,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("userId");
        questionsService.addQuestionToProduct(productId, questionsDto.text, userId);
        productMetricsService.incrementQuestionByProduct(productId);
        return ResponseEntity.ok("Question added successfully");
    }

    @Operation(summary = "Get reviews for a product", description = "Returns all reviews for a given product.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reviews retrieved")
    })
    @GetMapping("/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewsFromProduct(@Parameter(description = "Product ID") @PathVariable String productId) {
        return ResponseEntity.ok(reviewService.getProductReviews(productId));
    }

    @NeedsAuth
    @PostMapping("/{productId}/reviews")
    @Operation(summary = "Add a review to a product", description = "Adds a new review to a product. Requires authentication.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<String> postReviewToProduct(
            @Parameter(description = "Product ID") @PathVariable String productId,
            @Valid @RequestBody ReviewDto reviewDto,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("userId");
        reviewService.addReviewToProduct(productId, reviewDto.rating, reviewDto.review, userId);
        productMetricsService.incrementReviewByProduct(productId);
        return ResponseEntity.ok("Review added successfully");
    }
}
