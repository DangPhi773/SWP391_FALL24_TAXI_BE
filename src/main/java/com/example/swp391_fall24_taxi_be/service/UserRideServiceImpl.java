package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.entity.Ride;
import com.example.swp391_fall24_taxi_be.repository.UserRideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRideServiceImpl implements UserRideService {

    @Autowired
    private UserRideRepository userRideRepository;

    @Override
    public List<Ride> getRidesByUserId(Long userId) {
        return userRideRepository.findRidesByUserId(userId);
    }

}
