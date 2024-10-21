package com.example.swp391_fall24_taxi_be.repository;

import com.example.swp391_fall24_taxi_be.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByStatus(String status);
}
