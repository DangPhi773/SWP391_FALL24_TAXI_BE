package com.example.swp391_fall24_taxi_be.controller;

import com.example.swp391_fall24_taxi_be.dto.response.LocationDTO;
import com.example.swp391_fall24_taxi_be.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("/getAll")
    public List<LocationDTO> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/search")
    public List<LocationDTO> searchByName(@RequestParam String name) {
        return locationService.searchByName(name);
    }

    @PostMapping("/add")
    public ResponseEntity<LocationDTO> addLocation(@RequestBody LocationDTO locationDTO) {
        LocationDTO createdLocation = locationService.addLocation(locationDTO);
        return ResponseEntity.ok(createdLocation);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable Long id, @RequestBody LocationDTO locationDTO) {
        LocationDTO updatedLocation = locationService.updateLocation(id, locationDTO);
        return ResponseEntity.ok(updatedLocation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
