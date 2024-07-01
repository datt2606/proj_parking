package com.example.cellphones.service;
import com.example.cellphones.dto.ParkingSpaceDto;
import com.example.cellphones.dto.request.park.UpdateSpaceReq;

public interface ParkingSpaceService {
//    ParkingSpaceDto create(String spaceLocation, Long garageId);
    ParkingSpaceDto update(Long id, UpdateSpaceReq req);
    boolean delete(Long id);
    Object search(String searchText, Boolean isOccupied, Long garageId, Integer page, Integer limit, Boolean isPaginate);
    ParkingSpaceDto getById(Long id);
}
