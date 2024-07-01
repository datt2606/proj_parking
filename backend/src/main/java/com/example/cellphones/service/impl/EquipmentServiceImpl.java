package com.example.cellphones.service.impl;

import com.example.cellphones.dto.EquipmentDto;
import com.example.cellphones.dto.request.equipment.CreateEquipmentReq;
import com.example.cellphones.dto.request.equipment.UpdateEquipmentReq;
import com.example.cellphones.exception.*;
import com.example.cellphones.mapper.EquipmentMapper;
import com.example.cellphones.model.parking.*;
import com.example.cellphones.repository.EquipmentRepository;
import com.example.cellphones.repository.GarageRepository;
import com.example.cellphones.response.PageResult;
import com.example.cellphones.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {
    private final GarageRepository garageRepository;
    private final EquipmentRepository equipmentRepository;
    @Override
    public EquipmentDto create(CreateEquipmentReq req) {
        ParkingGarage garage = garageRepository.findById(req.getGarageId())
                .orElseThrow(()-> new GarageNotFoundByIdException(req.getGarageId()));
        try {
            Equipment equipment = Equipment.builder()
                    .equipmentName(req.getName())
                    .price(req.getPrice())
                    .purpose(req.getPurpose())
                    .quantity(req.getQuantity())
                    .parkingGarage(garage)
                    .build();
            equipment = equipmentRepository.save(equipment);
            return (EquipmentMapper.responseFromModel(equipment));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EquipmentDto update(Long id, UpdateEquipmentReq req) {
        ParkingGarage garage = garageRepository.findById(req.getGarageId())
                .orElseThrow(()-> new GarageNotFoundByIdException(req.getGarageId()));
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new EquipmentNotFoundByIdException(id));
        equipment.setEquipmentName(req.getName());
        equipment.setPrice(req.getPrice());
        equipment.setPurpose(req.getPurpose());
        equipment.setQuantity(req.getQuantity());
        equipment.setParkingGarage(garage);
        equipment = equipmentRepository.saveAndFlush(equipment);
        return (EquipmentMapper.responseFromModel(equipment));
    }

    @Override
    public boolean delete(Long id) {
        try {
            equipmentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Object search(String searchText, Long garageId, Integer page, Integer limit, Boolean isPaginate) {
        if (isPaginate) {
            Pageable pageable = getPageable(page, limit, null, "DESC");
            Page<Equipment> equipments = equipmentRepository.getPaginate(searchText.toLowerCase(), garageId, pageable);
            return PageResult.builder()
                    .data(equipments.stream()
                            .map(EquipmentMapper::responseFromModel)
                            .collect(Collectors.toList()))
                    .currentPage(equipments.getNumber())
                    .limit(limit)
                    .totalItems(equipments.getTotalElements())
                    .totalPages(equipments.getTotalPages())
                    .build();
        }
        return this.equipmentRepository.findAll().stream()
                .map(EquipmentMapper::responseFromModel)
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentDto getById(Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(()-> new EquipmentNotFoundByIdException(id));
        return (EquipmentMapper.responseFromModel(equipment));
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
