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
import unq.pdes._5.g1.segui_tus_compras.security.annotation.NeedsAuth;
import unq.pdes._5.g1.segui_tus_compras.service.purchase.PurchaseService;
import unq.pdes._5.g1.segui_tus_compras.service.user.UserService;

import java.util.List;

@RestController
@NeedsAuth
@RequestMapping("/users")
public class UserController {

    private final UserService _userService;
    private final PurchaseService _purchaseService;
    private final Mapper _mapper;

    public UserController(UserService userService, Mapper mapper, PurchaseService purchaseService) {
        this._userService = userService;
        this._mapper = mapper;
        this._purchaseService = purchaseService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(_mapper.toDTO(_userService.getUserById(userId)));
    }

    @GetMapping("/me/favorites")
    public ResponseEntity<List<Product>> getFavorites(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(_userService.getFavorites(userId));
    }

    @PutMapping("/me/favorites")
    public ResponseEntity<List<Product>> toggleFavorite(HttpServletRequest request, @Valid @RequestBody FavoriteDto dto) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(_userService.toggleFavorite(userId, dto.productId));
    }

    @PostMapping("/me/purchases")
    public ResponseEntity<String> postNewPurchase(HttpServletRequest request, @Valid @RequestBody PurchaseDto dto) {
        Long userId = (Long) request.getAttribute("userId");
        _purchaseService.generatePurchase(userId, dto.items);
        return ResponseEntity.ok("Purchase created successfully");
    }

    @GetMapping("/me/purchases")
    public ResponseEntity<?> getPurchases(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(_userService.getPurchases(userId));
    }

}
