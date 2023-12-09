package udpm.fpt.repository;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import udpm.fpt.model.SaleMilk;

import java.util.List;
import org.springframework.data.repository.query.Param;

public interface ISaleMilk extends JpaRepository<SaleMilk, Integer> {
    @Query("SELECT sm FROM SaleMilk sm WHERE sm.start_day <= CURRENT_DATE AND CURRENT_DATE <= sm.end_day")
    List<SaleMilk> findSalesInRange();
    @Query("Select sm FROM SaleMilk sm WHERE sm.end_day <= :ed")
    List<SaleMilk> findByEndDate(@Param("ed") Date ed);
}
