package com.example.cellphones.controller;
import com.example.cellphones.dto.request.park.UpdateSpaceReq;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.service.ParkingSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/parking-space")
@RequiredArgsConstructor
public class ParkingSpaceController {
    private final ParkingSpaceService parkingSpaceService;

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteGarage(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(parkingSpaceService.delete(id))
                        .build());
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateGarage(@PathVariable Long id, @RequestBody UpdateSpaceReq req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(parkingSpaceService.update(id, req))
                        .build()
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getGarageById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(parkingSpaceService.getById(id))
                        .build()
        );
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchGarage(@Param("searchText") String searchText,
                                          @Param("isOccupied") Boolean isOccupied,
                                          @Param("garageId") Long garageId,
                                          @Param("page") Integer page,
                                          @Param("limit") Integer limit,
                                          @Param("isPaginate") Boolean isPaginate) {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(parkingSpaceService.search(searchText, isOccupied, garageId, page, limit, isPaginate))
                .build());
    }
}
