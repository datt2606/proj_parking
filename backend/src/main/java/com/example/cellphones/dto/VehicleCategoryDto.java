package com.example.cellphones.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VehicleCategoryDto {
    private Long id;
    private String name;
    private String description;
}
