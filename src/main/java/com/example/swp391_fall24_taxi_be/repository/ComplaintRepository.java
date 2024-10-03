package com.example.swp391_fall24_taxi_be.repository;

import com.example.swp391_fall24_taxi_be.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByDescriptionContaining(String description);
//    List<Complaint> findBySubmittedDateContaining(LocalDateTime submittedDate);
    List<Complaint> findByStatusContaining(String status);
}
