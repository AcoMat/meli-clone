package unq.pdes._5.g1.segui_tus_compras.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.model.Purchase;
import unq.pdes._5.g1.segui_tus_compras.model.dto.user.FavoriteDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.user.PurchaseDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.security.JwtTokenProvider;
import unq.pdes._5.g1.segui_tus_compras.service.user.UserService;
import unq.pdes._5.g1.segui_tus_compras.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService _userService;

    public UserController(UserService userService) {
        this._userService = userService;
    }

    @GetMapping("/favorites")
    public ResponseEntity<ApiResponse<List<Product>>> getFavorites(@RequestHeader("Authorization") String token) {
        Long userId = JwtTokenProvider.validateTokenAndGetUserId(token);
        return ResponseEntity.ok(new ApiResponse<>(true, "Favorites retrieved successfully", null, _userService.getFavorites(userId)));
    }

    @PutMapping("/favorites")
    public ResponseEntity<ApiResponse<List<Product>>> toggleFavorite(@RequestBody FavoriteDto dto, @RequestHeader("Authorization") String token) {
        Long userId = JwtTokenProvider.validateTokenAndGetUserId(token);
        return ResponseEntity.ok(new ApiResponse<>(true, "Favorite switched successfully", null, _userService.toggleFavorite(userId, dto.productId)));
    }

    @PostMapping("/purchases")
    public ResponseEntity<ApiResponse<?>> postNewPurchase(@RequestBody PurchaseDto dto, @RequestHeader("Authorization") String token) {
        Long userId = JwtTokenProvider.validateTokenAndGetUserId(token);
        List<String> productsIds = dto.productsIds;
        _userService.postNewPurchase(userId, productsIds);
        return ResponseEntity.ok(new ApiResponse<>(true, "Purchase created successfully", null, null));
    }

    @GetMapping("/purchases")
    public ResponseEntity<?> getPurchases(@RequestHeader("Authorization") String token) {
        Long userId = JwtTokenProvider.validateTokenAndGetUserId(token);
        List<Purchase> purchases = _userService.getPurchases(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Purchases retrieved successfully", null, purchases));
    }

}
