package unq.pdes._5.g1.segui_tus_compras.service.product;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.exception.product.ProductNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api.ApiSearchDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.product.ProductFavoriteCountDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.repository.ProductsRepository;
import unq.pdes._5.g1.segui_tus_compras.service.external.MeLiApiService;

import java.util.List;

@Service
public class ProductService {

    private final ProductsRepository productsRepository;
    private final MeLiApiService meLiService;
    private final ProductInternalService productInternalService;

    public ProductService(ProductsRepository productsRepository, MeLiApiService externalApiService, ProductInternalService productInternalService) {
        this.productsRepository = productsRepository;
        this.meLiService = externalApiService;
        this.productInternalService = productInternalService;
    }

    public Product getProductById(String id) {
        return productInternalService.getProductById(id);
    }

    public void updateProduct(Product product) {
        if (!productsRepository.existsById(product.getId())) {
            throw new ProductNotFoundException(product.getId());
        }
        productsRepository.save(product);
    }

    public List<Product> searchProducts(String keywords, int offset, int limit) {
        ApiSearchDto apiProducts = meLiService.search(keywords, offset, limit);
        if (apiProducts.results.isEmpty()) {
            return List.of();
        }
        return apiProducts.results.stream().map(
            result -> productInternalService.getProductById(result.id)
        ).toList();
    }

    public List<ProductFavoriteCountDto> getTopFavoriteProducts() {
        return productsRepository.findTopFavoriteProducts(PageRequest.of(0, 5));
    }

}
