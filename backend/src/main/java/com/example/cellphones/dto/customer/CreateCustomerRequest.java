package com.example.cellphones.dto.customer;

import com.example.cellphones.model.user.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateCustomerRequest {
    private String username;

    private String password;

    private String fullName;

    private String address;

    private Gender gender;

    private String dateOfBirth;

    private String email;

    private String phoneNumber;

    private String identityCardNumber;
}
