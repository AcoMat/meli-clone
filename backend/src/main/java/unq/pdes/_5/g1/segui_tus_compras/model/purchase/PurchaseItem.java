package unq.pdes._5.g1.segui_tus_compras.model.purchase;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;

@Entity
@Getter
@NoArgsConstructor
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    private Integer amount;
    private Double subTotal;

    public PurchaseItem(Product product, Integer amount, Double subTotal) {
        this.product = product;
        this.amount = amount;
        this.subTotal = subTotal;
    }

    public Double getSubTotal() {
        if (subTotal == null) {
            return 0.0;
        }
        return subTotal;
    }
}
