package unq.pdes._5.g1.segui_tus_compras.service;

import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.model.Product;
import unq.pdes._5.g1.segui_tus_compras.model.dto.api.ExternalProductDto;
import unq.pdes._5.g1.segui_tus_compras.repository.ProductsRepository;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final MeLiApiService meLiService;

    public ProductsService(ProductsRepository productsRepository, MeLiApiService externalApiService) {
        this.productsRepository = productsRepository;
        this.meLiService = externalApiService;
    }

    public Product getProductById(String id) {
        if(productsRepository.existsById(id)) {
            return productsRepository.findById(id).orElse(null);
        }
        ExternalProductDto apiProduct = meLiService.getProductById(id);
        if (apiProduct == null) {
            return null;
        }
        return new Product(apiProduct);
    }
}
