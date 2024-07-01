package com.example.cellphones.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ParkingSpaceDto {
    private Long id;
    private Boolean isOccupied;
    private Integer spaceLocation;
    private GarageDto garage;
}