package com.example.swp391_fall24_taxi_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

