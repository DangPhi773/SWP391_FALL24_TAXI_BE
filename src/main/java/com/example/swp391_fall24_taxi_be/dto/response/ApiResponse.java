package com.example.swp391_fall24_taxi_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private Boolean status;
    private String message;
    private Object data;

    public ApiResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
