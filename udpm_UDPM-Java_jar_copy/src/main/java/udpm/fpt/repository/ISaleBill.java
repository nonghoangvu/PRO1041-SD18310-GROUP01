/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package udpm.fpt.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import udpm.fpt.model.SaleBill1;

/**
 *
 * @author Thanh Dat
 */
public interface ISaleBill extends JpaRepository<SaleBill1, Integer>{
    @Query("Select sb from SaleBill1 sb where sb.endDay <= :ed")
    List<SaleBill1> findByEndDate(@Param("ed") Date ed);
    
}
