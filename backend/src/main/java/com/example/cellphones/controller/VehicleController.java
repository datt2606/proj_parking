package com.example.cellphones.controller;


import com.example.cellphones.dto.request.vehicle.CreateVehicleRequest;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(vehicleService.delete(id))
                        .build());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createVehicle(
            @RequestBody CreateVehicleRequest req
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(vehicleService.create(req))
                        .build()
        );
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Long id, @RequestBody CreateVehicleRequest req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(vehicleService.update(id, req))
                        .build()
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(vehicleService.getById(id))
                        .build()
        );
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchVehicle(@Param("searchText") String searchText,
                                                   @Param("categoryId") Long categoryId,
                                                   @Param("customerId") Long customerId,
                                                   @Param("page") Integer page,
                                                   @Param("limit") Integer limit,
                                                   @Param("isPaginate") Boolean isPaginate) {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(vehicleService.search(searchText, categoryId, customerId, page, limit, isPaginate))
                .build());
    }
}
