package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import udpm.fpt.model.SaleMilk;

import java.util.List;

public interface ISaleMilk extends JpaRepository<SaleMilk, Integer> {
    @Query("SELECT sm FROM SaleMilk sm WHERE sm.start_day <= CURRENT_DATE AND CURRENT_DATE <= sm.end_day")
    List<SaleMilk> findSalesInRange();

}
