package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.request.UserRegisterPayload;
import com.example.swp391_fall24_taxi_be.dto.response.ApiResponse;
import com.example.swp391_fall24_taxi_be.entity.User;
import com.example.swp391_fall24_taxi_be.repository.UserRepository;
import com.example.swp391_fall24_taxi_be.util.EntityToDTOMapper;
import com.example.swp391_fall24_taxi_be.util.HelperUtil;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.example.swp391_fall24_taxi_be.util.Constants.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final HelperUtil helperUtil;

    @Override
    public ResponseEntity<?> login(UserRegisterPayload userRegisterPayload) {
        try {
            String errorMessage = validateUserCreatePayload(userRegisterPayload.getEmail(), userRegisterPayload.getPassword());
            if (errorMessage != null)
                return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage));

            String password = Base64.getEncoder().encodeToString(userRegisterPayload.getPassword().getBytes(StandardCharsets.UTF_8));
            User user = userRepository.findByEmailAndPassword(userRegisterPayload.getEmail(), password);
            if (user == null)
                return ResponseEntity.badRequest().body(new ApiResponse(false, LOGIN_ERROR));

            return ResponseEntity.ok().body(new ApiResponse(true, SUCCESS, entityToDTOMapper.mapUserEntityToDTO(user)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
    private String validateUserCreatePayload(String email, String password) {
        if (Strings.isNullOrEmpty(email))
            return EMAIL_NULL_OR_EMPTY_ERROR;
        if (Strings.isNullOrEmpty(password))
            return PASSWORD_NULL_OR_EMPTY_ERROR;
        if (!helperUtil.validateEmail(email))
            return INVALID_EMAIL;
        if (userRepository.existsByEmail(email) != null)
            return EXIST_EMAIL_ERROR;
        return null;
    }
}
