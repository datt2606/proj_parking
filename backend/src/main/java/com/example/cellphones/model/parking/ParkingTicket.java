package com.example.cellphones.model.parking;

import com.example.cellphones.model.base.BaseModel;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.DATE;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tbl_parking_ticket")
public class ParkingTicket extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Temporal(DATE)
    @CreatedDate
    private Date registrationDate;

    @Temporal(DATE)
    private Date expiryDate;

    private Boolean isActive;

    private Double price;

    private Boolean isParking;

    @Enumerated(STRING)
    private TicketType ticketType;

    @OneToOne(cascade = {PERSIST, REFRESH, MERGE})
    @JoinColumn(name = "parking_space_id", referencedColumnName = "id")
    private ParkingSpace parkingSpace;

    @ManyToOne(cascade = {PERSIST, REFRESH, MERGE})
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;
}
