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
import jakarta.persistence.Table;
import java.util.Date;
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
public class Customer {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fullname", length = 50)
    private String fullname;

    @Column(name = "phone", length = 20)
    private String phone; 

    @Column(name = "email", unique = true, length = 50)
    private String email;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "customer_type_id")
    private Integer customerTypeId;

    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "created_at")
    private Date createdAt;
//    @OneToMany(mappedBy = "customer")
//    private Set <DeliveryNote> delivery;
//    public Customer(Long id, String fullname, String phone, String email, int birthYear, String address, long customerTypeId, String note, Date createdAt) {
//        this.id = id;
//        this.fullname = fullname;
//        this.phone = phone;
//        this.email = email;
//        this.birthYear = birthYear;
//        this.address = address;
//        this.customerTypeId = customerTypeId;
//        this.note = note;
//        this.createdAt = createdAt;
//    }
//
//    public Customer() {
//    }
//    
    
}
