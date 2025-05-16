package unq.pdes._5.g1.segui_tus_compras.service.product;

import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.exception.ProductNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.dto.meli_api.ApiSearchDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.dto.meli_api.ExternalProductDto;
import unq.pdes._5.g1.segui_tus_compras.repository.ProductsRepository;
import unq.pdes._5.g1.segui_tus_compras.service.external.MeLiApiService;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private final ProductsRepository productsRepository;
    private final MeLiApiService meLiService;

    public ProductService(ProductsRepository productsRepository, MeLiApiService externalApiService) {
        this.productsRepository = productsRepository;
        this.meLiService = externalApiService;
    }

    public Product getProductById(String id) {
        if(productsRepository.existsById(id)) {
            System.out.println("Product found in database");
            return productsRepository.findById(id).orElse(null);
        }
        ExternalProductDto apiProduct = meLiService.getProductById(id);
        if (apiProduct == null) {
            throw new ProductNotFoundException();
        }
        return productsRepository.save(new Product(apiProduct));
    }

    public Product updateProduct(Product product) {
        if (!productsRepository.existsById(product.getId())) {
            throw new ProductNotFoundException();
        }
        return productsRepository.save(product);
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
}
