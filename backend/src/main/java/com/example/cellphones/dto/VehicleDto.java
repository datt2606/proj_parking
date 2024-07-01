package com.example.cellphones.dto;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VehicleDto {
    private Long id;
    private String customerName;
    private String vehicleName;
    private String color;
    private String licensePlate;
    private String manufacturer;
    private VehicleCategoryDto vehicleCategory;
}
