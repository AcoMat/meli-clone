package unq.pdes._5.g1.segui_tus_compras.repository.external;

import org.springframework.data.jpa.repository.JpaRepository;
import unq.pdes._5.g1.segui_tus_compras.model.external.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {}