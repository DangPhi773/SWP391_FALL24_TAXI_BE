package com.example.swp391_fall24_taxi_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.swp391_fall24_taxi_be.util.Constants.TOKEN_TYPE_BEARER;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = TOKEN_TYPE_BEARER;
    private long userId;
    private String username;
    private String role;

    public JwtAuthenticationResponse(String accessToken, long userId, String username, String role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.username = username;
        this.role = role;
    }
}
