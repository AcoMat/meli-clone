package unq.pdes._5.g1.segui_tus_compras.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
<<<<<<< Updated upstream:backend/src/main/java/unq/pdes/_5/g1/segui_tus_compras/model/Purchase.java
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
=======
import unq.pdes._5.g1.segui_tus_compras.model.user.User;
>>>>>>> Stashed changes:backend/src/main/java/unq/pdes/_5/g1/segui_tus_compras/model/purchase/Purchase.java

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
<<<<<<< Updated upstream:backend/src/main/java/unq/pdes/_5/g1/segui_tus_compras/model/Purchase.java
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "purchase_products",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
=======
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchaseItem> items;
>>>>>>> Stashed changes:backend/src/main/java/unq/pdes/_5/g1/segui_tus_compras/model/purchase/Purchase.java

    public Purchase() {
    }

    public Purchase(User user, List<Product> products) {
        this.user = user;
        this.products = products;
        this.date = ZonedDateTime.now();
        this.total = products.stream()
                .map(Product::getPriceWithDiscountApplied)
                .reduce(0.0, Double::sum);
    }
}
