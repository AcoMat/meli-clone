package unq.pdes._5.g1.segui_tus_compras.exception.product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String id) {
        super("Product with ID " + id + " not found");
    }
}
