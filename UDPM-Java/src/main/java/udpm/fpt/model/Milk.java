package udpm.fpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
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
    @OneToOne
    @JoinColumn(name = "flavor_id")
    private Flavor flavor;
    @Column(name = "price_retail")
    private Float price_retail;
    @Column(name = "price_wholesale")
    private Float price_wholesale;
    @Column(name = "amount")
    private int amount;
    @Column(name = "production_date")
    private Date production_date;
    @Column(name = "expiration_date")
    private Date expiration_date;
    @Column(name = "provider")
    private String provider;
    @Column(name = "create_at")
    private Date create_at;
    @Column(name = "create_by")
    private String create_by;
}
