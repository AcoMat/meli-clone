package unq.pdes._5.g1.segui_tus_compras.model.dto.out.product;

import unq.pdes._5.g1.segui_tus_compras.model.product.Product;

public class TopPurchasedProductDto {
    public String productId;
    public String name;
    public Long total;
    public String firstPicture;

    public TopPurchasedProductDto(Product product, Long totalAmount) {
        this.productId = product.getId();
        this.name = product.getName();
        this.total = totalAmount;
        this.firstPicture = (product.getPictures() != null && !product.getPictures().isEmpty())
                ? product.getPictures().getFirst()
                : null;
    }
}

