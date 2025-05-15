package unq.pdes._5.g1.segui_tus_compras.model.purchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import unq.pdes._5.g1.segui_tus_compras.model.User;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
    private ZonedDateTime date;
    private Double total;
    private List<PurchaseItem> items;

    public Purchase() {
    }

    public Purchase(User user, List<PurchaseItem> items) {
        this.user = user;
        this.date = ZonedDateTime.now();
        this.items = items;
        this.total = items.stream()
                .map(PurchaseItem::getSubTotal)
                .reduce(0.0, Double::sum);
    }
}
