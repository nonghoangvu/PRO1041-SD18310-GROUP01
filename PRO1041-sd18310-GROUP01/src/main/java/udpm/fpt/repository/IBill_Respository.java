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

/**
 *
 * @author PHONG PC
 */
public interface IBill_Respository extends JpaRepository<Bill, Integer>{
    @Query("From Bill WHERE payment_status like 'Pending' AND shopping_method like 'delivery'")
    List<Bill> findBillByshoppingmethod();
    
    @Query("From Bill WHERE id like CONCAT('%', :idBill, '%')")
    Bill findBill(@Param("idBill") String idBill);
}
