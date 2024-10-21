package com.example.swp391_fall24_taxi_be.controller;

import com.example.swp391_fall24_taxi_be.dto.request.ComplaintPayLoad;
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
@RequestMapping("/taxi-server/api/v1/complaints")
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints() {
        List<ComplaintDTO> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }

    @GetMapping("/getAllByStaff")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaintsByStaff() {
        List<ComplaintDTO> complaints = complaintService.getAllComplaintsByStaff();
        return ResponseEntity.ok(complaints);
    }

    @GetMapping("/search/description")
    public ResponseEntity<List<ComplaintDTO>> searchByDescription(@RequestParam String description) {
        List<ComplaintDTO> complaints = complaintService.searchByDescription(description);
        return ResponseEntity.ok(complaints);
    }

    @GetMapping("/searchByStatus")
    public ResponseEntity<List<ComplaintDTO>> searchByStatus(@RequestParam String status) {
        List<ComplaintDTO> complaints = complaintService.searchByStatus(status);
        return ResponseEntity.ok(complaints);
    }

    @PostMapping("/add")
    public ResponseEntity<ComplaintDTO> addComplaint(@RequestBody ComplaintPayLoad complaintPayLoad) {
        ComplaintDTO newComplaint = complaintService.addComplaint(complaintPayLoad);
        return ResponseEntity.ok(newComplaint);
    }

    @PutMapping("/updateByUser/{id}")
    public ResponseEntity<ComplaintDTO> updateComplaintByUser(@PathVariable Long id, @RequestBody ComplaintPayLoad complaintPayLoad) {
        ComplaintDTO updatedComplaint = complaintService.updateComplaintByUser(id, complaintPayLoad);
        return ResponseEntity.ok(updatedComplaint);
    }

    @PutMapping("/updateByStaff/{id}")
    public ResponseEntity<ComplaintDTO> updateComplaintByStaff(
            @RequestParam Long userId,
            @RequestParam Long complaintId,
            @RequestParam String status) {

        // Gọi service để thực hiện cập nhật
        ComplaintDTO updatedComplaint = complaintService.updateComplaintByStaff(userId, complaintId, status);

        return ResponseEntity.ok(updatedComplaint);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.noContent().build();
    }
}
