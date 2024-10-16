package com.example.swp391_fall24_taxi_be.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideId;

    private String rideCode;
    private String startLocation;
    private String endLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate rideDate;
    private Double price;
    private String status;

    @OneToMany(mappedBy = "ride")
    private List<UserRide> userRides;

    @OneToMany(mappedBy = "ride")
    private List<RideLocation> rideLocations;

    @OneToMany(mappedBy = "ride")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "ride")
    private List<Complaint> complaints;

    // Getters and Setters
}

