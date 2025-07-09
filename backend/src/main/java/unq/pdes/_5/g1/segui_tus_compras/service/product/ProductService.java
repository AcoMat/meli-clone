package unq.pdes._5.g1.segui_tus_compras.service.product;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.pdes._5.g1.segui_tus_compras.exception.product.ProductNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api.ApiSearchDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api.ExternalProductDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.product.ProductFavoriteCountDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
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

    @Transactional(readOnly = true)
    public Product getProductById(String id) {
        // First try to find existing product
        Product existingProduct = productsRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            return existingProduct;
        }

        // If not found, create it in a separate transaction
        return createProductFromApi(id);
    }

    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)
    public Product createProductFromApi(String id) {
        // Double-check if product was created by another thread
        Product existingProduct = productsRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            return existingProduct;
        }

        ExternalProductDto apiProduct = meLiService.getProductById(id);
        if (apiProduct.buyBoxWinner == null || apiProduct.buyBoxWinner.originalPrice == null) {
            apiProduct.buyBoxWinner = new ExternalProductDto.BuyBoxWinnerDto();
            // Generate random price between 50.000 and 1.000.000
            double randomPrice = 50000 + Math.random() * (1000000 - 50000);
            apiProduct.buyBoxWinner.price = Math.round(randomPrice * 100.0) / 100.0;
            // Gen Random discount percentage between 0 and 50
            int randomDiscountPercentage = (int) (Math.random() * 51);
            double originalPrice = apiProduct.buyBoxWinner.price * (1 + randomDiscountPercentage / 100.0);
            apiProduct.buyBoxWinner.originalPrice = Math.round(originalPrice * 100.0) / 100.0;
            // Generate random free shipping boolean
            apiProduct.buyBoxWinner.shipping = new ExternalProductDto.BuyBoxWinnerDto.ShippingDto();
            apiProduct.buyBoxWinner.shipping.freeShipping = Math.random() < 0.5;
        }

        try {
            return productsRepository.save(new Product(apiProduct));
        } catch (Exception e) {
            // If any constraint violation occurs, try to fetch the product that might have been created by another thread
            if (e.getMessage() != null && (
                e.getMessage().contains("Duplicate entry") ||
                e.getMessage().contains("Unique index or primary key violation") ||
                e.getMessage().contains("constraint") ||
                e.getMessage().contains("Timeout trying to lock table") ||
                e.getMessage().contains("rollback"))) {

                // Wait briefly for other transaction to complete
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}

                Product result = productsRepository.findById(id).orElse(null);
                if (result != null) {
                    return result;
                }
            }
            throw new ProductNotFoundException("Failed to create or retrieve product: " + id + " - " + e.getMessage());
        }
    }

    @Transactional
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
        return apiProducts.results.stream()
            .collect(java.util.stream.Collectors.toMap(
                result -> result.id,
                result -> result,
                (existing, replacement) -> existing, // Keep first occurrence if duplicate IDs
                java.util.LinkedHashMap::new // Preserve insertion order
            ))
            .values()
            .stream()
            .map(result -> {
                try {
                    return getProductById(result.id);
                } catch (Exception e) {
                    // Log the error but continue processing other products
                    System.err.println("Error processing product " + result.id + ": " + e.getMessage());
                    return null;
                }
            })
            .filter(product -> product != null) // Remove null products that failed to process
            .toList();
    }

    public List<ProductFavoriteCountDto> getTopFavoriteProducts() {
        return productsRepository.findTopFavoriteProducts(PageRequest.of(0, 5));
    }

}
