package com.example.swp391_fall24_taxi_be.controller;

import com.example.swp391_fall24_taxi_be.dto.request.RidePayload;
import com.example.swp391_fall24_taxi_be.dto.response.RideResponse;
import com.example.swp391_fall24_taxi_be.entity.Ride;
import com.example.swp391_fall24_taxi_be.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/taxi-server/api/v1/rides")
public class RideController {

    @Autowired
    private RideService rideService;

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
}
