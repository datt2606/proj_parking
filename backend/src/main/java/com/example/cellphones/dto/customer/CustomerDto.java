package com.example.cellphones.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerDto {
    private Long id;

    private Long userId;

    private String fullName;

    private String address;

    private Date dateOfBirth;

    private String email;

    private String phoneNumber;

    private String identityCardNumber;

    private String gender;

    private String role;
}
