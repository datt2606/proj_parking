package com.example.cellphones.service;

import com.example.cellphones.dto.VehicleCategoryDto;
import com.example.cellphones.dto.request.vehicle.categoty.CreateVehicleCategoryReq;
import com.example.cellphones.dto.request.vehicle.categoty.UpdateVehicleCategoryReq;


public interface VehicleCategoryService {
    VehicleCategoryDto createVehicleCategory(CreateVehicleCategoryReq req);
    VehicleCategoryDto updateVehicleCategory(Long id, UpdateVehicleCategoryReq request);
    boolean deleteVehicleCategory(Long id);
    Object searchVehicleCategory(String searchText, Integer page, Integer limit, Boolean isPaginate);
    VehicleCategoryDto getVehicleCategoryById(Long id);
}
