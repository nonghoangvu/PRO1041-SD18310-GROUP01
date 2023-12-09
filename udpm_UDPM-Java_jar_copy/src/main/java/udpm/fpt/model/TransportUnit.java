/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author PHONG PC
 */
@Table
@Entity
@Getter
@Setter
public class TransportUnit {
    @Id
    private Integer id;

    @Column(name = "transport_unit_name", length = 255)
    private String transportUnitName;

    // Constructors, getters, setters, and other methods...
    // Constructors...
    public TransportUnit() {
    
    }

    public TransportUnit(Integer id, String transportUnitName, String address, String phone) {
        this.id = id;
        this.transportUnitName = transportUnitName;
    }


}
