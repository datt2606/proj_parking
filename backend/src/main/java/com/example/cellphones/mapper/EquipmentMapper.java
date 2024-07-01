package com.example.cellphones.mapper;

import com.example.cellphones.dto.EquipmentDto;
import com.example.cellphones.dto.GarageDto;
import com.example.cellphones.model.parking.Equipment;
import com.example.cellphones.model.parking.ParkingGarage;

public class EquipmentMapper {
    public static EquipmentDto responseFromModel(Equipment equipment){
        return EquipmentDto.builder()
                .id(equipment.getId())
                .name(equipment.getEquipmentName())
                .price(equipment.getPrice())
                .purpose(equipment.getPurpose())
                .quantity(equipment.getQuantity())
                .garage(GarageMapper.responseFromModel(equipment.getParkingGarage()))
                .build();
    }
}
