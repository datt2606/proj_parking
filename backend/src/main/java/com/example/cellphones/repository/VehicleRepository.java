package com.example.cellphones.repository;

import com.example.cellphones.model.parking.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("select v from Vehicle v where (:searchText is null or (v.vehicleName like %:searchText% or v.licensePlate like %:searchText% or v.manufacturer like %:searchText% or v.color like %:searchText%) " +
            "and (:categoryId is null or :categoryId = v.vehicleCategory.id) " +
            "and (:customerId is null or :customerId = v.customer.id))")
    Page<Vehicle> getPaginate(String searchText, Long categoryId, Long customerId, Pageable pageable);
}
