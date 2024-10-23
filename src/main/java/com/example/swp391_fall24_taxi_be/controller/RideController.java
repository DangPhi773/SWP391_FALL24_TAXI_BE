package com.example.swp391_fall24_taxi_be.controller;

import com.example.swp391_fall24_taxi_be.dto.request.RidePayload;
import com.example.swp391_fall24_taxi_be.dto.response.RideResponse;
import com.example.swp391_fall24_taxi_be.entity.Ride;
import com.example.swp391_fall24_taxi_be.service.RideService;
import com.example.swp391_fall24_taxi_be.service.UserRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/taxi-server/api/v1/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    @Autowired
    private UserRideService userRideService;

    @PostMapping("/add")
    public ResponseEntity<RideResponse> createRide(@RequestBody RidePayload ridePayload) {
        return ResponseEntity.ok(rideService.createRide(ridePayload));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RideResponse> updateRide(@PathVariable Long id, @RequestBody RidePayload ridePayload) {
        return ResponseEntity.ok(rideService.updateRide(id, ridePayload));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getRideById/{id}")
    public ResponseEntity<RideResponse> getRideById(@PathVariable Long id) {
        return ResponseEntity.ok(rideService.getRideById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<RidePayload>> getAllRides() {
        return ResponseEntity.ok(rideService.getAllRides());
    }

    @GetMapping("/getAllRidePendingStatus")
    public ResponseEntity<List<RideResponse>> getAllRidesByPendingStatus() {
        return ResponseEntity.ok(rideService.getAllRidesByPendingStatus());
    }

    @PostMapping("/{rideId}/join")
    public ResponseEntity<String> joinRide(
            @PathVariable Long rideId,
            @RequestParam Long userId) {
        rideService.joinRide(rideId, userId);
        return ResponseEntity.ok("User has successfully joined the ride.");
    }

    @PutMapping("/{rideId}/status")
    public ResponseEntity<String> updateRideStatus(
            @PathVariable Long rideId,
            @RequestParam String status,
            @RequestParam Long staffId) {
        try {
            rideService.updateRideStatus(rideId, status.toUpperCase(), staffId);
            return ResponseEntity.ok("Ride status updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RideResponse>> getRidesByUserId(@PathVariable Long userId) {
        List<Ride> rides = userRideService.getRidesByUserId(userId);

        if (rides.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<RideResponse> rideResponses = rides.stream()
                .map(ride -> {
                    String organizerUsername = ride.getUserRides().stream()
                            .filter(userRide -> userRide.getRoleInRide().equals("ORGANIZER"))
                            .map(userRide -> userRide.getUser().getFullName())
                            .findFirst()
                            .orElse("No Organizer");

                    List<String> participantUsernames = ride.getUserRides().stream()
                            .filter(userRide -> userRide.getRoleInRide().equals("PARTICIPANT"))
                            .map(userRide -> userRide.getUser().getFullName())
                            .collect(Collectors.toList());

                    return new RideResponse(
                            ride.getRideId(),
                            ride.getRideCode(),
                            ride.getStartTime(),
                            ride.getEndTime(),
                            ride.getRideDate(),
                            ride.getCapacity(),
                            ride.getPrice(),
                            ride.getStatus(),
                            ride.getPaymentMethod(),
                            organizerUsername,
                            participantUsernames
                    );
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(rideResponses, HttpStatus.OK);
    }




}
