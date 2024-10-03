package com.example.swp391_fall24_taxi_be.controller;

import com.example.swp391_fall24_taxi_be.dto.response.ComplaintDTO;
import com.example.swp391_fall24_taxi_be.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints() {
        List<ComplaintDTO> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }

    @GetMapping("/search/description")
    public ResponseEntity<List<ComplaintDTO>> searchByDescription(@RequestParam String description) {
        List<ComplaintDTO> complaints = complaintService.searchByDescription(description);
        return ResponseEntity.ok(complaints);
    }

    @GetMapping("/search/submittedDate")
    public ResponseEntity<List<ComplaintDTO>> searchBySubmittedDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime submittedDate) {
        List<ComplaintDTO> complaints = complaintService.searchBySubmittedDate(submittedDate);
        return ResponseEntity.ok(complaints);
    }

    @GetMapping("/search/status")
    public ResponseEntity<List<ComplaintDTO>> searchByStatus(@RequestParam String status) {
        List<ComplaintDTO> complaints = complaintService.searchByStatus(status);
        return ResponseEntity.ok(complaints);
    }

    @PostMapping("/add")
    public ResponseEntity<ComplaintDTO> addComplaint(@RequestBody ComplaintDTO complaintDTO) {
        ComplaintDTO newComplaint = complaintService.addComplaint(complaintDTO);
        return ResponseEntity.ok(newComplaint);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ComplaintDTO> updateComplaint(@PathVariable Long id, @RequestBody ComplaintDTO complaintDTO) {
        ComplaintDTO updatedComplaint = complaintService.updateComplaint(id, complaintDTO);
        return ResponseEntity.ok(updatedComplaint);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.noContent().build();
    }
}
