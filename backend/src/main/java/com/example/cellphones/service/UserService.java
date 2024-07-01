package com.example.cellphones.service;

import com.example.cellphones.dto.UserDto;
import com.example.cellphones.dto.customer.CreateCustomerRequest;
import com.example.cellphones.dto.customer.UpdateCustomerRequest;
import com.example.cellphones.dto.employee.CreateEmployeeRequest;
import com.example.cellphones.dto.employee.UpdateEmployeeRequest;
import com.example.cellphones.dto.request.user.CreateUserReq;
import com.example.cellphones.dto.request.user.UpdateUserReq;
import com.example.cellphones.model.user.Role;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    boolean createUser(CreateUserReq request);
    boolean activeAccount(String username);
    boolean inactiveAccount(String username);
    boolean setRoleForUser(String username, Role role);
    UserDto updateUser(UpdateUserReq request, Long userId);


    // ----------------------------------------------------------
    Object getUserInfo(Long id);

    String deleteUser(Long id);

    Object searchUser(String searchText, Role role, Integer page, Integer limit, Boolean isPaginate);

    Object createCustomer(CreateCustomerRequest req);

    Object createEmployee(CreateEmployeeRequest req);

    Object updateCustomer(UpdateCustomerRequest req);

    Object updateEmployee(UpdateEmployeeRequest req);
}
