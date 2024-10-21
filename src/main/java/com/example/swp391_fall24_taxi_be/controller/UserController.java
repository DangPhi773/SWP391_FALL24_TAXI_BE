package com.example.swp391_fall24_taxi_be.controller;

import com.example.swp391_fall24_taxi_be.dto.request.ChangePasswordPayload;
import com.example.swp391_fall24_taxi_be.dto.request.UserLoginPayload;
import com.example.swp391_fall24_taxi_be.dto.request.UserRegisterPayload;
import com.example.swp391_fall24_taxi_be.dto.request.UserUpdatePayload;
import com.example.swp391_fall24_taxi_be.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taxi-server/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginPayload userLoginPayload) throws Exception {
        return userService.login(userLoginPayload);
    }

    @PostMapping("/registerNewUser")
    public  ResponseEntity<?> registerNewUser(@RequestBody UserRegisterPayload userRegisterPayload) {
        return userService.registerNewUser(userRegisterPayload);
    }

    @PostMapping("/registerNewStaff")
    public  ResponseEntity<?> registerNewStaff(@RequestBody UserRegisterPayload userRegisterPayload) {
        return userService.registerNewStaff(userRegisterPayload);
    }

    @PostMapping("/registerNewAdmin")
    public  ResponseEntity<?> registerNewAdmin(@RequestBody UserRegisterPayload userRegisterPayload) {
        return userService.registerNewAdmin(userRegisterPayload);
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable("id") int userId, @RequestBody ChangePasswordPayload changePasswordPayload) {
        return userService.changePassword(userId, changePasswordPayload);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int userId) throws Exception {
        return userService.getUserById(userId);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") int userId, @RequestBody UserUpdatePayload userUpdatePayload) {
        return userService.updateUser(userId, userUpdatePayload);
    }
}
