package com.example.cellphones.model.parking;

import com.example.cellphones.model.base.BaseModel;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tbl_vehicle_category")
public class VehicleCategory extends BaseModel {
    private String name;
    private String description;

    @OneToMany(mappedBy = "vehicleCategory", cascade = ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Vehicle> vehicles;
}
