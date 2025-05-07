package unq.pdes._5.g1.segui_tus_compras.model;

import jakarta.persistence.*;
import lombok.Getter;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Purchase> purchases;

    @ManyToMany
    @JoinTable(
        name = "user_favorites",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> favorites;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.purchases = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }

    public void toggleFavorite(Product product) {
        if (this.favorites.contains(product)) {
            this.favorites.remove(product);
        } else {
            this.favorites.add(product);
        }
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }
}
