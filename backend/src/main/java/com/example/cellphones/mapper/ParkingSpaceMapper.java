package com.example.cellphones.mapper;
import com.example.cellphones.dto.ParkingSpaceDto;
import com.example.cellphones.model.parking.ParkingSpace;

public class ParkingSpaceMapper {
    public static ParkingSpaceDto responseFromModel(ParkingSpace parkingSpace){
        return ParkingSpaceDto.builder()
                .id(parkingSpace.getId())
                .isOccupied(parkingSpace.getIsOccupied())
                .spaceLocation(parkingSpace.getSpaceLocation())
                .garage(GarageMapper.responseFromModel(parkingSpace.getParkingGarage()))
                .build();
    }
}
