/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import udpm.fpt.model.Milk;

/**
 *
 * @author PHONG PC
 */
public interface IMilk_Respository extends JpaRepository<Milk, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Milk SET amount = amount - :quanity WHERE id = :milkId")
    void updateQuantity(@Param("quanity") int amount, @Param("milkId") Long id);
}
