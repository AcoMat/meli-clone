package unq.pdes._5.g1.segui_tus_compras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unq.pdes._5.g1.segui_tus_compras.model.product.Review;

public interface ReviewsRepository extends JpaRepository<Review,Long> {
}
