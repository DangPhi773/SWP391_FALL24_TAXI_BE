package com.example.swp391_fall24_taxi_be.repository;

import com.example.swp391_fall24_taxi_be.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    //Find By Description
    List<Complaint> findByDescriptionContaining(String description);

    //Find By SubmittedDate in day
    List<Complaint> findBySubmittedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    //Find By Status (all status)
    List<Complaint> findByStatusContaining(String status);

    //Find By Status (status active "only"
    List<Complaint> findByStatus(String status);
}
