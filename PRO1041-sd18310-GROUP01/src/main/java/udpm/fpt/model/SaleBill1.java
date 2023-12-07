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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import udpm.fpt.model.User;

@Entity
@Table(name = "SaleBill")
@Getter
@Setter
public class SaleBill1 {

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "sale_event", length = 50)
    private String saleEvent;

    @Column(name = "discount_conditions", length = 50)
    private String discountConditions;

    @Column(name = "percent_decrease")
    private int percentDecrease;

    @Column(name = "start_day")
    @Temporal(TemporalType.DATE)
    private Date startDay;

    @Column(name = "end_day")
    @Temporal(TemporalType.DATE)
    private Date endDay;

    @OneToOne
    @JoinColumn(name = "staff_id")
    private User user;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date createdAt;

    // Constructors, getters, and setters (omitted for brevity)
}
