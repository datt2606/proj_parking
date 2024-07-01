package com.example.cellphones.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDto {
    private Long id;
    private Long userId;
    private String fullName;
    private Date dateOfBirth;
    private String position;
    private String gender;
    private String role;
}
