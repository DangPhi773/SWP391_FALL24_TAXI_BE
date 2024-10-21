package com.example.swp391_fall24_taxi_be.util;

import com.example.swp391_fall24_taxi_be.dto.response.LocationDTO;
import com.example.swp391_fall24_taxi_be.dto.response.UserDTO;
import com.example.swp391_fall24_taxi_be.entity.Location;
import com.example.swp391_fall24_taxi_be.entity.User;
import org.springframework.stereotype.Service;

@Service
public class EntityToDTOMapper {
    public UserDTO mapUserEntityToDTO(User userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setRole(userEntity.getRole());
        userDTO.setStatus(userEntity.getStatus());
        return userDTO;
    }
    public LocationDTO mapLocationEntityToDTO(Location locationEntity) {
        LocationDTO locationDTO = new LocationDTO();

        locationDTO.setLocationId(locationEntity.getLocationId());
        locationDTO.setLocationName(locationEntity.getLocationName());
        locationDTO.setDescription(locationEntity.getDescription());
        return locationDTO;
    }
}
