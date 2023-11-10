/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
@Table(name = "HistoryProductInfo")
@Getter
@Setter
public class HistoryProductInfo {
    @Id
    private Long id;
    @Column
    private Long product_info_id;
    @Column
    private Long milk_id;
    @Column
    private String brand;
    @Column
    private Double volume;
    @Column
    private Long unit_id;
    @Column
    private String origin;
    @Column
    private String composition;
    @Column
    private Long packaging_id;
    @Column
    private String product_description;
    @Column
    private Date create_at;
    @Column
    private String create_by;
}
