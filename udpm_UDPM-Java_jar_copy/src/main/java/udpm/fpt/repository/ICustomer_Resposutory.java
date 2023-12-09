/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package udpm.fpt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import udpm.fpt.model.Customer;


/**
 *
 * @author PHONG PC
 */
public interface ICustomer_Resposutory extends JpaRepository<Customer, Integer> {
    @Query("From Customer WHERE fullname LIKE CONCAT('%', :name, '%')")
    List<Customer> findByName(@Param("name") String name);
}
