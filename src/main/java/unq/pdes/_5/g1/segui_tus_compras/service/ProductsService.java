package unq.pdes._5.g1.segui_tus_compras.service;

import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.model.dto.ProductDto;

@Service
public class ProductsService {

    public ProductDto getProductById(String id) {
        // Logic to retrieve product by ID
        return new ProductDto();
    }
}
