package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udpm.fpt.model.ProductInfo;

/**
 *
 * @author NONG HOANG VU
 */
public interface IProductInfo extends JpaRepository<ProductInfo, Long>{
    
}
