package com.example.swp391_fall24_taxi_be.util;

import com.example.swp391_fall24_taxi_be.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class HelperUtil {
    private final UserRepository userRepository;
    public boolean validateEmail(String email) {
        //Regular Expression
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
