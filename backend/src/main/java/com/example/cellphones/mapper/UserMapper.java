package com.example.cellphones.mapper;

import com.example.cellphones.dto.UserDto;
import com.example.cellphones.dto.customer.CustomerDto;
import com.example.cellphones.dto.employee.EmployeeDto;
import com.example.cellphones.model.parking.Customer;
import com.example.cellphones.model.parking.Employee;
import com.example.cellphones.model.user.User;

public class UserMapper {
    public static UserDto responseUserDtoFromModel(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .gender(String.valueOf(user.getGender()))
                .role(String.valueOf(user.getRole()))
                .active(user.isEnabled())
                .build();
    }

    public static CustomerDto responseCustomerDtoFromModel(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .userId(customer.getUser().getId())
                .email(customer.getEmail())
                .dateOfBirth(customer.getDateOfBirth())
                .address(customer.getAddress())
                .role(customer.getUser().getRole().toString())
                .phoneNumber(customer.getPhoneNumber())
                .identityCardNumber(customer.getIdentityCardNumber())
                .fullName(customer.getFullName())
                .gender(customer.getUser().getGender().name())
                .build();
    }

    public static EmployeeDto responseEmployeeDtoFromModel(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .userId(employee.getUser().getId())
                .position(employee.getPosition())
                .dateOfBirth(employee.getDateOfBirth())
                .fullName(employee.getFullName())
                .role(employee.getUser().getRole().toString())
                .gender(employee.getUser().getGender().name())
                .build();
    }
}
