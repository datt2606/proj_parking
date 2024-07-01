package com.example.cellphones.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ParkingTotalDto {
    private Long parkedCount;
    private Long notParkedCount;
}
