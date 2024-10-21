package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.request.ComplaintPayLoad;
import com.example.swp391_fall24_taxi_be.dto.response.ComplaintDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ComplaintService {
    //Get All Complaint (Status "Active")
    List<ComplaintDTO> getAllComplaints();

    List<ComplaintDTO> getAllComplaintsByStaff();

    // Search display all status
    List<ComplaintDTO> searchByDescription(String description);

    List<ComplaintDTO> searchByStatus(String status);

    List<ComplaintDTO> searchBySubmittedDate(LocalDateTime startDate, LocalDateTime endDate);

    // Create New Complaint
    ComplaintDTO addComplaint(ComplaintPayLoad complaintPayLoad);

    // Update Complaint By User
    ComplaintDTO updateComplaintByUser(Long id, ComplaintPayLoad complaintPayLoad);

    // Update Complaint By Staff
    ComplaintDTO updateComplaintByStaff(Long userId, Long complaintId, String status);

    // Delete Complaint (Update Status to "Deactivated")
    void deleteComplaint(Long id);

}
