package com.example.swp391_fall24_taxi_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginPayload {
    private String password;
    private String email;
}
