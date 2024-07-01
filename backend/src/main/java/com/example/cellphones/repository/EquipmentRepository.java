package com.example.cellphones.repository;

import com.example.cellphones.model.parking.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    @Query("select e from Equipment e where (:searchText is null or (lower(e.equipmentName) like %:searchText% or e.purpose like %:searchText%) " +
            "and (:garageId is null or :garageId = e.parkingGarage.id))")
    Page<Equipment> getPaginate(String searchText, Long garageId, Pageable pageable);
}
