package unq.pdes._5.g1.segui_tus_compras.model;

import jakarta.persistence.*;
import lombok.Getter;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private ZonedDateTime date;
    private Double total;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "purchase_products",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

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
