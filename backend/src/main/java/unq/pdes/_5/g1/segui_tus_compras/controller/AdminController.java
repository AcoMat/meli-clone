package unq.pdes._5.g1.segui_tus_compras.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.product.ProductFavoriteCountDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.product.TopPurchasedProductDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.user.BasicUserDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.user.UserDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.user.UserPurchasesDto;
import unq.pdes._5.g1.segui_tus_compras.security.annotation.AdminOnly;
import unq.pdes._5.g1.segui_tus_compras.service.product.ProductService;
import unq.pdes._5.g1.segui_tus_compras.service.purchase.PurchaseService;
import unq.pdes._5.g1.segui_tus_compras.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AdminOnly
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final PurchaseService purchaseService;

    public AdminController(UserService userService, ProductService productService, PurchaseService purchaseService) {
        this.userService = userService;
        this.productService = productService;
        this.purchaseService = purchaseService;
    }

    @GetMapping()
    public boolean isAdmin() {
        return true;
    }

    @GetMapping("/users")
    public List<BasicUserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserData(userId);
    }

    @GetMapping("/top/purchased")
    List<TopPurchasedProductDto> getTopPurchasedProducts()  {
        return purchaseService.getTopPurchasedProducts();
    }

    @GetMapping("/top/favorites")
    List<ProductFavoriteCountDto> getTopFavoriteProducts() {
        return productService.getTopFavoriteProducts();
    }

    @GetMapping("/top/buyers")
    List<UserPurchasesDto> getTopBuyers() {
        return purchaseService.getTopBuyers();
    }

}
