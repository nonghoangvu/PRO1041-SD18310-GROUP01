package udpm.fpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Milk")
@Getter
@Setter
public class Milk {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "product_name")
    private String product_name;
    @Column(name = "img")
    private String img;
    @Column(name = "price")
    private Float price;
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
