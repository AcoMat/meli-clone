package unq.pdes._5.g1.segui_tus_compras.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.model.dto.ProductDto;
import unq.pdes._5.g1.segui_tus_compras.service.ProductsService;
import unq.pdes._5.g1.segui_tus_compras.util.ApiResponse;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable String id) {
        ProductDto product = productsService.getProductById(id);
        ApiResponse<ProductDto> response = new ApiResponse<>(true, "Product retrieved successfully", product, null);
        return ResponseEntity.ok(response);
    }
}
