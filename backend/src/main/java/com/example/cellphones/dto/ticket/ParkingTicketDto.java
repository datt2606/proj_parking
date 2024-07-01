package com.example.cellphones.dto.ticket;

import com.example.cellphones.dto.ParkingSpaceDto;
import com.example.cellphones.dto.VehicleDto;
import com.example.cellphones.dto.customer.CustomerDto;
import com.example.cellphones.model.parking.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ParkingTicketDto {
    private Long id;

    private CustomerDto customer;

    private Date registrationDate;

    private Date expiryDate;

    private Boolean isActive;

    private Double price;

    private Boolean isParking;

    private ParkingSpaceDto parkingSpace;

    private VehicleDto vehicle;

    private TicketType ticketType;
}
