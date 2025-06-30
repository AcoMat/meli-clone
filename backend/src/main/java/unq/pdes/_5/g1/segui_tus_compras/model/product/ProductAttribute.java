package unq.pdes._5.g1.segui_tus_compras.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttribute {
    @Id
    private String id;
    private String name;
    @Column(name = "attribute_value")
    private String value;
}
