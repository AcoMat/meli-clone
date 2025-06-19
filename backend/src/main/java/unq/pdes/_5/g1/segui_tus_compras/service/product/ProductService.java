package unq.pdes._5.g1.segui_tus_compras.service.product;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.exception.product.ProductNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.dto.meli_api.ApiSearchDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Commentary;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.dto.meli_api.ExternalProductDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Review;
import unq.pdes._5.g1.segui_tus_compras.repository.ProductsRepository;
import unq.pdes._5.g1.segui_tus_compras.service.external.MeLiApiService;

import java.util.List;

@Service
public class ProductService {

    private final ProductsRepository productsRepository;
    private final MeLiApiService meLiService;

    public ProductService(ProductsRepository productsRepository, MeLiApiService externalApiService) {
        this.productsRepository = productsRepository;
        this.meLiService = externalApiService;
    }

    public Product getProductById(String id) {
        return productsRepository.findById(id).orElseGet(() -> {
            ExternalProductDto apiProduct = meLiService.getProductById(id);
            try {
                return productsRepository.save(new Product(apiProduct));
            } catch (DataIntegrityViolationException e) {
                // Another thread inserted it, so fetch it again
                return productsRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
            }
        });
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
            result -> {
                try {
                    return getProductById(result.id);
                } catch (ProductNotFoundException e) {
                    return null;
                }
            }
        ).toList();
    }

}
