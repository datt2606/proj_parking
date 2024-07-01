package com.example.cellphones.dto.employee;

import com.example.cellphones.model.user.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateEmployeeRequest {
    private Long id;
    private String fullName;
    private String dateOfBirth;
    private String position;
    private Gender gender;
}
