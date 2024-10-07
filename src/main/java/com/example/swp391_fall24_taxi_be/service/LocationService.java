package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.response.LocationDTO;

import java.util.List;

public interface LocationService {
    List<LocationDTO> getAllLocations();
    List<LocationDTO> searchByName(String name);
    LocationDTO addLocation(LocationDTO locationDTO);
    LocationDTO updateLocation(Long id, LocationDTO locationDTO);
    void deleteLocation(Long id);
}
