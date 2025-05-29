package unq.pdes._5.g1.segui_tus_compras.service.purchase;

import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.model.dto.purchase.PurchaseItemDto;
import unq.pdes._5.g1.segui_tus_compras.model.purchase.Purchase;
import unq.pdes._5.g1.segui_tus_compras.model.purchase.PurchaseItem;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;
import unq.pdes._5.g1.segui_tus_compras.service.product.ProductService;
import unq.pdes._5.g1.segui_tus_compras.service.user.UserService;

import java.util.List;

@Service
public class PurchaseService {

    private final ProductService _productService;
    private final UserService _userService;

    public PurchaseService(ProductService productService, UserService userService) {
        this._productService = productService;
        this._userService = userService;
    }

    public void generatePurchase(Long userId, List<PurchaseItemDto> dtoItems) {
        User user = _userService.getUserById(userId);
        List<PurchaseItem> items = dtoItems
                .stream().map(p -> new PurchaseItem(
                        _productService.getProductById(p.productId),
                        p.amount
                        )
                ).toList();
        user.addPurchase(new Purchase(user, items));
        _userService.updateUser(user);
    }

    public boolean userBoughtProduct(Long userId, String productId) {
        User user = _userService.getUserById(userId);
        return user.getPurchases().stream()
                .anyMatch(purchase -> purchase.getItems().stream()
                        .anyMatch(item -> item.getProduct().getId().equals(productId)));
    }
}
