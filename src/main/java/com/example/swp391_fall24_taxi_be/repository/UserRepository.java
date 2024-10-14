package com.example.swp391_fall24_taxi_be.repository;

import com.example.swp391_fall24_taxi_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT u from User u where u.email=:email and u.password=:password and u.status = 'active'")
    User findByEmailAndPassword(String email, String password);
    @Query(value = "SELECT u from User u where u.email=:email and u.status = 'active'")
    User existsByEmail(String email);
}
