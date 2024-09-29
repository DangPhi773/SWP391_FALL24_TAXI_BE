package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.request.UserRegisterPayload;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> login(UserRegisterPayload userRegisterPayload) throws Exception;
}
