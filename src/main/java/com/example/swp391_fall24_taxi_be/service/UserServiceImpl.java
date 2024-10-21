package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.request.*;
import com.example.swp391_fall24_taxi_be.dto.response.ApiResponse;
import com.example.swp391_fall24_taxi_be.dto.response.UserDTO;
import com.example.swp391_fall24_taxi_be.entity.User;
import com.example.swp391_fall24_taxi_be.entity.Wallet;
import com.example.swp391_fall24_taxi_be.repository.UserRepository;
import com.example.swp391_fall24_taxi_be.util.EntityToDTOMapper;
import com.example.swp391_fall24_taxi_be.util.HelperUtil;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.stream.Collectors;

import static com.example.swp391_fall24_taxi_be.util.Constants.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final HelperUtil helperUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(user -> new UserDTO(
                        user.getUserId(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole(),
                        user.getStatus()))
                .collect(Collectors.toList());

        if (userDTOs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found");
        }
        return ResponseEntity.ok(userDTOs);
    }

    @Override
    public ResponseEntity<?> getUserById(int userId) {
        try {
            if (userRepository.findById(userId).isEmpty())
                return ResponseEntity.ok(new ApiResponse(true, NOT_FOUND));
            User userEntity = userRepository.getOne(userId);
            UserDTO userDTO = entityToDTOMapper.mapUserEntityToDTO(userEntity);
            return ResponseEntity.ok(new ApiResponse(true, SUCCESS, userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(int userId) {
        try {
            if (userRepository.findById(userId).isPresent()) {
                User user = userRepository.getOne(userId);
                user.setStatus("DEACTIVE");
                userRepository.save(user);
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse(false, NOT_FOUND));
            }
            return ResponseEntity.ok(new ApiResponse(true, SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> login(UserLoginPayload userLoginPayload) {
        try {
            String errorMessage = validateLoginPayload(userLoginPayload.getEmail(), userLoginPayload.getPassword());
            if (errorMessage != null)
                return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage));

            // Assuming the password is hashed using BCrypt
            User user = userRepository.existsByEmail(userLoginPayload.getEmail());
            if (user == null || !passwordEncoder.matches(userLoginPayload.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Incorrect email or password, please try again"));
            }

            return ResponseEntity.ok().body(new ApiResponse(true, SUCCESS, entityToDTOMapper.mapUserEntityToDTO(user)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }


    private String validateLoginPayload(String email, String password) {
        if (Strings.isNullOrEmpty(email))
            return EMAIL_NULL_OR_EMPTY_ERROR;
        if (Strings.isNullOrEmpty(password))
            return PASSWORD_NULL_OR_EMPTY_ERROR;
        if (!helperUtil.validateEmail(email))
            return INVALID_EMAIL;
        return null;
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

    @Override
    public ResponseEntity<?> updateUser(Integer userId, UserUpdatePayload userUpdatePayload) {
        try {
            if (userRepository.findById(userId).isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "User not found"));
            }

            User user = userRepository.getOne(userId);
            user.setFullName(userUpdatePayload.getUserDTO().getFullName());
            user.setEmail(userUpdatePayload.getUserDTO().getEmail());
            user.setPassword(userUpdatePayload.getUserDTO().getPassword());
            user.setRole(userUpdatePayload.getUserDTO().getRole());
            user.setStatus(userUpdatePayload.getUserDTO().getStatus());

            userRepository.save(user);

            return ResponseEntity.ok(new ApiResponse(true, "User updated successfully", userUpdatePayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }


    @Override
    public ResponseEntity<?> registerNewUser(UserRegisterPayload userRegisterPayload) {
        try {
            String errorMessage = validateUserCreatePayload(userRegisterPayload.getEmail(), userRegisterPayload.getPassword());
            if (errorMessage != null) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage));
            }

            User userEntity = new User();
            userEntity.setEmail(userRegisterPayload.getEmail());
            userEntity.setFullName(userRegisterPayload.getFullName());
            String encodedPassword = passwordEncoder.encode(userRegisterPayload.getPassword());
            userEntity.setPassword(encodedPassword);

            userEntity.setRole("STUDENT");
            userEntity.setStatus("ACTIVE");

            Wallet wallet = new Wallet();
            wallet.setBalance(0.0);
            wallet.setUser(userEntity);
            userEntity.setWallet(wallet);

            userRepository.save(userEntity);

            return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully", userRegisterPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> registerNewAdmin(UserRegisterPayload userRegisterPayload) {
        try {
            String errorMessage = validateUserCreatePayload(userRegisterPayload.getEmail(), userRegisterPayload.getPassword());
            if (errorMessage != null) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage));
            }

            User userEntity = new User();
            userEntity.setEmail(userRegisterPayload.getEmail());
            userEntity.setFullName(userRegisterPayload.getFullName());
            String encodedPassword = passwordEncoder.encode(userRegisterPayload.getPassword());
            userEntity.setPassword(encodedPassword);

            userEntity.setRole("ADMIN");
            userEntity.setStatus("ACTIVE");

            Wallet wallet = new Wallet();
            wallet.setBalance(0.0);
            wallet.setUser(userEntity);
            userEntity.setWallet(wallet);

            userRepository.save(userEntity);

            return ResponseEntity.ok().body(new ApiResponse(true, "Admin registered successfully", userRegisterPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> registerNewStaff(UserRegisterPayload userRegisterPayload) {
        try {
            String errorMessage = validateUserCreatePayload(userRegisterPayload.getEmail(), userRegisterPayload.getPassword());
            if (errorMessage != null) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage));
            }

            User userEntity = new User();
            userEntity.setEmail(userRegisterPayload.getEmail());
            userEntity.setFullName(userRegisterPayload.getFullName());
            String encodedPassword = passwordEncoder.encode(userRegisterPayload.getPassword());
            userEntity.setPassword(encodedPassword);

            userEntity.setRole("STAFF");
            userEntity.setStatus("ACTIVE");

            Wallet wallet = new Wallet();
            wallet.setBalance(0.0);
            wallet.setUser(userEntity);
            userEntity.setWallet(wallet);

            userRepository.save(userEntity);

            return ResponseEntity.ok().body(new ApiResponse(true, "Staff registered successfully", userRegisterPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> changePassword(int userId, ChangePasswordPayload changePasswordPayload) {
        try {
            if (userRepository.findById(userId).isPresent()) {
                User userEntity = userRepository.getOne(userId);

                if (passwordEncoder.matches(changePasswordPayload.getCurrentPassword(),userEntity.getPassword())) {
                    String newPassword = passwordEncoder.encode(changePasswordPayload.getNewPassword());
                    userEntity.setPassword(newPassword);
                    userRepository.save(userEntity);
                } else {
                    return ResponseEntity.badRequest().body(new ApiResponse(false, INCORRECT_PASSWORD_ERROR));
                }
            }
            return ResponseEntity.ok(new ApiResponse(true, SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<?> forgotPassword(String email) {
        try {
            if (userRepository.existsByEmail(email) == null)
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Email is not exist !!!"));
            User user = userRepository.existsByEmail(email);
            String randomPassword = RandomStringUtils.randomAlphanumeric(10);
            user.setPassword(passwordEncoder.encode(randomPassword));
            userRepository.save(user);
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(email);
            emailDetails.setSubject("FORGOT_PASSWORD");
            String message = " Dear " + user.getFullName() + ", \n" + " Your new password is : " + randomPassword;
            emailDetails.setMsgBody(message);
            String result =  emailService.sendSimpleMail(emailDetails);
            return ResponseEntity.ok(new ApiResponse(true,result));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

}
