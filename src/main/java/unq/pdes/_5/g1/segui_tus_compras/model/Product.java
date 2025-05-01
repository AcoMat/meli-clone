package unq.pdes._5.g1.segui_tus_compras.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import unq.pdes._5.g1.segui_tus_compras.model.dto.api.ExternalProductDto;

@Entity
public class Product {
    @Id
    String id;
    @NotBlank
    String name;
    String description;

    public Product(ExternalProductDto apiProduct) {
        this.id = apiProduct.id;
        this.name = apiProduct.name;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
