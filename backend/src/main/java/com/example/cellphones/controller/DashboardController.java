package com.example.cellphones.controller;

import com.example.cellphones.dto.dashboard.AgeDto;
import com.example.cellphones.dto.dashboard.ParkingTotalDto;
import com.example.cellphones.repository.CustomerRepository;
import com.example.cellphones.repository.ParkingTicketRepository;
import com.example.cellphones.response.ResponseObject;
import lombok.*;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(path = "/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    /**
     *  thống kê về khách hàng theo độ tuổi
     *  doanh thu theo thang va nam
     *  số lượng xe gửi / lay
     */
    private final ParkingTicketRepository parkingTicketRepository;
    private final CustomerRepository customerRepository;

    private String getMonthYearKey(Integer month, Integer year) {
        return month + "/" + year;
    }

    @GetMapping(path = "/user-with-age")
    public ResponseEntity<?> getRevenue() {
        List<AgeDto> dashboard = this.customerRepository.dashboard();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(dashboard)
                        .build()
        );
    }

    @GetMapping(path = "/revenue")
    public ResponseEntity<?> getRevenuePriceWithMonth(@Param("year") Integer year) {
        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) months.add(i);
        Map<String, Double> initMap = months.stream()
                .collect(Collectors.toMap(entry -> getMonthYearKey(entry, year), row -> 0.0, (k, v) -> v, LinkedHashMap::new));
        List<Object[]> dashboard = this.parkingTicketRepository.dashboard(year);
        if (!dashboard.isEmpty()) {
            dashboard.stream()
                    .collect(Collectors.groupingBy(row -> ((Integer) row[0])))
                    .forEach((status, key) -> key.forEach(row -> {
                        String month = getMonthYearKey((Integer) row[0], year);
                        double value = ((Double) row[1]).intValue();
                        initMap.put(month, value);
                    }));
        }
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(initMap)
                        .build()
        );
    }

    @GetMapping("/parking-total")
    public ResponseEntity<?> getParkingTotal(@Param("year") Integer year) {
        ParkingTotalDto dashboard = this.parkingTicketRepository.totalParked(year);
        if (dashboard.getParkedCount() == null || dashboard.getNotParkedCount() == null) {
            dashboard.setParkedCount(0L);
            dashboard.setNotParkedCount(0L);
        }
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(dashboard)
                        .build()
        );
    }
}
