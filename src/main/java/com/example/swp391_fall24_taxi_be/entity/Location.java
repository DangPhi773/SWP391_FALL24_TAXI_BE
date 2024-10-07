package com.example.swp391_fall24_taxi_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

