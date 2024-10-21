package com.example.swp391_fall24_taxi_be.controller;

import com.example.swp391_fall24_taxi_be.dto.request.LocationPayLoad;
import com.example.swp391_fall24_taxi_be.dto.response.LocationDTO;
import com.example.swp391_fall24_taxi_be.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taxi-server/api/v1/locations")
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
    public ResponseEntity<LocationDTO> addLocation(@RequestBody LocationPayLoad locationPayLoad) {
        LocationDTO createdLocation = locationService.addLocation(locationPayLoad);
        return ResponseEntity.ok(createdLocation);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable Long id, @RequestBody LocationPayLoad locationPayLoad) {
        LocationDTO updatedLocation = locationService.updateLocation(id, locationPayLoad);
        return ResponseEntity.ok(updatedLocation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
