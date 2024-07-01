package com.example.cellphones.service.impl;

import com.example.cellphones.dto.UserDto;
import com.example.cellphones.dto.customer.CreateCustomerRequest;
import com.example.cellphones.dto.customer.UpdateCustomerRequest;
import com.example.cellphones.dto.employee.CreateEmployeeRequest;
import com.example.cellphones.dto.employee.UpdateEmployeeRequest;
import com.example.cellphones.dto.request.user.CreateUserReq;
import com.example.cellphones.dto.request.user.UpdateUserReq;
import com.example.cellphones.exception.CustomException;
import com.example.cellphones.exception.UserNotFoundByIdException;
import com.example.cellphones.exception.UserNotFoundByUsername;
import com.example.cellphones.mapper.UserMapper;
import com.example.cellphones.model.parking.Customer;
import com.example.cellphones.model.parking.Employee;
import com.example.cellphones.model.user.Gender;
import com.example.cellphones.model.user.Role;
import com.example.cellphones.model.user.User;
import com.example.cellphones.repository.CustomerRepository;
import com.example.cellphones.repository.EmployeeRepository;
import com.example.cellphones.repository.UserRepository;
import com.example.cellphones.response.PageResult;
import com.example.cellphones.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final private UserRepository userRepo;
    private final CustomerRepository customerRepo;
    private final EmployeeRepository employeeRepo;
    final private PasswordEncoder passwordEncoder;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public List<UserDto> getUsers() {
        List<User> users = this.userRepo.findAll();
        return (users.stream().map(UserMapper::responseUserDtoFromModel).collect(Collectors.toList()));
    }

    @Override
    public boolean createUser(CreateUserReq request) {
        try {

            User user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.CUSTOMER)
                    .enabled(true)
                    .build();
            this.userRepo.save(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean activeAccount(String username) {
        try {
            User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundByUsername(username));
            user.setEnabled(true);
            this.userRepo.saveAndFlush(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean inactiveAccount(String username) {
        try {
            User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundByUsername(username));
            user.setEnabled(false);
            this.userRepo.saveAndFlush(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean setRoleForUser(String username, Role role) {
        try {
            User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundByUsername(username));
            user.setRole(role);
            this.userRepo.saveAndFlush(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public UserDto updateUser(UpdateUserReq request, Long userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundByIdException(userId));
        user.setGender(Gender.valueOf(request.getGender()));
        user = this.userRepo.save(user);
        return (UserMapper.responseUserDtoFromModel(user));
    }


    //  ------------------------------------------------------------
    @Override
    public Object getUserInfo(Long id) {
        User user = this.userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));
        if (user.getRole().equals(Role.CUSTOMER)) {
            return UserMapper.responseCustomerDtoFromModel(user.getCustomer());
        }
        return UserMapper.responseEmployeeDtoFromModel(user.getEmployee());
    }

    @Override
    public String deleteUser(Long id) {
        try {
            User user = this.userRepo.findById(id)
                    .orElseThrow(() -> new CustomException("User not found"));
            user.setEnabled(false);
            this.userRepo.save(user);
            return "Xóa tài khoản thành công";
//            this.userRepo.deleteUserByUsername(username);
        } catch (Exception e) {
            return "Xóa tài khoản thất bại";
        }
    }


    @Override
    public Object createCustomer(CreateCustomerRequest request) {
        try {
            Customer customer = Customer.builder()
                    .email(request.getEmail())
                    .address(request.getAddress())
                    .fullName(request.getFullName())
                    .phoneNumber(request.getPhoneNumber())
                    .identityCardNumber(request.getIdentityCardNumber())
                    .dateOfBirth(formatter.parse(request.getDateOfBirth()))
                    .build();
            User user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.CUSTOMER)
                    .customer(customer)
                    .gender(request.getGender())
                    .enabled(true)
                    .build();
            customer.setUser(user);
            this.userRepo.save(user);
            return "Tạo tài khoản thành công";
        } catch (Exception e) {
            throw new CustomException("Tạo tài khoản thất bại");
        }
    }

    @Override
    public Object createEmployee(CreateEmployeeRequest request) {
        try {
            Employee employee = Employee.builder()
                    .fullName(request.getFullName())
                    .position(request.getPosition())
                    .dateOfBirth(formatter.parse(request.getDateOfBirth()))
                    .build();
            User user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.EMPLOYEE)
                    .employee(employee)
                    .gender(request.getGender())
                    .enabled(true)
                    .build();
            employee.setUser(user);
            this.userRepo.save(user);
            return "Tạo tài khoản thành công";
        } catch (Exception e) {
            throw new CustomException("Tạo tài khoản thất bại");
        }
    }

    @Override
    public Object updateCustomer(UpdateCustomerRequest req) {
        try {
            Customer customer = this.customerRepo.findById(req.getId())
                    .orElseThrow(() -> new CustomException("Không tìm thấy customer"));
            customer.setAddress(req.getAddress());
            customer.setFullName(req.getFullName());
            customer.setIdentityCardNumber(req.getIdentityCardNumber());
            customer.setDateOfBirth(formatter.parse(req.getDateOfBirth()));
            customer.setEmail(req.getEmail());
            customer.getUser().setGender(req.getGender());
            this.customerRepo.save(customer);
            return "Cập nhật thành công";
        } catch (Exception e) {
            throw new CustomException("Cập nhật thất bại");
        }
    }

    @Override
    public Object updateEmployee(UpdateEmployeeRequest req) {
        try {
            Employee employee = this.employeeRepo.findById(req.getId())
                    .orElseThrow(() -> new CustomException("Không tìm thấy employee"));
            employee.setFullName(req.getFullName());
            employee.setDateOfBirth(formatter.parse(req.getDateOfBirth()));
            employee.setPosition(req.getPosition());
            employee.setFullName(req.getFullName());
            employee.getUser().setGender(req.getGender());
            this.employeeRepo.save(employee);
            return "Cập nhật thành công";
        } catch (Exception e) {
            throw new CustomException("Cập nhật thất bại");
        }
    }

    @Override
    public Object searchUser(String searchText, Role role, Integer page, Integer limit, Boolean isPaginate) {
        if (isPaginate) {
            Pageable pageable = getPageable(page, limit, null, "DESC");
            if (role == Role.CUSTOMER) {
                Page<Customer> users = this.customerRepo.getPaginate(searchText, pageable);
                return PageResult.builder()
                        .data(users.stream()
                                .map(UserMapper::responseCustomerDtoFromModel)
                                .collect(Collectors.toList()))
                        .currentPage(users.getNumber())
                        .limit(limit)
                        .totalItems(users.getTotalElements())
                        .totalPages(users.getTotalPages())
                        .build();
            } else {
                Page<Employee> users = this.employeeRepo.getPaginate(searchText, pageable);
                return PageResult.builder()
                        .data(users.stream()
                                .map(UserMapper::responseEmployeeDtoFromModel)
                                .collect(Collectors.toList()))
                        .currentPage(users.getNumber())
                        .limit(limit)
                        .totalItems(users.getTotalElements())
                        .totalPages(users.getTotalPages())
                        .build();
            }
        }
        if (role == Role.CUSTOMER) {
            return this.customerRepo.findAll().stream()
                    .map(UserMapper::responseCustomerDtoFromModel)
                    .collect(Collectors.toList());
        } else {
            return this.employeeRepo.findAll().stream()
                    .map(UserMapper::responseEmployeeDtoFromModel)
                    .collect(Collectors.toList());
        }
    }

    protected Pageable getPageable(Integer page, Integer size, String sortBy, String sortOrder) {
        page = (page == null) ? 0 : page;
        size = (size == null) ? 20 : size;
        sortBy = sortBy == null || sortBy.isEmpty() ? "id" : sortBy;
        Sort.Direction sortOrderRequest = sortOrder == null || sortOrder.isEmpty() ? Sort.Direction.ASC : Sort.Direction.fromString(sortOrder);
        Sort sort = Sort.by(sortOrderRequest, sortBy);
        return PageRequest
                .of(page, size, sort);
    }
}
