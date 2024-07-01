package com.example.cellphones.model.parking;

import com.example.cellphones.model.base.BaseModel;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tbl_parking_garage")
public class ParkingGarage extends BaseModel {
    private Integer capacity;
    private String location;

    @OneToMany(mappedBy = "parkingGarage", cascade = ALL, fetch = LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ParkingSpace> parkingSpaces;

    @OneToMany(mappedBy = "parkingGarage", cascade = ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Equipment> equipments;
}
