package com.example.cellphones.controller;

import com.example.cellphones.dto.customer.CreateCustomerRequest;
import com.example.cellphones.dto.customer.UpdateCustomerRequest;
import com.example.cellphones.dto.employee.CreateEmployeeRequest;
import com.example.cellphones.dto.employee.UpdateEmployeeRequest;
import com.example.cellphones.dto.request.user.SetRoleReq;
import com.example.cellphones.model.user.Role;
import com.example.cellphones.model.user.User;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/info")
    public ResponseEntity<?> getUserInfo() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(userService.getUserInfo(currentUser.getId()))
                        .build()
        );
    }

    @PostMapping(path = "/create-customer")
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerRequest req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(userService.createCustomer(req))
                        .build());
    }


    @PostMapping(path = "/create-employee")
//    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity<?> createCustomer(@RequestBody CreateEmployeeRequest req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(userService.createEmployee(req))
                        .build());
    }


    @GetMapping(path = "/search")
    public ResponseEntity<?> search(@Param("searchText") String searchText,
                                    @Param("page") Integer page,
                                    @Param("role") Role role,
                                    @Param("limit") Integer limit,
                                    @Param("isPaginate") Boolean isPaginate) {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(userService.searchUser(searchText, role, page, limit, isPaginate))
                .build());
    }

    @PutMapping(path = "/update-customer")
    public ResponseEntity<?> updateCustomer(@RequestBody UpdateCustomerRequest req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(userService.updateCustomer(req))
                        .build()
        );
    }

    @PutMapping(path = "/update-employee")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<?> updateEmployee(@RequestBody UpdateEmployeeRequest req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(userService.updateEmployee(req))
                        .build()
        );
    }

    @DeleteMapping(path = "/delete/{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(userService.deleteUser(id))
                .build());
    }
    // --------------------------------------------------------------------------

    @PostMapping(path = "/active/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> activeAccount(@PathVariable String username) {
        boolean res = userService.activeAccount(username);
        if (res)
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .data("Kích hoạt tài khoản thành công")
                            .build());
        else
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .data("Kích hoạt tài khoản thất bại")
                            .build());
    }

    @PostMapping(path = "/inactive/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> inactiveAccount(@PathVariable String username) {
        boolean res = userService.inactiveAccount(username);
        if (res)
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .data("Kích hoạt tài khoản thành công")
                            .build());
        else
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .data("Kích hoạt tài khoản thất bại")
                            .build());
    }

    @PostMapping(path = "/setRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> setRole(@RequestBody SetRoleReq req) {
        boolean res = userService.setRoleForUser(req.getUsername(), req.getRole());
        if (res)
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .data("Thiết lập vai trò thành công")
                    .build());
        else
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .data("Thiết lập vai trò thất bại")
                    .build());
    }
}
