package unq.pdes._5.g1.segui_tus_compras.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unq.pdes._5.g1.segui_tus_compras.model.purchase.PurchaseItem;

import java.util.List;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem,Long> {
    @Query("SELECT p.product, SUM(p.amount) " +
            "FROM PurchaseItem p " +
            "GROUP BY p.product " +
            "ORDER BY SUM(p.amount) DESC")
    List<Object[]> findTopProductsRaw(Pageable pageable);
}
