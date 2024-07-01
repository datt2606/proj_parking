package com.example.cellphones.dto.request.vehicle.categoty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateVehicleCategoryReq {
    private String name;
    private String description;
}
