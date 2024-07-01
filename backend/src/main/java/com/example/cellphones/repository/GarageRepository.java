package com.example.cellphones.repository;

import com.example.cellphones.model.parking.ParkingGarage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageRepository extends JpaRepository<ParkingGarage, Long> {
    @Query("select p from ParkingGarage p where (:searchText is null or (p.location like %:searchText% ))")
    Page<ParkingGarage> getPaginate(String searchText, Pageable pageable);
}
