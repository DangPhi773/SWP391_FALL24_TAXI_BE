package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.request.ChangePasswordPayload;
import com.example.swp391_fall24_taxi_be.dto.request.UserLoginPayload;
import com.example.swp391_fall24_taxi_be.dto.request.UserRegisterPayload;
import com.example.swp391_fall24_taxi_be.dto.request.UserUpdatePayload;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> getAllUsers();

    ResponseEntity<?> registerNewUser(UserRegisterPayload userRegisterPayload);

    ResponseEntity<?> changePassword(int userId, ChangePasswordPayload changePasswordPayload);

    ResponseEntity<?> login(UserLoginPayload userLoginPayload) throws Exception;

    ResponseEntity<?> getUserById(int userId) throws Exception;

    ResponseEntity<?> updateUser(Integer userId, UserUpdatePayload userUpdatePayload);

//    ResponseEntity<?> forgotPassword(String email);

    ResponseEntity<?> deleteUser(int userId);


}
