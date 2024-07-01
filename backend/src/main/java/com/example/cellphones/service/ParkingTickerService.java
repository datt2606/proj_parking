package com.example.cellphones.service;

import com.example.cellphones.dto.ticket.CreateTicketRequest;
import com.example.cellphones.model.parking.TicketType;

public interface ParkingTickerService {
    Object disable(Long id);

    Object create(CreateTicketRequest request);

    Object search(String searchText, Long categoryId, Long customerId, Boolean isActive, TicketType ticketType, Integer page, Integer limit, Boolean isPaginate);

    Object getById(Long id);

    Object parking(Long id);
}
