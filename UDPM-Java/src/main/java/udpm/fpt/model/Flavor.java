package udpm.fpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Flavor")
@Getter
@Setter
public class Flavor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "taste")
    private String taste;

    @Column(name = "create_at")
    private Date create_at;

    @Column(name = "create_by")
    private String create_by;

    @Override
    public String toString() {
        return this.taste;
    }
}
