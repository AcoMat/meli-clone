package unq.pdes._5.g1.segui_tus_compras.model.purchase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Getter
    private String productId;
    @Getter
    private Integer amount;
    private Double subTotal;

    public PurchaseItem(String productId, Integer amount, Double subTotal) {
        this.productId = productId;
        this.amount = amount;
        this.subTotal = subTotal;
    }

    public Double getSubTotal() {
        if (subTotal == null) {;
            return 0.0;
        }
        return subTotal;
    }
}
