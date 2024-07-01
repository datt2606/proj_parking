package com.example.cellphones.service.impl;

import com.example.cellphones.dto.VehicleDto;
import com.example.cellphones.dto.request.vehicle.CreateVehicleRequest;
import com.example.cellphones.exception.CategoryNotFoundByIdException;
import com.example.cellphones.exception.CustomException;
import com.example.cellphones.exception.VehicleNotFoundByIdException;
import com.example.cellphones.mapper.VehicleMapper;
import com.example.cellphones.model.parking.Customer;
import com.example.cellphones.model.parking.Vehicle;
import com.example.cellphones.model.parking.VehicleCategory;
import com.example.cellphones.repository.CustomerRepository;
import com.example.cellphones.repository.VehicleCategoryRepository;
import com.example.cellphones.repository.VehicleRepository;
import com.example.cellphones.response.PageResult;
import com.example.cellphones.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    final private VehicleRepository vehicleRepository;
    final private VehicleCategoryRepository vehicleCategoryRepository;
    private final CustomerRepository customerRepository;
    @Override
    public VehicleDto create(CreateVehicleRequest req) {
        VehicleCategory category = this.vehicleCategoryRepository.findById(req.getCategoryId())
                .orElseThrow(()-> new CategoryNotFoundByIdException(req.getCategoryId()));
        Customer customer = customerRepository.findById(req.getCustomerId())
                .orElseThrow(() -> new CustomException("Customer not found"));
        try {
            Vehicle vehicle = Vehicle.builder()
                    .vehicleName(req.getVehicleName())
                    .color(req.getColor())
                    .licensePlate(req.getLicensePlate())
                    .manufacturer(req.getManufacturer())
                    .customer(customer)
                    .vehicleCategory(category)
                    .build();
            vehicle = this.vehicleRepository.save(vehicle);
            return (VehicleMapper.responseFromModel(vehicle));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VehicleDto update(Long id, CreateVehicleRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(()-> new VehicleNotFoundByIdException(id));
        VehicleCategory category = vehicleCategoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new CategoryNotFoundByIdException(request.getCategoryId()));

        vehicle.setVehicleName(request.getVehicleName());
        vehicle.setColor(request.getColor());
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setManufacturer(request.getManufacturer());
        vehicle.setVehicleCategory(category);
        vehicle = vehicleRepository.saveAndFlush(vehicle);
        return (VehicleMapper.responseFromModel(vehicle));
    }

    @Override
    public boolean delete(Long id) {
        try {
            this.vehicleRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Object search(String searchText, Long categoryId, Long customerId, Integer page, Integer limit, Boolean isPaginate) {
        if (isPaginate) {
            Pageable pageable = getPageable(page, limit, null, "DESC");
            Page<Vehicle> vehicles = vehicleRepository.getPaginate(searchText, categoryId, customerId, pageable);
            return PageResult.builder()
                    .data(vehicles.stream()
                            .map(VehicleMapper::responseFromModel)
                            .collect(Collectors.toList()))
                    .currentPage(vehicles.getNumber())
                    .limit(limit)
                    .totalItems(vehicles.getTotalElements())
                    .totalPages(vehicles.getTotalPages())
                    .build();
        }
        return this.vehicleRepository.findAll().stream()
                .filter(s -> s.getCustomer().getId().equals(customerId))
                .map(VehicleMapper::responseFromModel)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDto getById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(()-> new VehicleNotFoundByIdException(id));
        return (VehicleMapper.responseFromModel(vehicle));
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
