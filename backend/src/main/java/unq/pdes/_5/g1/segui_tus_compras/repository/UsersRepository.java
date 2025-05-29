package unq.pdes._5.g1.segui_tus_compras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;

public interface UsersRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);

}
