package unq.pdes._5.g1.segui_tus_compras.service;

import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.exception.ProductNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.Commentary;
import unq.pdes._5.g1.segui_tus_compras.model.User;
import unq.pdes._5.g1.segui_tus_compras.model.dto.api.ApiSearchDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.dto.api.ExternalProductDto;
import unq.pdes._5.g1.segui_tus_compras.repository.ProductsRepository;

import java.util.List;
import java.util.Objects;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    private final MeLiApiService meLiService;
    private final UserService userService;

    public ProductsService(ProductsRepository productsRepository, MeLiApiService externalApiService, UserService userService) {
        this.productsRepository = productsRepository;
        this.meLiService = externalApiService;
        this.userService = userService;
    }

    public Product getProductById(String id) {
        if(productsRepository.existsById(id)) {
            System.out.println("Product found in database");
            return productsRepository.findById(id).orElse(null);
        }
        ExternalProductDto apiProduct = meLiService.getProductById(id);
        if (apiProduct == null) {
            throw new ProductNotFoundException("Product not found");
        }
        return productsRepository.save(new Product(apiProduct));
    }

    public List<Product> searchProducts(String keywords, int offset, int limit) {
        ApiSearchDto apiProducts = meLiService.search(keywords, offset, limit);
        if (apiProducts.results.isEmpty()) {
            return null;
        }
        return apiProducts.results.stream()
                .map(result -> meLiService.getProductById(result.id))
                .filter(Objects::nonNull)
                .map(Product::new)
                .toList();
    }

    public void addCommentToProduct(String productId, String comment, Long userId) {
        User user = userService.getUserById(userId);
        Product product = getProductById(productId);
        Commentary newCommentary = new Commentary(user, product, comment);
        product.addComment(newCommentary);
        productsRepository.save(product);
    }
}
