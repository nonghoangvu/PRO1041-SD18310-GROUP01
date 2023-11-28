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
import udpm.fpt.model.DeliveryNote;

/**
 *
 * @author PHONG PC
 */
public interface IDelivery_noteRespository extends JpaRepository<DeliveryNote, Long> {

    @Query("FROM DeliveryNote WHERE creationdate BETWEEN :minSalary AND :maxSalary")
    List<DeliveryNote> findBySalaryBetween(@Param("minSalary") Date minSalary, @Param("maxSalary") Date maxSalary);

    @Query("FROM DeliveryNote  Where customer_name LIKE CONCAT('%', :name, '%')")
    List<DeliveryNote> findByName(@Param("name") String name);
    
    @Query("FROM DeliveryNote Where id like CONCAT('%', :id, '%')")
    List<DeliveryNote> findByID(@Param("id") String id);
}
