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
@Table(name = "Flavor")
@Getter
@Setter
public class Flavor {
    @Id
    private Long id;
    @Column
    private String taste;
    @Column
    private Date create_at;
    @Column
    private String create_b;
}
