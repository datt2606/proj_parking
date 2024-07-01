package com.example.cellphones.controller;

import com.example.cellphones.dto.request.park.CreateGarageReq;
import com.example.cellphones.dto.request.park.UpdateGarageReq;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.service.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/garage")
@RequiredArgsConstructor
public class GarageController {
    private final GarageService garageService;

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteGarage(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(garageService.delete(id))
                        .build());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createGarage(@RequestBody CreateGarageReq req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(garageService.create(req))
                        .build()
        );
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateGarage(@PathVariable Long id, @RequestBody UpdateGarageReq req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(garageService.update(id, req))
                        .build()
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getGarageById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(garageService.getById(id))
                        .build()
        );
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchGarage(@Param("searchText") String searchText,
                                                   @Param("page") Integer page,
                                                   @Param("limit") Integer limit,
                                                   @Param("isPaginate") Boolean isPaginate) {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(garageService.search(searchText, page, limit, isPaginate))
                .build());
    }
}
