package udpm.fpt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import udpm.fpt.model.Milk;

/**
 *
 * @author NONG HOANG VU
 */
public interface IMilk extends JpaRepository<Milk, Long> {
    @Query("SELECT m FROM Milk m JOIN FETCH m.flavor")
    List<Milk> findAllWithFlavor();
}
