package com.example.swp391_fall24_taxi_be.dto.request;

import com.example.swp391_fall24_taxi_be.dto.response.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePayload {
    private UserDTO userDTO;
}
