package com.example.swp391_fall24_taxi_be.service;
import com.example.swp391_fall24_taxi_be.dto.response.ComplaintDTO;
import com.example.swp391_fall24_taxi_be.entity.Complaint;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ComplaintService {
    List<ComplaintDTO> getAllComplaints();
    List<ComplaintDTO> searchByDescription(String description);
//    List<ComplaintDTO> searchBySubmittedDate(LocalDateTime submittedDate);
    List<ComplaintDTO> searchByStatus(String status);
    ComplaintDTO addComplaint(ComplaintDTO complaintDTO);
    ComplaintDTO updateComplaint(Long id, ComplaintDTO ComplaintDTO);
    void deleteComplaint(Long id);
}
