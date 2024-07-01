package com.example.cellphones.service;

import com.example.cellphones.dto.EquipmentDto;
import com.example.cellphones.dto.request.equipment.CreateEquipmentReq;
import com.example.cellphones.dto.request.equipment.UpdateEquipmentReq;
import com.example.cellphones.dto.request.park.UpdateGarageReq;

public interface EquipmentService {
    EquipmentDto create(CreateEquipmentReq req);
    EquipmentDto update(Long id, UpdateEquipmentReq req);
    boolean delete(Long id);
    Object search(String searchText, Long garageId, Integer page, Integer limit, Boolean isPaginate);
    EquipmentDto getById(Long id);
}
