package udpm.fpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Phong
 */
@Entity
@Table
@Getter
@Setter
public class DeliveryNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creationdate", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date creationdate;

    @Column(name = "customer_name", length = 50)
    private String customer_name;

    @Column(name = "address", length = 50)
    private String address;

    @OneToOne
    @JoinColumn(name = "bill_id", insertable = false, updatable = false)
    private Bill bill_id2;

    @Column(name = "bill_id")
    private Integer bill_id;

    @Column(name = "waybill_number", length = 50)
    private String waybill_number;

    @OneToOne
    @JoinColumn(name = "transport_unit_id", insertable = false, updatable = false)
    private TransportUnit transport_unit_id2;

    @Column(name = "transport_unit_id")
    private Integer transport_unit_id;

    @Column(name = "note", columnDefinition = "NVARCHAR(MAX)")
    private String note;

    @Column(name = "shipping_cost", precision = 10, scale = 2)
    private Double shippingCost;

    @OneToOne
    @JoinColumn(name = "status_id", insertable = false, updatable = false)
    private Status status_id2;

    @Column(name = "status_id")
    private Integer status_id;

    @Column(name = "sdt", length = 50)
    private String sdt;

    @Column(name = "estimatedtime")
    private Date estimatedtime;

    @Column(name = "milk_name")
    private String milk_name;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "total_amount")
    private Double total_amount;

    public DeliveryNote() {
    }

    public DeliveryNote(Date creationdate, String customer_name, String address, Integer bill_id, String waybill_number, Integer transport_unit_id, String note, Double shippingCost, Integer status_id, String sdt, String milk_name, Integer quantity, Double total_amount) {
        this.creationdate = creationdate;
        this.customer_name = customer_name;
        this.address = address;
        this.bill_id = bill_id;
        this.waybill_number = waybill_number;
        this.transport_unit_id = transport_unit_id;
        this.note = note;
        this.shippingCost = shippingCost;
        this.status_id = status_id;
        this.sdt = sdt;
        this.estimatedtime = estimatedtime;
        this.milk_name = milk_name;
        this.quantity = quantity;
        this.total_amount = total_amount;
    }
     public DeliveryNote(Date creationdate, String customer_name, String address, Integer bill_id , Integer transport_unit_id, String note, Double shippingCost, Integer status_id, String sdt, String milk_name, Integer quantity, Double total_amount) {
        this.creationdate = creationdate;
        this.customer_name = customer_name;
        this.address = address;
        this.bill_id = bill_id;
        this.transport_unit_id = transport_unit_id;
        this.note = note;
        this.shippingCost = shippingCost;
        this.status_id = status_id;
        this.sdt = sdt;
        this.estimatedtime = estimatedtime;
        this.milk_name = milk_name;
        this.quantity = quantity;
        this.total_amount = total_amount;
    }
    
}
