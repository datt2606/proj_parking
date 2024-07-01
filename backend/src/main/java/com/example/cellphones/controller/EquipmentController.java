package com.example.cellphones.controller;

import com.example.cellphones.dto.request.equipment.CreateEquipmentReq;
import com.example.cellphones.dto.request.equipment.UpdateEquipmentReq;
import com.example.cellphones.dto.request.park.CreateGarageReq;
import com.example.cellphones.dto.request.park.UpdateGarageReq;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/equipment")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteGarage(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(equipmentService.delete(id))
                        .build());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createGarage(@RequestBody CreateEquipmentReq req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(equipmentService.create(req))
                        .build()
        );
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateGarage(@PathVariable Long id, @RequestBody UpdateEquipmentReq req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(equipmentService.update(id, req))
                        .build()
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getGarageById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(equipmentService.getById(id))
                        .build()
        );
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchGarage(@Param("searchText") String searchText,
                                          @Param("searchText") Long garageId,
                                          @Param("page") Integer page,
                                          @Param("limit") Integer limit,
                                          @Param("isPaginate") Boolean isPaginate) {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(equipmentService.search(searchText,garageId, page, limit, isPaginate))
                .build());
    }
}
