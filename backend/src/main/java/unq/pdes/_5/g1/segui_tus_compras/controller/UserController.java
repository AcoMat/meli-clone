package unq.pdes._5.g1.segui_tus_compras.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.metrics.product.ProductMetricsService;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.user.FavoriteDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.purchase.PurchaseDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.user.BasicUserDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.purchase.Purchase;
import unq.pdes._5.g1.segui_tus_compras.security.annotation.NeedsAuth;
import unq.pdes._5.g1.segui_tus_compras.service.purchase.PurchaseService;
import unq.pdes._5.g1.segui_tus_compras.service.user.FavoriteService;
import unq.pdes._5.g1.segui_tus_compras.service.user.UserService;
import unq.pdes._5.g1.segui_tus_compras.metrics.user.UserMetricsService;

import java.util.List;

@RestController
@NeedsAuth
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;
    private final PurchaseService purchaseService;
    private final FavoriteService favoriteService;
    private final UserMetricsService userMetricsService;
    private final ProductMetricsService productMetricsService;

    public UserController(UserService userService, PurchaseService purchaseService, FavoriteService favoriteService, UserMetricsService userMetricsService, ProductMetricsService productMetricsService) {
        this.userService = userService;
        this.purchaseService = purchaseService;
        this.favoriteService = favoriteService;
        this.userMetricsService = userMetricsService;
        this.productMetricsService = productMetricsService;
    }

    @GetMapping("/profile")
    @Operation(summary = "Get user profile information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User profile information retrieved"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<BasicUserDto> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(new BasicUserDto(userService.getUserById(userId)));
    }

    @GetMapping("/favorites")
    @Operation(summary = "Get user's favorite products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of favorite products retrieved"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<Product>> getFavorites(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userService.getUserFavorites(userId));
    }

    @PutMapping("/favorites")
    @Operation(summary = "Add or remove a product from user's favorites")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product added or removed from favorites"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<String> toggleFavorite(HttpServletRequest request, @Valid @RequestBody FavoriteDto dto) {
        Long userId = (Long) request.getAttribute("userId");
        boolean added = favoriteService.toggleFavorite(userId, dto.productId);

        if (added) {
            userMetricsService.incrementUserFavorite(userId);
            productMetricsService.incrementProductFavorite(dto.productId);
        } else {
            userMetricsService.decrementUserFavorite(userId);
            productMetricsService.decrementProductFavorite(dto.productId);
        }

        return ResponseEntity.ok("Product " + dto.productId + (added ? " added to" : " removed from") + " favorites");
    }

    @PostMapping("/purchases")
    @Operation(summary = "Create a new purchase for the user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid purchase data")
    })
    public ResponseEntity<String> postNewPurchase(HttpServletRequest request, @Valid @RequestBody PurchaseDto dto) {
        Long userId = (Long) request.getAttribute("userId");
        purchaseService.generatePurchase(userId, dto.items);

        userMetricsService.incrementUserPurchase(userId);
        dto.items.forEach(item -> productMetricsService.incrementProductPurchase(item.getProductId(), item.getAmount()));

        return ResponseEntity.ok("Purchase created successfully");
    }

    @GetMapping("/purchases")
    @Operation(summary = "Get all purchases for the user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of purchases returned"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<Purchase>> getPurchases(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userService.getUserPurchases(userId));
    }

    @GetMapping("/purchases/{productId}")
    @Operation(summary = "Check if user bought a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Boolean result returned"),
        @ApiResponse(responseCode = "404", description = "User or product not found")
    })
    public ResponseEntity<Boolean> getPurchases(HttpServletRequest request, @PathVariable String productId) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(purchaseService.userBoughtProduct(userId, productId));
    }

}
