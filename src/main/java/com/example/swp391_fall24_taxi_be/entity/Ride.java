package com.example.swp391_fall24_taxi_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideId;

    private String rideCode;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate rideDate;
    private Integer capacity;
    private Double price;
    private String paymentMethod;
    private String status;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL)
    private List<RideLocation> rideLocations;

    @OneToMany(mappedBy = "ride")
    private List<UserRide> userRides;

    public String getOrganizerUsername() {
        if (userRides != null && !userRides.isEmpty()) {
            return userRides.get(0).getUser().getFullName();
        }
        return null;
    }

    public List<String> getParticipantUsernames() {
        if (userRides != null && !userRides.isEmpty()) {
            return userRides.stream()
                    .filter(userRide -> userRide.getRoleInRide().equals("PARTICIPANT"))
                    .map(userRide -> userRide.getUser().getFullName())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @OneToMany(mappedBy = "ride")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "ride")
    private List<Complaint> complaints;
}

