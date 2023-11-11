package udpm.fpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Flavor {
    @Id
    @Column(name = "id")
    private Long id;
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
