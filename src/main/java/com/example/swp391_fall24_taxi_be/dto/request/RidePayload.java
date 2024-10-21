package com.example.swp391_fall24_taxi_be.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RidePayload {
    private String rideCode;
    private Long startLocationId;
    private Long endLocationId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate rideDate;
    private Integer capacity;
    private Double price;
    private String status;
    private Long userId;
}
