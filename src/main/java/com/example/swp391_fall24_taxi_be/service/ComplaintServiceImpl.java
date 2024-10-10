package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.response.ComplaintDTO;
import com.example.swp391_fall24_taxi_be.entity.Complaint;
import com.example.swp391_fall24_taxi_be.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintRepository complaintRepository;

    private static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");

    @Override
    public List<ComplaintDTO> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        return complaints.stream()
                .map(loc -> new ComplaintDTO(loc.getComplaintId(), loc.getDescription(), loc.getSubmittedDate(), loc.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplaintDTO> searchByDescription(String description) {
        List<Complaint> complaints = complaintRepository.findByDescriptionContaining(description);
        return complaints.stream()
                .map(loc -> new ComplaintDTO(loc.getComplaintId(), loc.getDescription(), loc.getSubmittedDate(), loc.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplaintDTO> searchBySubmittedDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<Complaint> complaints = complaintRepository.findBySubmittedDateBetween(startDate, endDate);
        return complaints.stream()
                .map(loc -> new ComplaintDTO(loc.getComplaintId(), loc.getDescription(), loc.getSubmittedDate(), loc.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplaintDTO> searchByStatus(String status) {
        List<Complaint> complaints = complaintRepository.findByStatusContaining(status);
        return complaints.stream()
                .map(loc -> new ComplaintDTO(loc.getComplaintId(), loc.getDescription(), loc.getSubmittedDate(), loc.getStatus()))
                .collect(Collectors.toList());
    }


    @Override
    public ComplaintDTO addComplaint(ComplaintDTO complaintDTO) {
        Complaint complaint = new Complaint();
        complaint.setDescription(complaintDTO.getDescription());
        complaint.setSubmittedDate(ZonedDateTime.now(VIETNAM_ZONE).toLocalDateTime());
        complaint.setStatus("Pending");
        complaintRepository.save(complaint);
        return new ComplaintDTO(complaint.getComplaintId(), complaint.getDescription(), complaint.getSubmittedDate(), complaint.getStatus());
    }

    @Override
    public ComplaintDTO updateComplaintByUser(Long id, ComplaintDTO complaintDTO) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setDescription(complaintDTO.getDescription());
        complaint.setSubmittedDate(ZonedDateTime.now(VIETNAM_ZONE).toLocalDateTime());
        complaint.setStatus("Pending");
        complaintRepository.save(complaint);
        return new ComplaintDTO(complaint.getComplaintId(), complaint.getDescription(), complaint.getSubmittedDate(), complaint.getStatus());
    }

    @Override
    public ComplaintDTO updateComplaintByStaff(Long id, ComplaintDTO complaintDTO) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new RuntimeException("Complaint not found"));
        if (complaintDTO.getDescription() != null && !complaintDTO.getDescription().trim().isEmpty()) {
            complaint.setDescription(complaintDTO.getDescription());
        }
        complaint.setSubmittedDate(ZonedDateTime.now(VIETNAM_ZONE).toLocalDateTime());
        complaint.setStatus(complaintDTO.getStatus());
        complaintRepository.save(complaint);
        return new ComplaintDTO(complaint.getComplaintId(), complaint.getDescription(), complaint.getSubmittedDate(), complaint.getStatus());
    }

    @Override
    public void deleteComplaint(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setStatus("Declined");
        complaintRepository.save(complaint);
    }
}
