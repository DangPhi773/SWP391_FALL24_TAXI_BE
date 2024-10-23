package com.example.swp391_fall24_taxi_be.repository;

import com.example.swp391_fall24_taxi_be.dto.request.RidePayload;
import com.example.swp391_fall24_taxi_be.entity.Ride;
import com.example.swp391_fall24_taxi_be.entity.UserRide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRideRepository extends JpaRepository<UserRide, Long> {
    List<UserRide> findByRide(Ride ride);
    UserRide findByRideAndRoleInRide(Ride ride, String roleInRide);
    @Query("SELECT ur.ride FROM UserRide ur WHERE ur.user.userId = :userId")
    List<Ride> findRidesByUserId(@Param("userId") Long userId);
}
