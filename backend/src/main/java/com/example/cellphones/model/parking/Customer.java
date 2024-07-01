package com.example.cellphones.model.parking;

import com.example.cellphones.model.base.BaseModel;
import com.example.cellphones.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tbl_customer")
public class Customer extends BaseModel {

    @OneToOne(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    private String fullName;

    private String address;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String email;

    private String phoneNumber;

    private String identityCardNumber;

    @OneToMany(mappedBy = "customer", cascade = ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ParkingTicket> parkingTickets;

    @OneToMany(mappedBy = "customer", cascade = ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Vehicle> vehicles;
}
