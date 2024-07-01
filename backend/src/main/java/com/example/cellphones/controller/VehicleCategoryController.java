package com.example.cellphones.controller;

import com.example.cellphones.dto.request.vehicle.categoty.CreateVehicleCategoryReq;
import com.example.cellphones.dto.request.vehicle.categoty.UpdateVehicleCategoryReq;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.service.VehicleCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/vehicle-category")
@RequiredArgsConstructor
public class VehicleCategoryController {
    private final VehicleCategoryService vehicleCategoryService;

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteVehicleCategory(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(vehicleCategoryService.deleteVehicleCategory(id))
                        .build());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createVehicleCategory(
            @RequestBody CreateVehicleCategoryReq req
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(vehicleCategoryService.createVehicleCategory(req))
                        .build()
        );
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateVehicleCategory(@PathVariable Long id, @RequestBody UpdateVehicleCategoryReq req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(vehicleCategoryService.updateVehicleCategory(id, req))
                        .build()
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getVehicleCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(vehicleCategoryService.getVehicleCategoryById(id))
                        .build()
        );
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchVehicleCategory(@Param("searchText") String searchText,
                                           @Param("page") Integer page,
                                           @Param("limit") Integer limit,
                                           @Param("isPaginate") Boolean isPaginate) {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(vehicleCategoryService.searchVehicleCategory(searchText, page, limit, isPaginate))
                .build());
    }
}
