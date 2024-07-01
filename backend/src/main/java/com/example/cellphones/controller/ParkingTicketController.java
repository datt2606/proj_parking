package com.example.cellphones.controller;

import com.example.cellphones.dto.ticket.CreateTicketRequest;
import com.example.cellphones.model.parking.TicketType;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.service.ParkingTickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/parking-ticket")
@RequiredArgsConstructor
public class ParkingTicketController {
    private final ParkingTickerService parkingTickerService;

    @PutMapping(path = "/active/{id}")
    public ResponseEntity<?> disableTicket(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(parkingTickerService.disable(id))
                        .build());
    }

    @PutMapping(path = "/parking/{id}")
    public ResponseEntity<?> parking(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(parkingTickerService.parking(id))
                        .build());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createTicket(@RequestBody CreateTicketRequest request) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(parkingTickerService.create(request))
                        .build()
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(parkingTickerService.getById(id))
                        .build()
        );
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> search(@Param("searchText") String searchText,
                                    @Param("categoryId") Long categoryId,
                                    @Param("customerId") Long customerId,
                                    @Param("isActive") Boolean isActive,
                                    @Param("ticketType") TicketType ticketType,
                                    @Param("page") Integer page,
                                    @Param("limit") Integer limit,
                                    @Param("isPaginate") Boolean isPaginate) {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(parkingTickerService.search(searchText, categoryId, customerId, isActive, ticketType, page, limit, isPaginate))
                .build());
    }
}
