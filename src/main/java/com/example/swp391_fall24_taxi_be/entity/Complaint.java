package com.example.swp391_fall24_taxi_be.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complaintId;

    private String description;
    private LocalDateTime submittedDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "rideId")
    private Ride ride;

    // Getters and Setters
}
