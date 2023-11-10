package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udpm.fpt.model.PackagingSpecification;

/**
 *
 * @author NONG HOANG VU
 */
public interface IPackagingSpecification extends JpaRepository<PackagingSpecification, Long>{
    
}
