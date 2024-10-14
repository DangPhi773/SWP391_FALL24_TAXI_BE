package com.example.swp391_fall24_taxi_be.jwts;

import com.example.swp391_fall24_taxi_be.dto.UserPrincipal;
import com.example.swp391_fall24_taxi_be.entity.User;
import com.example.swp391_fall24_taxi_be.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    final
    UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userRepository.existsByEmail(email);
            return UserPrincipal.create(user);
        }catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException("user not found with username or email: " + email);
        }
    }
    //this method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("user not found with Id: " + userId));
        return UserPrincipal.create(user);
    }
}
