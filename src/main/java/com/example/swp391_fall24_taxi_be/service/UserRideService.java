package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.request.RidePayload;
import com.example.swp391_fall24_taxi_be.entity.Ride;

import java.util.List;

public interface UserRideService {
    List<Ride> getRidesByUserId(Long userId);
}
