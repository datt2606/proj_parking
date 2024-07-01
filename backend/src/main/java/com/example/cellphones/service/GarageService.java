package com.example.cellphones.service;

import com.example.cellphones.dto.GarageDto;
import com.example.cellphones.dto.request.park.CreateGarageReq;
import com.example.cellphones.dto.request.park.UpdateGarageReq;

public interface GarageService {
    GarageDto create(CreateGarageReq req);
    GarageDto update(Long id, UpdateGarageReq req);
    boolean delete(Long id);
    Object search(String searchText, Integer page, Integer limit, Boolean isPaginate);
    GarageDto getById(Long id);
}
