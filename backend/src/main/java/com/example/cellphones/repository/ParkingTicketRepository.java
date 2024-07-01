package com.example.cellphones.repository;

import com.example.cellphones.dto.dashboard.ParkingTotalDto;
import com.example.cellphones.model.parking.ParkingTicket;
import com.example.cellphones.model.parking.TicketType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {

    @Query("select t from ParkingTicket t where (" +
            " (:searchText is null or t.customer.fullName like %:searchText%) " +
            "and (:categoryId is null or t.vehicle.vehicleCategory.id = :categoryId) " +
            "and (:customerId is null or t.customer.id = :customerId) " +
            "and (:isActive is null or t.isActive = :isActive) " +
            "and (:ticketType is null or t.ticketType = :ticketType)" +
            ")")
    Page<ParkingTicket> getPaginate(String searchText, Long categoryId, Long customerId, Boolean isActive, TicketType ticketType, Pageable pageable);

    @Query("select month(o.registrationDate), sum(o.price) from ParkingTicket o " +
            "where year(o.registrationDate) =:year " +
            "group by month(o.registrationDate)")
    List<Object[]> dashboard(Integer year);

    @Query("select new com.example.cellphones.dto.dashboard.ParkingTotalDto(" +
            "SUM(CASE WHEN COALESCE(t.isParking,false) = true THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN COALESCE(t.isParking,false) = false THEN 1 ELSE 0 END)) " +
            "from ParkingTicket t " +
            "where year(t.createdAt) = :year and t.isActive = true")
    ParkingTotalDto totalParked(Integer year);
}
