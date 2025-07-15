package unq.pdes._5.g1.segui_tus_compras.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final PurchaseService purchaseService;

    public AdminController(UserService userService, ProductService productService, PurchaseService purchaseService) {
        this.userService = userService;
        this.productService = productService;
        this.purchaseService = purchaseService;
    }

    @Operation(summary = "Check if user is admin", description = "Returns true if the user is an admin.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User is admin")
    })
    @GetMapping()
    public boolean isAdmin() {
        return true;
    }

    @Operation(summary = "Get all users", description = "Returns a list of all users.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of users returned")
    })
    @GetMapping("/users")
    public List<BasicUserDto> getUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by ID", description = "Returns user data for the given user ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User data returned"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/users/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserData(userId);
    }

    @Operation(summary = "Get top purchased products", description = "Returns a list of top purchased products.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of top purchased products returned")
    })
    @GetMapping("/top/purchased")
    List<TopPurchasedProductDto> getTopPurchasedProducts()  {
        return purchaseService.getTopPurchasedProducts();
    }

    @Operation(summary = "Get top favorite products", description = "Returns a list of top favorite products.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of top favorite products returned")
    })
    @GetMapping("/top/favorites")
    List<ProductFavoriteCountDto> getTopFavoriteProducts() {
        return productService.getTopFavoriteProducts();
    }

    @Operation(summary = "Get top buyers", description = "Returns a list of top buyers.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of top buyers returned")
    })
    @GetMapping("/top/buyers")
    List<UserPurchasesDto> getTopBuyers() {
        return purchaseService.getTopBuyers();
    }

}
