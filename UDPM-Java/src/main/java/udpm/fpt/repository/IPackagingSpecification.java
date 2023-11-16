package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import udpm.fpt.model.PackagingSpecification;

/**
 *
 * @author NONG HOANG VU
 */
@Repository
public interface IPackagingSpecification extends JpaRepository<PackagingSpecification, Integer> {

//    @Transactional
//    Integer deleteByPackaging_type(String packaging_type);
//
//    default boolean deletePackagingSpecification(String packagingType) {
//        int deletedCount = deleteByPackaging_type(packagingType);
//        return deletedCount > 0;
//    }
}
