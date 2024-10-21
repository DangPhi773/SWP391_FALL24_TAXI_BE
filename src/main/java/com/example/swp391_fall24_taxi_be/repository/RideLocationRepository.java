package com.example.swp391_fall24_taxi_be.repository;

import com.example.swp391_fall24_taxi_be.entity.Ride;
import com.example.swp391_fall24_taxi_be.entity.RideLocation;
import com.example.swp391_fall24_taxi_be.entity.UserRide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideLocationRepository extends JpaRepository<RideLocation, Long> {
    List<RideLocation> findByRide(Ride ride);
}