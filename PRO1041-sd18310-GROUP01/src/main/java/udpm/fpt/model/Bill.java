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
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.springframework.data.repository.cdi.Eager;

/**
 *
 * @author PHONG PC
 */
@Table
@Entity
@Getter
@Setter
public class Bill {
    @Id
    private Integer id;
    @OneToOne
    @JoinColumn (name = "customer_id")
    private Customer customerId;
    @Column(name = "payment_menthod", length = 50)
    private String payment_method;

    @Column(name = "payment_status", length = 50)
    private String payment_status;

    @Column(name = "coupon_id")
    private Integer couponId;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "total_amount_after_tax")
    private Double totalAmountAfterTax;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "staff_id")
    private Integer staff_id;
    
    @Column(name = "sale_bill_id")
    private Integer sale_bill_id;
    
    @Column(name = "shopping_method")
    private String shopping_method;

    public Bill() {
    }   
}
