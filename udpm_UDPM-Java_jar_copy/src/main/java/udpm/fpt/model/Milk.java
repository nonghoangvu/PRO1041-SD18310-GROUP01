package udpm.fpt.model;

import jakarta.persistence.*;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Milk")
@Getter
@Setter
public class Milk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "barcode")
    private Long barcode;
    @Column(name = "product_name")
    private String product_name;
    @Column(name = "img")
    private String img;
    @Column(name = "price")
    private Integer price;
    @Column(name = "amount")
    private int amount;
    @Column(name = "production_date")
    private Date production_date;
    @Column(name = "expiration_date")
    private Date expiration_date;
    @Column(name = "provider")
    private String provider;
    @Column(name = "isDelete")
    private Boolean isDelete;
}
