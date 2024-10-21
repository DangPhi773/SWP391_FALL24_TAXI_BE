package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.request.LocationPayLoad;
import com.example.swp391_fall24_taxi_be.dto.response.LocationDTO;

import java.util.List;

public interface LocationService {
    List<LocationDTO> getAllLocations();
    List<LocationDTO> searchByName(String name);
    LocationDTO addLocation(LocationPayLoad locationPayLoad);
    LocationDTO updateLocation(Long id, LocationPayLoad locationPayLoad);
    void deleteLocation(Long id);
}
