package com.example.cellphones.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EquipmentDto {
    private Long id;
    private String name;
    private Double price;
    private String purpose;
    private int quantity;
    private GarageDto garage;
}
