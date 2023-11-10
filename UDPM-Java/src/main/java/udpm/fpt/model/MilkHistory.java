package udpm.fpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author NONG HOANG VU
 */
@Entity
@Table(name = "MilkHistory")
@Getter
@Setter
public class MilkHistory {
    @Id
    private Long id;
    @Column
    private Long milk_id;
    @Column
    private String product_name;
    @Column
    private String img;
    @Column
    private Long flavor_id;
    @Column
    private Float price_retail;
    @Column
    private Float price_wholesale;
    @Column
    private int amount;
    @Column
    private Date production_date;
    @Column
    private Date expiration_date;
    @Column
    private String provider;
    @Column
    private Date create_at;
    @Column
    private String create_by;
}
