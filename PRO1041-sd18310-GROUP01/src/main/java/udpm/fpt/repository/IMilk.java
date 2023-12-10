package udpm.fpt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import udpm.fpt.model.Milk;

/**
 *
 * @author NONG HOANG VU
 */
public interface IMilk extends JpaRepository<Milk, Long> {

    @Modifying
    @Query("UPDATE Milk m SET m.isDelete = :newIsDelete WHERE m.id = :milkId")
    int updateStatus(@Param("milkId") Long milkId, @Param("newIsDelete") String newIsDelete);
    @Transactional
    @Modifying
    @Query("UPDATE Milk SET amount = amount - :quanity WHERE id = :milkId")
    int updateQuantity(@Param("quanity") int amount, @Param("milkId") Long id);
    public Milk findAllByBarcode(Long barcode);
    @Query("From Milk WHERE product_name LIKE CONCAT('%', :name, '%')")
    List<Milk> findMilkByName(@Param("name") String name);
}
