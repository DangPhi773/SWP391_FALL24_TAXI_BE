package com.example.swp391_fall24_taxi_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.example.swp391_fall24_taxi_be.util.Constants.TOKEN_TYPE_BEARER;

@Data
@NoArgsConstructor
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String role;
    private String username;

    public JwtAuthenticationResponse(String accessToken, String role, String username) {
        this.accessToken = accessToken;
        this.role = role;
        this.username = username;
    }
}
