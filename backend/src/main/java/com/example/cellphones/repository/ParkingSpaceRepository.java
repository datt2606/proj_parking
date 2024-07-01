package com.example.cellphones.repository;

import com.example.cellphones.model.parking.ParkingSpace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    @Query("select p from ParkingSpace p where (:isOccupied is null or p.isOccupied = :isOccupied) and (:garageId is null or p.parkingGarage.id = :garageId) order by p.spaceLocation")
    Page<ParkingSpace> getPaginate( Boolean isOccupied, Long garageId, Pageable pageable);
}
