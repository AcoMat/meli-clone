package unq.pdes._5.g1.segui_tus_compras.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.mapper.Mapper;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.UserDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.user.FavoriteDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.purchase.PurchaseDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.purchase.Purchase;
import unq.pdes._5.g1.segui_tus_compras.security.annotation.NeedsAuth;
import unq.pdes._5.g1.segui_tus_compras.service.purchase.PurchaseService;
import unq.pdes._5.g1.segui_tus_compras.service.user.FavoriteService;
import unq.pdes._5.g1.segui_tus_compras.service.user.UserService;

import java.util.List;

@RestController
@NeedsAuth
@RequestMapping("/users")
public class UserController {

    private final UserService _userService;
    private final PurchaseService _purchaseService;
    private final FavoriteService _favoriteService;
    private final Mapper _mapper;

    public UserController(UserService userService, Mapper mapper, PurchaseService purchaseService, FavoriteService favoriteService) {
        this._userService = userService;
        this._mapper = mapper;
        this._purchaseService = purchaseService;
        this._favoriteService = favoriteService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(_mapper.toDTO(_userService.getUserById(userId)));
    }

    @GetMapping("/me/favorites")
    public ResponseEntity<List<Product>> getFavorites(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(_userService.getUserFavorites(userId));
    }

    @PutMapping("/me/favorites")
    public ResponseEntity<String> toggleFavorite(HttpServletRequest request, @Valid @RequestBody FavoriteDto dto) {
        Long userId = (Long) request.getAttribute("userId");
        boolean added = _favoriteService.toggleFavorite(userId, dto.productId);
        return ResponseEntity.ok("Product " + dto.productId + (added ? " added to" : " removed from") + " favorites");
    }

    @PostMapping("/me/purchases")
    public ResponseEntity<String> postNewPurchase(HttpServletRequest request, @Valid @RequestBody PurchaseDto dto) {
        Long userId = (Long) request.getAttribute("userId");
        _purchaseService.generatePurchase(userId, dto.items);
        return ResponseEntity.ok("Purchase created successfully");
    }

    @GetMapping("/me/purchases")
    public ResponseEntity<List<Purchase>> getPurchases(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(_userService.getUserPurchases(userId));
    }

    @GetMapping("/me/purchases/{productId}")
    public ResponseEntity<Boolean> getPurchases(HttpServletRequest request, @PathVariable String productId) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(_purchaseService.userBoughtProduct(userId, productId));
    }

}
