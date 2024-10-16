package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.response.ComplaintDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ComplaintService {
    //Get All Complaint (Status "Active")
    List<ComplaintDTO> getAllComplaints();

    //Search display all status
    List<ComplaintDTO> searchByDescription(String description);

    List<ComplaintDTO> searchBySubmittedDate(LocalDateTime startDate, LocalDateTime endDate);

    List<ComplaintDTO> searchByStatus(String status);

    //Create New Complaint By User (status: "pending")
    ComplaintDTO addComplaint(ComplaintDTO complaintDTO);

    //Update Complaint By User (status: "pending")
    ComplaintDTO updateComplaintByUser(Long id, ComplaintDTO complaintDTO);

    //Update Complaint By Staff (status: depends on the Staff)
    ComplaintDTO updateComplaintByStaff(Long id, ComplaintDTO complaintDTO);

    //Delete Such As Update Status To "Declined"
    void deleteComplaint(Long id);
}
