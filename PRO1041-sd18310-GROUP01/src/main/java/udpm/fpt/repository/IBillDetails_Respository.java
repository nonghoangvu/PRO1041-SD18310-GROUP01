/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package udpm.fpt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import udpm.fpt.model.Bill;
import udpm.fpt.model.BillDetails;

/**
 *
 * @author PHONG PC
 */
public interface IBillDetails_Respository extends JpaRepository<BillDetails, Integer>{
     @Query("From BillDetails Where bill_id = :idBill")
     List<BillDetails> findAllByBill_id(@Param("idBill") Bill id);
}
