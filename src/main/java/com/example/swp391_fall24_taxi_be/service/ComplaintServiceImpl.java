package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.response.ComplaintDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.List;


import com.example.swp391_fall24_taxi_be.dto.response.LocationDTO;
import com.example.swp391_fall24_taxi_be.entity.Complaint;
import com.example.swp391_fall24_taxi_be.entity.Location;
import com.example.swp391_fall24_taxi_be.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintServiceImpl implements ComplaintService{
    @Autowired
    private ComplaintRepository complaintRepository;

    @Override
    public List<ComplaintDTO> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        return complaints.stream()
                .map(loc -> new ComplaintDTO(loc.getComplaintId(),loc.getDescription(),loc.getSubmittedDate(),loc.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplaintDTO> searchByDescription(String description) {
        List<Complaint> complaints = complaintRepository.findByDescriptionContaining(description);
        return complaints.stream()
                .map(loc -> new ComplaintDTO(loc.getComplaintId(), loc.getDescription(), loc.getSubmittedDate(),loc.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplaintDTO> searchBySubmittedDate(LocalDateTime submittedDate) {
        LocalDateTime startOfDay = submittedDate.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = submittedDate.toLocalDate().atTime(LocalTime.MAX);
        List<Complaint> complaints = complaintRepository.findBySubmittedDateBetween(startOfDay, endOfDay);
        return complaints.stream()
                .map(loc -> new ComplaintDTO(loc.getComplaintId(), loc.getDescription(), loc.getSubmittedDate(),loc.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplaintDTO> searchByStatus(String status) {
        List<Complaint> complaints = complaintRepository.findByStatusContaining(status);
        return complaints.stream()
                .map(loc -> new ComplaintDTO(loc.getComplaintId(), loc.getDescription(), loc.getSubmittedDate(),loc.getStatus()))
                .collect(Collectors.toList());
    }


    @Override
    public ComplaintDTO addComplaint(ComplaintDTO complaintDTO) {
        Complaint complaint = new Complaint();
        complaint.setDescription(complaintDTO.getDescription());
        complaint.setSubmittedDate(complaintDTO.getSubmittedDate());
        complaint.setStatus(complaintDTO.getStatus());
        complaintRepository.save(complaint);
        return new ComplaintDTO(complaint.getComplaintId(),complaint.getDescription(),complaint.getSubmittedDate(),complaint.getStatus());
    }

    @Override
    public ComplaintDTO updateComplaint(Long id, ComplaintDTO complaintDTO) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setDescription(complaintDTO.getDescription());
        complaint.setSubmittedDate(complaintDTO.getSubmittedDate());
        complaint.setStatus(complaintDTO.getStatus());
        complaintRepository.save(complaint);
        return new ComplaintDTO(complaint.getComplaintId(),complaint.getDescription(),complaint.getSubmittedDate(),complaint.getStatus());
    }

    @Override
    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }
}
