package udpm.fpt.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import udpm.fpt.model.DashboardProduct;
import udpm.fpt.model.Milk;

import udpm.fpt.model.ProductInfo;

/**
 * @author NONG HOANG VU
 */
public interface IProductInfo extends JpaRepository<ProductInfo, Integer> {
    @Query("SELECT p FROM ProductInfo p "
            + "JOIN FETCH p.milk m "
            + "JOIN FETCH p.flavor f "
            + "JOIN FETCH p.unit u "
            + "JOIN FETCH p.packagingSpecification ps "
            + "WHERE (m.product_name LIKE %:productName% OR :productName IS NULL) "
            + "AND (f.taste = :flavor OR :flavor IS NULL) "
            + "AND (ps.packaging_type = :packagingType OR :packagingType IS NULL) "
            + "AND (u.measurement_unit = :measurementUnit OR :measurementUnit IS NULL) "
            + "AND (p.volume = :volume OR :volume IS NULL) "
            + "AND (p.create_at >= :entryDate OR :entryDate IS NULL) "
            + "AND (m.amount >= COALESCE(:minQuantity, (SELECT MIN(m2.amount) FROM Milk m2)) AND m.amount <= COALESCE(:maxQuantity, (SELECT MAX(m2.amount) FROM Milk m2))) "
            + "AND (m.price >= COALESCE(:minPrice, (SELECT MIN(m2.price) FROM Milk m2)) AND m.price <= COALESCE(:maxPrice, (SELECT MAX(m2.price) FROM Milk m2))) "
            + "AND (:expiryStatus IS NULL OR (:expiryStatus = 'Expired' AND m.expiration_date < CURRENT_DATE) OR (:expiryStatus = 'Valid' AND m.expiration_date >= CURRENT_DATE))"
    )
    List<ProductInfo> findProductInfoByCriteria(
            @Param("productName") String productName,
            @Param("flavor") String flavor,
            @Param("packagingType") String packagingType,
            @Param("measurementUnit") String measurementUnit,
            @Param("volume") Float volume,
            @Param("entryDate") Date entryDate,
            @Param("minQuantity") Integer minQuantity,
            @Param("maxQuantity") Integer maxQuantity,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("expiryStatus") String expiryStatus
    );

    @Query("SELECT p FROM ProductInfo p "
            + "JOIN FETCH p.milk m "
            + "JOIN FETCH p.flavor f "
            + "JOIN FETCH p.unit u "
            + "JOIN FETCH p.packagingSpecification ps "
            + "WHERE (m.barcode = :productName OR m.id = :productName OR :productName IS NULL) "
            + "AND (f.taste = :flavor OR :flavor IS NULL) "
            + "AND (ps.packaging_type = :packagingType OR :packagingType IS NULL) "
            + "AND (u.measurement_unit = :measurementUnit OR :measurementUnit IS NULL) "
            + "AND (p.volume = :volume OR :volume IS NULL) "
            + "AND (p.create_at >= :entryDate OR :entryDate IS NULL) "
            + "AND (m.amount >= COALESCE(:minQuantity, (SELECT MIN(m2.amount) FROM Milk m2)) AND m.amount <= COALESCE(:maxQuantity, (SELECT MAX(m2.amount) FROM Milk m2))) "
            + "AND (m.price >= COALESCE(:minPrice, (SELECT MIN(m2.price) FROM Milk m2)) AND m.price <= COALESCE(:maxPrice, (SELECT MAX(m2.price) FROM Milk m2))) "
            + "AND (:expiryStatus IS NULL OR (:expiryStatus = 'Expired' AND m.expiration_date < CURRENT_DATE) OR (:expiryStatus = 'Valid' AND m.expiration_date >= CURRENT_DATE))"
    )
    List<ProductInfo> findProductInfoByCriteriaBarcode(
            @Param("productName") String productName,
            @Param("flavor") String flavor,
            @Param("packagingType") String packagingType,
            @Param("measurementUnit") String measurementUnit,
            @Param("volume") Float volume,
            @Param("entryDate") Date entryDate,
            @Param("minQuantity") Integer minQuantity,
            @Param("maxQuantity") Integer maxQuantity,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("expiryStatus") String expiryStatus
    );
    @Query("SELECT COALESCE(COUNT(m.amount), 0) FROM ProductInfo pi JOIN pi.milk m")
    Integer getInventoryQuantity();
    @Query("SELECT COALESCE(SUM(CASE WHEN m.expiration_date < CURRENT_DATE THEN 1 ELSE 0 END), 0) FROM ProductInfo pi JOIN pi.milk m")
    Integer getExpiredProducts();
}
