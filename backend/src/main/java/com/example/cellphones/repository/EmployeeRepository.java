package com.example.cellphones.repository;

import com.example.cellphones.model.parking.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select c from Employee c where :searchText is null or  c.fullName like %:searchText% " +
            "and c.user.enabled = true")
    Page<Employee> getPaginate(String searchText, Pageable pageable);
}
