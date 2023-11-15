package udpm.fpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Objects;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "packaging_type")
    private String packaging_type;
    @Column(name = "create_at")
    private Date create_at;
    @Column(name = "create_by")
    private String create_by;

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PackagingSpecification other = (PackagingSpecification) obj;
        return Objects.equals(this.packaging_type, other.packaging_type);
    }

    @Override
    public String toString() {
        return this.packaging_type;
    }
}
