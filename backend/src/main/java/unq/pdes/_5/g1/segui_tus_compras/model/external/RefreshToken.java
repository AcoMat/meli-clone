package unq.pdes._5.g1.segui_tus_compras.model.external;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class RefreshToken {

    @Id
    private Long id = 1L; // Solo 1 registro

    @Getter
    @Column(nullable = false)
    private String token;

    public RefreshToken(String token) {
        this.token = token;
    }
}

