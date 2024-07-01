package com.example.cellphones.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GarageDto {
    private Long id;
    private String location;
    private int capacity;
}
