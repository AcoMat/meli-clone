package unq.pdes._5.g1.segui_tus_compras.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import unq.pdes._5.g1.segui_tus_compras.model.dto.api.ExternalProductDto;

import java.util.List;

@Entity
@Getter
public class Product {
    @Id
    String id;
    @NotBlank
    String name;
    Double price;
    String description;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ProductAttribute> attributes;
    List<String> pictures;
    Integer priceDiscountPercentage;
    Boolean isFreeShipping;

    public Product(ExternalProductDto apiProduct) {
        this.id = apiProduct.id;
        this.name = apiProduct.name;
        this.price = apiProduct.buyBoxWinner.originalPrice;
        this.description = apiProduct.description.content;
        this.attributes = apiProduct.attributes.stream()
                .map(attribute -> new ProductAttribute(attribute.id, attribute.name, attribute.value))
                .toList();
        this.pictures = apiProduct.pictures.stream()
                .map(picture -> picture.url)
                .toList();
        this.priceDiscountPercentage = apiProduct.buyBoxWinner.price != null
                ? (int) Math.round((1 - (apiProduct.buyBoxWinner.price / apiProduct.buyBoxWinner.originalPrice)) * 100)
                : null;
        this.isFreeShipping = apiProduct.buyBoxWinner.shipping.freeShipping;
    }

    public Product() {
    }
}
