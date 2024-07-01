package com.example.cellphones.service.impl;

import com.example.cellphones.dto.ticket.CreateTicketRequest;
import com.example.cellphones.exception.CustomException;
import com.example.cellphones.mapper.ParkingTicketMapper;
import com.example.cellphones.model.parking.*;
import com.example.cellphones.repository.*;
import com.example.cellphones.response.PageResult;
import com.example.cellphones.service.ParkingTickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingTickerServiceImpl implements ParkingTickerService {
    private final ParkingTicketRepository parkingTicketRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final VehicleCategoryRepository vehicleCategoryRepository;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


    @Override
    public Object create(CreateTicketRequest req) {
        try {
            Customer customer = this.customerRepository.findById(req.getCustomerId())
                    .orElseThrow(() -> new CustomException("Customer not found"));
            ParkingSpace parkingSpace = this.parkingSpaceRepository.findById(req.getParkingSpaceId())
                    .orElseThrow(() -> new CustomException("ParkingSpace not found"));
            Vehicle vehicle;
            if (req.getVehicleId() != null) {
                vehicle = this.vehicleRepository.findById(req.getVehicleId())
                        .orElseThrow(() -> new CustomException("Vehicle not found"));
            } else {
                vehicle = Vehicle.builder()
                        .customer(customer)
                        .color(req.getVehicleRequest().getColor())
                        .licensePlate(req.getVehicleRequest().getLicensePlate())
                        .manufacturer(req.getVehicleRequest().getManufacturer())
                        .vehicleCategory(vehicleCategoryRepository.findById(req.getVehicleRequest().getCategoryId())
                                .orElseThrow(() -> new CustomException("VehicleCategory not found"))
                        )
                        .vehicleName(req.getVehicleRequest().getVehicleName())
                        .build();
            }
            parkingSpace.setIsOccupied(true);
            ParkingTicket parkingTicket = this.parkingTicketRepository.save(ParkingTicket.builder()
                    .customer(customer)
                    .ticketType(req.getTicketType())
                    .isParking(false)
                    .isActive(false)
                    .expiryDate(formatter.parse(req.getExpiryDate()))
                    .parkingSpace(parkingSpace)
                    .price(req.getPrice())
                    .vehicle(vehicle)
                    .build());
            return ParkingTicketMapper.convertParkingTicket(parkingTicket);
        } catch (Exception e) {
            throw new CustomException("Tạo vé thất bại");
        }
    }

    @Override
    public Object search(String searchText, Long categoryId, Long customerId, Boolean isActive, TicketType ticketType, Integer page, Integer limit, Boolean isPaginate) {
        if (isPaginate) {
            Pageable pageable = getPageable(page, limit, null, "DESC");
            Page<ParkingTicket> users = this.parkingTicketRepository.getPaginate(searchText, categoryId, customerId, isActive, ticketType, pageable);
            return PageResult.builder()
                    .data(users.stream()
                            .map(ParkingTicketMapper::convertParkingTicket)
                            .collect(Collectors.toList()))
                    .currentPage(users.getNumber())
                    .limit(limit)
                    .totalItems(users.getTotalElements())
                    .totalPages(users.getTotalPages())
                    .build();
        }
        return this.parkingTicketRepository.findAll().stream()
                .map(ParkingTicketMapper::convertParkingTicket)
                .collect(Collectors.toList());

    }

    @Override
    public Object getById(Long id) {
        try {
            ParkingTicket ticket = this.parkingTicketRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Ticket not found"));
            return ParkingTicketMapper.convertParkingTicket(ticket);
        } catch (Exception e) {
            throw new CustomException("Không thể tìm thấy vé");
        }
    }

    @Override
    public Object parking(Long id) {
        try {
            ParkingTicket ticket = this.parkingTicketRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Ticket not found"));
            ticket.setIsParking(!ticket.getIsParking());
            this.parkingTicketRepository.save(ticket);
            return "Thay đổi trạng thái đỗ xe";
        } catch (Exception e) {
            throw new CustomException("Không thể thay đổi trạng thái");
        }
    }

    @Override
    public Object disable(Long id) {
        try {
            ParkingTicket ticket = this.parkingTicketRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Ticket not found"));
            ticket.setIsActive(!ticket.getIsActive());
            this.parkingTicketRepository.save(ticket);
            return "Thay đổi trạng thái vé thành công";
        } catch (Exception e) {
            throw new CustomException("Thay đổi trạng thái vé không thành công");
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
