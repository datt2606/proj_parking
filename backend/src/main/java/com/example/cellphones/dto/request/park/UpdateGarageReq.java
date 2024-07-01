package com.example.cellphones.dto.request.park;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateGarageReq {
    private String location;
    private int capacity;
}
