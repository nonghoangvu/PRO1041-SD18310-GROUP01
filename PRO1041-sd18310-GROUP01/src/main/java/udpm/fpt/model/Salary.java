package udpm.fpt.model;

//Author: BinhQuoc

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Salary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "salary_type")
    private String salaryType;
    
    @Column(name = "salary_mount")
    private Integer salaryAmount;
    
}
