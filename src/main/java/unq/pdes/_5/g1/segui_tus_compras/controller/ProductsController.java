package unq.pdes._5.g1.segui_tus_compras.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.model.Product;
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
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable String id) {
        Product product = productsService.getProductById(id);
        if(product == null) {
            ApiResponse<Product> response = new ApiResponse<>(false, "Product not found", null, null);
            return ResponseEntity.status(404).body(response);
        }
        System.out.println("Product found in external API");
        System.out.println(product.getId());
        ApiResponse<Product> response = new ApiResponse<>(true, "Product retrieved successfully", product, null);
        return ResponseEntity.ok(response);
    }
}
