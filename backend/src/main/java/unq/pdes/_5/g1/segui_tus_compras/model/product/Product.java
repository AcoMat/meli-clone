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
        this.description = apiProduct.description != null ? apiProduct.description.content : null;
        this.attributes = apiProduct.attributes != null ? apiProduct.attributes.stream()
                .map(attribute -> new ProductAttribute(attribute.id, attribute.name, attribute.value))
                .toList() : null;
        this.pictures = apiProduct.pictures != null ? apiProduct.pictures.stream()
                .map(picture -> picture.url)
                .toList() : null;

        if(apiProduct.buyBoxWinner != null) {
            // Handle null originalPrice
            this.price = apiProduct.buyBoxWinner.originalPrice;

            // Calculate discount only if both price and originalPrice are not null
            if(apiProduct.buyBoxWinner.price != null && apiProduct.buyBoxWinner.originalPrice != null
                    && apiProduct.buyBoxWinner.originalPrice > 0) {
                this.priceDiscountPercentage = (int) Math.round(
                        (1 - (apiProduct.buyBoxWinner.price / apiProduct.buyBoxWinner.originalPrice)) * 100
                );
            } else {
                this.priceDiscountPercentage = null;
            }

            // Handle null shipping
            this.isFreeShipping = apiProduct.buyBoxWinner.shipping != null ?
                    apiProduct.buyBoxWinner.shipping.freeShipping : null;
        }
    }

    public Product() {
    }
}
