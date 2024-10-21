package com.example.swp391_fall24_taxi_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
