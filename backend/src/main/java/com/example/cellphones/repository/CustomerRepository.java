package com.example.cellphones.repository;

import com.example.cellphones.dto.dashboard.AgeDto;
import com.example.cellphones.model.parking.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c where :searchText is null or  c.fullName like %:searchText% " +
            "and c.user.enabled = true")
    Page<Customer> getPaginate(String searchText, Pageable pageable);

    @Query("SELECT new com.example.cellphones.dto.dashboard.AgeDto(YEAR(CURRENT_DATE) - YEAR(c.dateOfBirth), COUNT(c)) " +
            "FROM Customer c " +
            "where c.user.enabled = true " +
            "GROUP BY YEAR(CURRENT_DATE) - YEAR(c.dateOfBirth) " +
            "order by YEAR(CURRENT_DATE) - YEAR(c.dateOfBirth)")
    List<AgeDto> dashboard();
}
