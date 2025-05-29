package unq.pdes._5.g1.segui_tus_compras.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String comment;
    private LocalDateTime createdAt;
    @ManyToOne
    @JsonIgnore
    private User user;
    @ManyToOne
    @JsonIgnore
    private Product product;


    public Commentary(User user, Product product, String comment) {
        this.user = user;
        this.product = product;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }

    @JsonProperty("by")
    public String getUserName() {
        return this.user.getFirstName();
    }
}
