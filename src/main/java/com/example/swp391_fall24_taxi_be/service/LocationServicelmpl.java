package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.response.LocationDTO;

import java.util.List;
import java.util.stream.Collectors;

import com.example.swp391_fall24_taxi_be.repository.LocationRepository;
import com.example.swp391_fall24_taxi_be.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServicelmpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<LocationDTO> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(loc -> new LocationDTO(loc.getLocationId(), loc.getLocationName(), loc.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationDTO> searchByName(String name) {
        List<Location> locations = locationRepository.findByLocationNameContaining(name);
        return locations.stream()
                .map(loc -> new LocationDTO(loc.getLocationId(), loc.getLocationName(), loc.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public LocationDTO addLocation(LocationDTO locationDTO) {
        Location location = new Location();
        location.setLocationName(locationDTO.getLocationName());
        location.setDescription(locationDTO.getDescription());
        location = locationRepository.save(location);
        return new LocationDTO(location.getLocationId(), location.getLocationName(), location.getDescription());
    }

    @Override
    public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new RuntimeException("Location not found"));
        location.setLocationName(locationDTO.getLocationName());
        location.setDescription(locationDTO.getDescription());
        location = locationRepository.save(location);
        return new LocationDTO(location.getLocationId(), location.getLocationName(), location.getDescription());
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
