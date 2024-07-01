package com.example.cellphones.mapper;

import com.example.cellphones.dto.ticket.ParkingTicketDto;
import com.example.cellphones.model.parking.ParkingTicket;

public class ParkingTicketMapper {
    public static ParkingTicketDto convertParkingTicket(ParkingTicket parkingTicket) {
        return ParkingTicketDto.builder()
                .id(parkingTicket.getId())
                .isParking(parkingTicket.getIsParking())
                .ticketType(parkingTicket.getTicketType())
                .parkingSpace(ParkingSpaceMapper.responseFromModel(parkingTicket.getParkingSpace()))
                .registrationDate(parkingTicket.getRegistrationDate())
                .isActive(parkingTicket.getIsActive())
                .expiryDate(parkingTicket.getExpiryDate())
                .price(parkingTicket.getPrice())
                .ticketType(parkingTicket.getTicketType())
                .customer(UserMapper.responseCustomerDtoFromModel(parkingTicket.getCustomer()))
                .vehicle(VehicleMapper.responseFromModel(parkingTicket.getVehicle()))
                .build();
    }
}
