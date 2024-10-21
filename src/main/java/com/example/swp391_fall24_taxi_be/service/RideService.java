package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.request.RidePayload;
import com.example.swp391_fall24_taxi_be.dto.response.RideResponse;

import java.util.List;

public interface RideService {
    RideResponse createRide(RidePayload ridePayload);
    RideResponse updateRide(Long id, RidePayload ridePayload);
    void deleteRide(Long id);
    RideResponse getRideById(Long id);
    List<RideResponse> getAllRides();
    void joinRide(Long rideId, Long userId);
    void updateRideStatus(Long rideId, String status, Long staffId);
}
