package com.example.swp391_fall24_taxi_be.controller;

import com.example.swp391_fall24_taxi_be.dto.UserPrincipal;
import com.example.swp391_fall24_taxi_be.dto.request.LoginRequest;
import com.example.swp391_fall24_taxi_be.dto.request.UserRegisterPayload;
import com.example.swp391_fall24_taxi_be.dto.response.ApiResponse;
import com.example.swp391_fall24_taxi_be.dto.response.JwtAuthenticationResponse;
import com.example.swp391_fall24_taxi_be.jwts.JwtTokenProvider;
import com.example.swp391_fall24_taxi_be.repository.UserRepository;
import com.example.swp391_fall24_taxi_be.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.swp391_fall24_taxi_be.util.Constants.LOGIN_ERROR;
import static com.example.swp391_fall24_taxi_be.util.Constants.STATUS_FALSE;

@RestController
@RequestMapping("/taxi-server/api/v1/auth")
public class AuthController {
    final
    AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    final UserService userService;
    final
    UserRepository userRepository;
    final
    PasswordEncoder passwordEncoder;
    final
    JwtTokenProvider tokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            //set authentication to security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // generate token
            String jwt = tokenProvider.generateToken(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            List<String> roles = userPrincipal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt,
                    userPrincipal.getId(),
                    userPrincipal.getUsername()
                    , roles.get(0)));
        } catch (Exception e) {
            logger.error(LOGIN_ERROR);
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, LOGIN_ERROR));
        }
    }

    @PostMapping("/registerNewUser")
    public  ResponseEntity<?> registerNewUser(@RequestBody UserRegisterPayload userRegisterPayload) {
        return userService.registerNewUser(userRegisterPayload);
    }
//    @GetMapping("/forgotPassword/{email}")
//    public ResponseEntity<?> forgotPassword(@PathVariable("email") String email)  {
//        return userService.forgotPassword(email);
//    }
}
