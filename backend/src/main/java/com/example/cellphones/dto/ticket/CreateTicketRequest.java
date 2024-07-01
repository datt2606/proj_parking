package com.example.cellphones.dto.ticket;

import com.example.cellphones.dto.ParkingSpaceDto;
import com.example.cellphones.dto.VehicleDto;
import com.example.cellphones.dto.customer.CustomerDto;
import com.example.cellphones.dto.request.vehicle.CreateVehicleRequest;
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
public class CreateTicketRequest {
    private Long customerId;

    private Long parkingSpaceId;

    private Long vehicleId;

    private CreateVehicleRequest vehicleRequest;

    private Double price;

    private String expiryDate;

    private TicketType ticketType;
}
