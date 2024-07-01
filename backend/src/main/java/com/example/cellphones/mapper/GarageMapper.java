package com.example.cellphones.mapper;

import com.example.cellphones.dto.GarageDto;
import com.example.cellphones.model.parking.ParkingGarage;


public class GarageMapper {
    public static GarageDto responseFromModel(ParkingGarage garage){
        return GarageDto.builder()
                .id(garage.getId())
                .location(garage.getLocation())
                .capacity(garage.getCapacity())
                .build();
    }
}
