/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author PHONG PC
 */
@Entity
@Table
@Getter
@Setter
public class BillDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "bill_id")
    private Bill bill_id;
    @OneToOne
    @JoinColumn(name = "milk_id")
    private Milk milk_id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

//    @Column(name = "total_amount")
//    private Double totalAmount;
//    
//    @Column(name = "staff_id")
//    private Integer staffId;
//
//    @Column(name = "sale_bill_id")
//    private Integer saleBillId;

    public BillDetails() {
    }

    
}
