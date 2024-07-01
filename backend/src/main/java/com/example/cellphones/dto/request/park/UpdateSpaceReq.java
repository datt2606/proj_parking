package com.example.cellphones.dto.request.park;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateSpaceReq {
    private Long garageId;
    private Integer spaceLocation;
    private Boolean isOccupied;
}
