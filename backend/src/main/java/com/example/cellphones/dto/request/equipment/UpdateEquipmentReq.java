package com.example.cellphones.dto.request.equipment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateEquipmentReq {
    private String name;
    private Double price;
    private String purpose;
    private int quantity;
    private Long garageId;
}
