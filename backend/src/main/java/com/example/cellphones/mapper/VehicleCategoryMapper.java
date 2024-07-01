package com.example.cellphones.mapper;

import com.example.cellphones.dto.VehicleCategoryDto;
import com.example.cellphones.model.parking.VehicleCategory;

public class VehicleCategoryMapper {
    public static VehicleCategoryDto responseFromModel(VehicleCategory vehicleCategory){
        return VehicleCategoryDto.builder()
                .id(vehicleCategory.getId())
                .name(vehicleCategory.getName())
                .description(vehicleCategory.getDescription())
                .build();
    }
}
