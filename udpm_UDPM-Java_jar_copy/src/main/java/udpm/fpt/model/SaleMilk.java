package udpm.fpt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author NONG HOANG VU
 */
@Entity
@Table(name = "SaleMilk")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleMilk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sale_event")
    private String sale_event;

    @Column(name = "percent_decrease")
    private Integer percent_decrease;

    @Column(name = "start_day")
    private Date start_day;

    @Column(name = "end_day")
    private Date end_day;

    @OneToOne
    @JoinColumn(name = "milk_id")
    private Milk milk;

    @OneToOne
    @JoinColumn(name = "staff_id")
    private User user;

    @Column(name = "created_at")
    private Date created_at;

}
