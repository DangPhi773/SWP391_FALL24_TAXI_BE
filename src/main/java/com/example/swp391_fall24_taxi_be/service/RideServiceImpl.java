package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.request.RidePayload;
import com.example.swp391_fall24_taxi_be.dto.response.RideResponse;
import com.example.swp391_fall24_taxi_be.entity.*;
import com.example.swp391_fall24_taxi_be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    private final List<String> VALID_STATUSES =
            Arrays.asList("ACTIVE", "INACTIVE", "RIDING", "COMPLETED", "PROBLEM");

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
    public List<RidePayload> getAllRides() {
        List<Ride> rides = rideRepository.findAll();
        List<RidePayload> ridePayloads = new ArrayList<>();

        for (Ride ride : rides) {
            int currentParticipants = userRideRepository.findByRide(ride).size();
            int availableSeats = ride.getCapacity() - currentParticipants;

            RidePayload payload = new RidePayload();
            payload.setRideId(ride.getRideId()); // Assuming this is a Long
            payload.setRideCode(String.valueOf(ride.getRideCode())); // Convert Long to String
            payload.setStartTime(ride.getStartTime());
            payload.setEndTime(ride.getEndTime());
            payload.setRideDate(ride.getRideDate());
            payload.setCapacity(ride.getCapacity());
            payload.setPrice(ride.getPrice());
            payload.setStatus(ride.getStatus());
            payload.setPaymentMethod(ride.getPaymentMethod());
            payload.setAvailableSeats(availableSeats);

            // Add startLocationName and endLocationName by retrieving the associated RideLocation entries
            List<RideLocation> rideLocations = rideLocationRepository.findByRide(ride);
            if (!rideLocations.isEmpty()) {
                // Assuming the first RideLocation is the start location and the second is the end location
                payload.setStartLocationName(rideLocations.get(0).getLocation().getLocationName());
                if (rideLocations.size() > 1) {
                    payload.setEndLocationName(rideLocations.get(1).getLocation().getLocationName());
                }
            }
            if (!rideLocations.isEmpty()) {
                // Assuming the first RideLocation is the start location and the second is the end location
                payload.setStartLocationId(rideLocations.get(0).getLocation().getLocationId());
                if (rideLocations.size() > 1) {
                    payload.setEndLocationId(rideLocations.get(1).getLocation().getLocationId());
                }
            }
            // Add userId by fetching the UserRide with the role 'ORGANIZER'
            UserRide organizerRide = userRideRepository.findByRideAndRoleInRide(ride, "ORGANIZER");
            if (organizerRide != null) {
                payload.setUserId(organizerRide.getUser().getUserId());
            }

            ridePayloads.add(payload);
        }

        return ridePayloads;
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
