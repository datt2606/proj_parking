package com.example.cellphones.model.parking;

import com.example.cellphones.model.base.BaseModel;
import com.example.cellphones.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tbl_vehicle")
public class Vehicle extends BaseModel {
    private String vehicleName;
    private String color;
    private String licensePlate;
    private String manufacturer;

    @OneToMany(mappedBy = "vehicle", cascade = ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ParkingTicket> parkingTickets;

    @ManyToOne
    @JoinColumn(name = "customer_vehicle_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "vehicle_category_id", referencedColumnName = "id")
    private VehicleCategory vehicleCategory;
}
