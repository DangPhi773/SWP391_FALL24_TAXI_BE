package com.example.swp391_fall24_taxi_be.repository;

import com.example.swp391_fall24_taxi_be.entity.RideLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideLocationRepository extends JpaRepository<RideLocation, Long> {
}