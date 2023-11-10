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
@Table(name = "PackagingSpecification")
@Getter
@Setter
public class PackagingSpecification {
    @Id
    private Long id;
    @Column
    private String packaging_type;
    @Column
    private Date create_at;
    @Column
    private String create_by;
}
