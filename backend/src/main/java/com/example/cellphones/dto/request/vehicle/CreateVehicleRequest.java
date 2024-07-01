package com.example.cellphones.dto.request.vehicle;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateVehicleRequest {
    private String vehicleName;
    private String color;
    private String licensePlate;
    private String manufacturer;
    private Long categoryId;
    private Long customerId;
}
