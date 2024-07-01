package com.example.cellphones.service.impl;

import com.example.cellphones.dto.ParkingSpaceDto;
import com.example.cellphones.dto.request.park.UpdateSpaceReq;
import com.example.cellphones.exception.GarageNotFoundByIdException;
import com.example.cellphones.exception.ParkingSpaceNotFoundByIdException;
import com.example.cellphones.mapper.ParkingSpaceMapper;
import com.example.cellphones.model.parking.ParkingGarage;
import com.example.cellphones.model.parking.ParkingSpace;
import com.example.cellphones.repository.GarageRepository;
import com.example.cellphones.repository.ParkingSpaceRepository;
import com.example.cellphones.response.PageResult;
import com.example.cellphones.service.ParkingSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final GarageRepository garageRepository;

//    @Override
//    public ParkingSpaceDto create(String spaceLocation, Long garageId) {
//        ParkingGarage garage =garageRepository.findById(garageId).orElseThrow(() -> new GarageNotFoundByIdException(garageId));
//        try {
//            ParkingSpace parkingSpace = ParkingSpace.builder()
//                    .spaceLocation(spaceLocation)
//                    .isOccupied(false)
//                    .parkingGarage(garage)
//                    .build();
//            parkingSpace = parkingSpaceRepository.save(parkingSpace);
//            return (ParkingSpaceMapper.responseFromModel(parkingSpace));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public ParkingSpaceDto update(Long id, UpdateSpaceReq req) {

        ParkingSpace space = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ParkingSpaceNotFoundByIdException(id));
        ParkingGarage garage = garageRepository.findById(req.getGarageId())
                .orElseThrow(() -> new GarageNotFoundByIdException(req.getGarageId()));
        space.setSpaceLocation(req.getSpaceLocation());
        space.setIsOccupied(req.getIsOccupied());
        space.setParkingGarage(garage);
        space = parkingSpaceRepository.saveAndFlush(space);
        return (ParkingSpaceMapper.responseFromModel(space));
    }

    @Override
    public boolean delete(Long id) {
        try {
            parkingSpaceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Object search(String searchText, Boolean isOccupied, Long garageId, Integer page, Integer limit, Boolean isPaginate) {
        if (isPaginate) {
            Pageable pageable = getPageable(page, limit, null, "DESC");
            Page<ParkingSpace> spaces = parkingSpaceRepository.getPaginate(isOccupied, garageId, pageable);
            return PageResult.builder()
                    .data(spaces.stream()
                            .sorted(Comparator.comparing(ParkingSpace::getSpaceLocation))
                            .map(ParkingSpaceMapper::responseFromModel)
                            .collect(Collectors.toList()))
                    .currentPage(spaces.getNumber())
                    .limit(limit)
                    .totalItems(spaces.getTotalElements())
                    .totalPages(spaces.getTotalPages())
                    .build();
        }
        return parkingSpaceRepository.findAll().stream()
                .filter(s -> s.getIsOccupied().equals(isOccupied))
                .sorted(Comparator.comparing(ParkingSpace::getSpaceLocation))
                .map(ParkingSpaceMapper::responseFromModel)
                .collect(Collectors.toList());
    }

    @Override
    public ParkingSpaceDto getById(Long id) {
        ParkingSpace space = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ParkingSpaceNotFoundByIdException(id));
        return (ParkingSpaceMapper.responseFromModel(space));
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
