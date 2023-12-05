package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import udpm.fpt.model.Milk;

/**
 *
 * @author NONG HOANG VU
 */
public interface IMilk extends JpaRepository<Milk, Long> {

    @Modifying
    @Query("UPDATE Milk m SET m.isDelete = :newIsDelete WHERE m.id = :milkId")
    int updateStatus(@Param("milkId") Long milkId, @Param("newIsDelete") String newIsDelete);

    public Milk findAllByBarcode(Long barcode);
}
