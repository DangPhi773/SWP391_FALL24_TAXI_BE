package com.example.swp391_fall24_taxi_be.entity;

import jakarta.persistence.*;


@Entity
public class RideLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideLocationId;

    @ManyToOne
    @JoinColumn(name = "rideId")
    private Ride ride;

    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location location;

    // Getters and Setters
}
