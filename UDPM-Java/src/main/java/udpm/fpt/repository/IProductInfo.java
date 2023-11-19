package udpm.fpt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
            + "WHERE m.product_name LIKE %:search% OR f.taste LIKE %:search%")
    List<ProductInfo> findProductInfo(@Param("search") String search);

    @Query("SELECT p FROM ProductInfo p "
            + "JOIN FETCH p.milk m "
            + "JOIN FETCH p.flavor f "
            + "JOIN FETCH p.unit u "
            + "JOIN FETCH p.packagingSpecification ps "
            + "WHERE m.product_name LIKE %:search% "
            + "AND m.price BETWEEN :minPrice AND :maxPrice")
    List<ProductInfo> findProductInfoInPriceRange(@Param("search") String search,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice);

}
