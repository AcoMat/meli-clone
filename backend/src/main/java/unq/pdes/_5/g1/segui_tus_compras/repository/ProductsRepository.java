package unq.pdes._5.g1.segui_tus_compras.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unq.pdes._5.g1.segui_tus_compras.model.dto.out.product.ProductFavoriteCountDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;

import java.util.List;


public interface ProductsRepository extends JpaRepository<Product, String> {

    @Query("""
    SELECT new unq.pdes._5.g1.segui_tus_compras.model.dto.out.product.ProductFavoriteCountDto(p, COUNT(u))
    FROM unq.pdes._5.g1.segui_tus_compras.model.user.User u
    JOIN u.favorites p
    GROUP BY p
    ORDER BY COUNT(u) DESC
    """)
    List<ProductFavoriteCountDto> findTopFavoriteProducts(Pageable pageable);
}
