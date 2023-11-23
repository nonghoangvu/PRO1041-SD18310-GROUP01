package udpm.fpt.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import udpm.fpt.model.Milk;

import udpm.fpt.model.ProductInfo;

/**
 *
 * @author NONG HOANG VU
 */
public interface IProductInfo extends JpaRepository<ProductInfo, Integer> {

    @Query("SELECT p FROM ProductInfo p "
            + "JOIN FETCH p.milk m "
            + "JOIN FETCH p.flavor f "
            + "JOIN FETCH p.unit u "
            + "JOIN FETCH p.packagingSpecification ps "
            + "WHERE m.product_name LIKE %:search% OR f.taste LIKE %:search% OR m.id LIKE %:search%")
    List<ProductInfo> findProductInfo(@Param("search") String search);

    @Query("SELECT p FROM ProductInfo p "
            + "JOIN FETCH p.milk m "
            + "JOIN FETCH p.flavor f "
            + "JOIN FETCH p.unit u "
            + "JOIN FETCH p.packagingSpecification ps "
            + "WHERE ((:minAmount IS NULL AND :maxAmount IS NULL) OR (m.amount >= :minAmount AND m.amount <= :maxAmount)) "
            + "AND (:minPrice IS NULL OR m.price >= :minPrice) "
            + "AND (:maxPrice IS NULL OR m.price <= :maxPrice) "
            + "AND (:startDate IS NULL OR m.production_date >= :startDate) "
            + "AND (:endDate IS NULL OR m.production_date <= :endDate) "
            + "AND (:taste IS NULL OR f.taste = :taste) "
            + "AND (:packaging_type IS NULL OR ps.packaging_type = :packaging_type)")
    List<ProductInfo> findProductInfoFilter(
            @Param("minAmount") Integer minAmount,
            @Param("maxAmount") Integer maxAmount,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("taste") String taste,
            @Param("packaging_type") String packaging_type,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice);

}
