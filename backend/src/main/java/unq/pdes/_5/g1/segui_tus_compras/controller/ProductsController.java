package unq.pdes._5.g1.segui_tus_compras.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.model.Commentary;
import unq.pdes._5.g1.segui_tus_compras.model.dto.CommentDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.PagingDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.security.JwtTokenProvider;
import unq.pdes._5.g1.segui_tus_compras.service.ProductsService;
import unq.pdes._5.g1.segui_tus_compras.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable String id) {
        Product product = productsService.getProductById(id);
        ApiResponse<Product> response = new ApiResponse<>(true, "Product retrieved successfully", product, null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Product>>> searchProductByName(
            @RequestParam String q,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        List<Product> productsSearch = productsService.searchProducts(q, offset, limit);
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

    @GetMapping("/product/{productId}/comments")
    public ResponseEntity<ApiResponse<List<Commentary>>> getCommentsFromProduct(@PathVariable String productId) {
        ApiResponse<List<Commentary>> response =
                new ApiResponse<>(true, "Commentaries retrieved successfully", null, productsService.getCommentariesFromProduct(productId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/product/{productId}/comments")
    public ResponseEntity<ApiResponse<Product>> addCommentToProduct(@PathVariable String productId, @RequestBody CommentDto commentDto, @RequestHeader("Authorization") String token) {
        Long userId = JwtTokenProvider.validateTokenAndGetUserId(token);
        ApiResponse<Product> response =
                new ApiResponse<>(true,
                        "Comment added successfully",
                        null,
                        productsService.addCommentToProduct(productId, commentDto.comment, userId)
                );
        return ResponseEntity.ok(response);
    }
}
