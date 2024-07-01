package com.example.cellphones.service.impl;
import com.example.cellphones.dto.VehicleCategoryDto;
import com.example.cellphones.dto.request.vehicle.categoty.CreateVehicleCategoryReq;
import com.example.cellphones.dto.request.vehicle.categoty.UpdateVehicleCategoryReq;
import com.example.cellphones.exception.CategoryNotFoundByIdException;
import com.example.cellphones.mapper.VehicleCategoryMapper;
import com.example.cellphones.model.parking.VehicleCategory;
import com.example.cellphones.repository.VehicleCategoryRepository;
import com.example.cellphones.response.PageResult;
import com.example.cellphones.service.VehicleCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VehicleCategoryServiceImpl implements VehicleCategoryService {
    private final VehicleCategoryRepository categoryRepo;
    @Override
    public VehicleCategoryDto createVehicleCategory(CreateVehicleCategoryReq req) {
        try {
            VehicleCategory category = VehicleCategory.builder()
                    .name(req.getName())
                    .description(req.getDescription())
                    .build();
            category = this.categoryRepo.save(category);
            return (VehicleCategoryMapper.responseFromModel(category));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VehicleCategoryDto updateVehicleCategory(Long id, UpdateVehicleCategoryReq request) {
        VehicleCategory oldCategory = this.categoryRepo.findById(id).orElseThrow(()-> new CategoryNotFoundByIdException(id));
        oldCategory.setName(request.getName());
        oldCategory.setDescription(request.getDescription());
        oldCategory = this.categoryRepo.saveAndFlush(oldCategory);
        return (VehicleCategoryMapper.responseFromModel(oldCategory));
    }

    @Override
    public boolean deleteVehicleCategory(Long id) {
        try {
            this.categoryRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Object searchVehicleCategory(String searchText, Integer page, Integer limit, Boolean isPaginate) {
        if (isPaginate) {
            Pageable pageable = getPageable(page, limit, null, "DESC");
            Page<VehicleCategory> categories = categoryRepo.getPaginate(searchText, pageable);
            return PageResult.builder()
                    .data(categories.stream()
                            .map(VehicleCategoryMapper::responseFromModel)
                            .collect(Collectors.toList()))
                    .currentPage(categories.getNumber())
                    .limit(limit)
                    .totalItems(categories.getTotalElements())
                    .totalPages(categories.getTotalPages())
                    .build();
        }
        return this.categoryRepo.findAll().stream()
                .map(VehicleCategoryMapper::responseFromModel)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleCategoryDto getVehicleCategoryById(Long id) {
        VehicleCategory category = categoryRepo.findById(id)
                .orElseThrow(()-> new CategoryNotFoundByIdException(id));
        return (VehicleCategoryMapper.responseFromModel(category));
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
