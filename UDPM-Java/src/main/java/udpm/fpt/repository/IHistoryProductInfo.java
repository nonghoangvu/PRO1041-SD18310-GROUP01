package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udpm.fpt.model.HistoryProductInfo;

/**
 *
 * @author NONG HOANG VU
 */
public interface IHistoryProductInfo extends JpaRepository<HistoryProductInfo, Long>{
    
}
