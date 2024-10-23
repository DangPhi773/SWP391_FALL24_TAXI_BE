package com.example.swp391_fall24_taxi_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class RideResponse {
    private Long rideId;
    private String rideCode;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate rideDate;
    private Integer capacity;
    private Double price;
    private String status;
    private String paymentMethod;
    private String organizerUsername;
    private List<String> participantUsernames;
}
