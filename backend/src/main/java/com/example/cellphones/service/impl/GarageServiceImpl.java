package com.example.cellphones.service.impl;

import com.example.cellphones.dto.GarageDto;
import com.example.cellphones.dto.request.park.CreateGarageReq;
import com.example.cellphones.dto.request.park.UpdateGarageReq;
import com.example.cellphones.exception.CategoryNotFoundByIdException;
import com.example.cellphones.exception.GarageNotFoundByIdException;
import com.example.cellphones.mapper.GarageMapper;
import com.example.cellphones.model.parking.ParkingGarage;
import com.example.cellphones.model.parking.ParkingSpace;
import com.example.cellphones.repository.GarageRepository;
import com.example.cellphones.response.PageResult;
import com.example.cellphones.service.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class GarageServiceImpl implements GarageService {
    private final GarageRepository garageRepository;

    @Override
    public GarageDto create(CreateGarageReq req) {
        try {
            ParkingGarage garage = ParkingGarage.builder()
                    .location(req.getLocation())
                    .capacity(req.getCapacity())
                    .build();
            ParkingGarage finalGarage = garage;
            Set<ParkingSpace> parkingSpaces = IntStream.range(0, req.getCapacity())
                    .mapToObj(i -> ParkingSpace.builder()
                            .isOccupied(false)
                            .parkingGarage(finalGarage)
                            .spaceLocation(i + 1)
                            .build())
                    .collect(Collectors.toSet());
            garage.setParkingSpaces(parkingSpaces);
            garage = garageRepository.save(garage);
            return (GarageMapper.responseFromModel(garage));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GarageDto update(Long id, UpdateGarageReq req) {
        ParkingGarage garage = garageRepository.findById(id).orElseThrow(() -> new CategoryNotFoundByIdException(id));
        garage.setLocation(req.getLocation());
        garage.setCapacity(req.getCapacity());
        garage.setUpdatedAt(new Date());
        garage = garageRepository.saveAndFlush(garage);
        return (GarageMapper.responseFromModel(garage));
    }

    @Override
    public boolean delete(Long id) {
        try {
            garageRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Object search(String searchText, Integer page, Integer limit, Boolean isPaginate) {
        if (isPaginate) {
            Pageable pageable = getPageable(page, limit, null, "DESC");
            Page<ParkingGarage> categories = garageRepository.getPaginate(searchText, pageable);
            return PageResult.builder()
                    .data(categories.stream()
                            .map(GarageMapper::responseFromModel)
                            .collect(Collectors.toList()))
                    .currentPage(categories.getNumber())
                    .limit(limit)
                    .totalItems(categories.getTotalElements())
                    .totalPages(categories.getTotalPages())
                    .build();
        }
        return this.garageRepository.findAll().stream()
                .map(GarageMapper::responseFromModel)
                .collect(Collectors.toList());
    }

    @Override
    public GarageDto getById(Long id) {
        ParkingGarage garage = garageRepository.findById(id)
                .orElseThrow(() -> new GarageNotFoundByIdException(id));
        return (GarageMapper.responseFromModel(garage));
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
