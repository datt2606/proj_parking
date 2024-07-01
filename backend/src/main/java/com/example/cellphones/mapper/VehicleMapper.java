package com.example.cellphones.mapper;
import com.example.cellphones.dto.VehicleDto;
import com.example.cellphones.model.parking.Vehicle;


public class VehicleMapper {
    public static VehicleDto responseFromModel(Vehicle vehicle){
        return VehicleDto.builder()
                .id(vehicle.getId())
                .customerName(vehicle.getCustomer().getFullName())
                .licensePlate(vehicle.getLicensePlate())
                .vehicleName(vehicle.getVehicleName())
                .color(vehicle.getColor())
                .manufacturer(vehicle.getManufacturer())
                .vehicleCategory(VehicleCategoryMapper.responseFromModel(vehicle.getVehicleCategory()))
                .build();
    }
}
