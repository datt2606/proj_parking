package com.example.cellphones.model.parking;

import com.example.cellphones.model.base.BaseModel;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tbl_equiment")
public class Equipment extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "garage_id", referencedColumnName = "id")
    private ParkingGarage parkingGarage;

    private String equipmentName;

    private String purpose;

    private Double price;

    private Integer quantity;

}
