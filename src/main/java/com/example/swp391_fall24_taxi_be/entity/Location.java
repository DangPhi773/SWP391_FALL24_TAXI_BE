package com.example.swp391_fall24_taxi_be.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    private String locationName;
    private String description;

    @OneToMany(mappedBy = "location")
    private List<RideLocation> rideLocations;

    // Getters and Setters
}

