package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.request.RidePayload;
import com.example.swp391_fall24_taxi_be.dto.response.RideResponse;
import com.example.swp391_fall24_taxi_be.entity.*;
import com.example.swp391_fall24_taxi_be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideServiceImpl implements RideService {


    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRideRepository userRideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private RideLocationRepository rideLocationRepository;

    @Override
    public RideResponse createRide(RidePayload ridePayload) {
        if (!"CASH".equals(ridePayload.getPaymentMethod()) && !"ONLINE".equals(ridePayload.getPaymentMethod())) {
            throw new IllegalArgumentException("Invalid payment method. It must be either 'CASH' or 'ONLINE'.");
        }

        Ride ride = new Ride();
        ride.setRideCode(ridePayload.getRideCode());
        ride.setStartTime(ridePayload.getStartTime());
        ride.setEndTime(ridePayload.getEndTime());
        ride.setRideDate(ridePayload.getRideDate());
        ride.setCapacity(ridePayload.getCapacity());
        ride.setPrice(ridePayload.getPrice());
        ride.setStatus("PENDING");

        ride.setPaymentMethod(ridePayload.getPaymentMethod());

        ride = rideRepository.save(ride);

        Location startLocation = locationRepository.findById(ridePayload.getStartLocationId())
                .orElseThrow(() -> new RuntimeException("Start location not found"));
        Location endLocation = locationRepository.findById(ridePayload.getEndLocationId())
                .orElseThrow(() -> new RuntimeException("End location not found"));

        RideLocation startRideLocation = new RideLocation();
        startRideLocation.setRide(ride);
        startRideLocation.setLocation(startLocation);
        rideLocationRepository.save(startRideLocation);

        RideLocation endRideLocation = new RideLocation();
        endRideLocation.setRide(ride);
        endRideLocation.setLocation(endLocation);
        rideLocationRepository.save(endRideLocation);

        User user = userRepository.findById(ridePayload.getUserId().intValue())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserRide userRide = new UserRide();
        userRide.setJoinDate(LocalDate.now());
        userRide.setRoleInRide("ORGANIZER");
        userRide.setUser(user);
        userRide.setRide(ride);
        userRideRepository.save(userRide);

        return mapToRideResponse(ride);
    }


    @Override
    public void joinRide(Long rideId, Long userId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (!"ACTIVE".equals(ride.getStatus().toUpperCase())) {
            throw new RuntimeException("Ride is not active. Cannot join.");
        }

        List<UserRide> currentParticipants = userRideRepository.findByRide(ride);

        int currentParticipantCount = currentParticipants.size();
        if (currentParticipantCount >= ride.getCapacity()) {
            throw new RuntimeException("Ride is full. Cannot join.");
        }

        User user = userRepository.findById(userId.intValue())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserRide userRide = new UserRide();
        userRide.setJoinDate(LocalDate.now());
        userRide.setRoleInRide("PARTICIPANT");
        userRide.setUser(user);
        userRide.setRide(ride);

        userRideRepository.save(userRide);
    }

    private final List<String> VALID_STATUSES = Arrays.asList("ACTIVE", "INACTIVE", "RIDING", "COMPLETED", "PROBLEM");

    @Override
    public void updateRideStatus(Long rideId, String status, Long staffId) {
        if (!VALID_STATUSES.contains(status)) {
            throw new RuntimeException("Invalid status. Valid statuses are: " + VALID_STATUSES);
        }

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        User staff = userRepository.findById(staffId.intValue()) // ép kiểu nếu cần
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (!"STAFF".equalsIgnoreCase(staff.getRole())) {
            throw new RuntimeException("Only staff can update ride status.");
        }

        ride.setStatus(status);
        rideRepository.save(ride);
    }

    @Override
    public RideResponse updateRide(Long id, RidePayload ridePayload) {
        Ride ride = rideRepository.findById(id).orElseThrow(() -> new RuntimeException("Ride not found"));
        ride.setRideCode(ridePayload.getRideCode());
        ride.setStartTime(ridePayload.getStartTime());
        ride.setEndTime(ridePayload.getEndTime());
        ride.setRideDate(ridePayload.getRideDate());
        ride.setCapacity(ridePayload.getCapacity());
        ride.setPrice(ridePayload.getPrice());
        ride.setStatus(ridePayload.getStatus());
        ride.setPaymentMethod(ridePayload.getPaymentMethod());
        ride = rideRepository.save(ride);
        return mapToRideResponse(ride);
    }

    @Override
    public void deleteRide(Long id) {
        rideRepository.deleteById(id);
    }

    @Override
    public RideResponse getRideById(Long id) {
        Ride ride = rideRepository.findById(id).orElseThrow(() -> new RuntimeException("Ride not found"));
        return mapToRideResponse(ride);
    }

    @Override
    public List<RideResponse> getAllRides() {
        List<Ride> rides = rideRepository.findAll();
        return rides.stream().map(this::mapToRideResponse).collect(Collectors.toList());
    }

    @Override
    public List<RideResponse> getAllRidesByPendingStatus() {
        List<Ride> pendingRides = rideRepository.findByStatus("PENDING");
        return pendingRides.stream().map(this::mapToRideResponse).collect(Collectors.toList());
    }


    private RideResponse mapToRideResponse(Ride ride) {
        return new RideResponse(
                ride.getRideId(),
                ride.getRideCode(),
                ride.getStartTime(),
                ride.getEndTime(),
                ride.getRideDate(),
                ride.getCapacity(),
                ride.getPrice(),
                ride.getStatus(),
                ride.getPaymentMethod()
        );
    }
}
