package com.example.cellphones.dto.customer;

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
public class UpdateCustomerRequest {
    private Long id;

    private String fullName;

    private String address;
    private Gender gender;

    private String dateOfBirth;

    private String email;

    private String phoneNumber;

    private String identityCardNumber;
}
