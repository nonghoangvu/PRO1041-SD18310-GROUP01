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
    
    @Query("FROM DeliveryNote Where status_id like CONCAT('%', :idStatus, '%')")
    List<DeliveryNote> findByStatus(@Param("idStatus") int idStatus);
    
    @Query("From DeliveryNote d where d.id like CONCAT('%', :idDelivery, '%') and " 
    +"d.customer_name like CONCAT('%',:Customer_name, '%') and "
    +"d.status_id like CONCAT('%', :Status, '%')")
    List<DeliveryNote> searchAll(@Param("idDelivery") String id, @Param ("Customer_name") String Customer_name, @Param("Status") String status);
}
