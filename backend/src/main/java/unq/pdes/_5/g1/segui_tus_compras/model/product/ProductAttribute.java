package unq.pdes._5.g1.segui_tus_compras.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attribute_id")
    private String attributeId;

    private String name;

    @Column(name = "attribute_value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "product_id") // FK
    @JsonIgnore
    private Product product;

    public ProductAttribute(String attributeId, String name, String value) {
        this.attributeId = attributeId;
        this.name = name;
        this.value = value;
    }
}

