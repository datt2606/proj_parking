package com.example.cellphones.service;
import com.example.cellphones.dto.VehicleDto;
import com.example.cellphones.dto.request.vehicle.CreateVehicleRequest;

public interface VehicleService {
    VehicleDto create(CreateVehicleRequest req);
    VehicleDto update(Long id, CreateVehicleRequest request);
    boolean delete(Long id);
    Object search(String searchText, Long categoryId, Long customerId, Integer page, Integer limit, Boolean isPaginate);
    VehicleDto getById(Long id);
}
