package com.example.swp391_fall24_taxi_be.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class UserRide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRideId;

    private LocalDate joinDate;
    private String roleInRide;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "rideId")
    private Ride ride;

    // Getters and Setters
}

