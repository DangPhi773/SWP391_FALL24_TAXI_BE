package com.example.swp391_fall24_taxi_be.service;

import com.example.swp391_fall24_taxi_be.dto.request.ComplaintPayLoad;
import com.example.swp391_fall24_taxi_be.dto.response.ComplaintDTO;
import com.example.swp391_fall24_taxi_be.entity.Complaint;
import com.example.swp391_fall24_taxi_be.entity.Ride;
import com.example.swp391_fall24_taxi_be.entity.User;
import com.example.swp391_fall24_taxi_be.repository.ComplaintRepository;
import com.example.swp391_fall24_taxi_be.repository.RideRepository;
import com.example.swp391_fall24_taxi_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RideRepository rideRepository;

    private static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");

    @Override
    public List<ComplaintDTO> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        return complaints.stream()
                .map(complaint -> new ComplaintDTO(
                        complaint.getComplaintId(),
                        complaint.getDescription(),
                        complaint.getSubmittedDate(),
                        complaint.getRating(),
                        complaint.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplaintDTO> getAllComplaintsByStaff() {
        List<Complaint> complaints = complaintRepository.findByStatus("PENDING");
        return complaints.stream()
                .map(complaint -> new ComplaintDTO(
                        complaint.getComplaintId(),
                        complaint.getDescription(),
                        complaint.getSubmittedDate(),
                        complaint.getRating(),
                        complaint.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplaintDTO> getComplaintByUser(Long userId) {
        // Tìm tất cả các complaint theo userId
        List<Complaint> complaints = complaintRepository.findByUser_UserId(userId);

        // Chuyển đổi entity thành DTO
        return complaints.stream()
                .map(complaint -> new ComplaintDTO(
                        complaint.getComplaintId(),
                        complaint.getDescription(),
                        complaint.getSubmittedDate(),
                        complaint.getRating(),
                        complaint.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplaintDTO> searchByDescription(String description) {
        List<Complaint> complaints = complaintRepository.findByDescriptionContaining(description);
        return complaints.stream()
                .map(complaint -> new ComplaintDTO(
                        complaint.getComplaintId(),
                        complaint.getDescription(),
                        complaint.getSubmittedDate(),
                        complaint.getRating(),
                        complaint.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplaintDTO> searchByStatus(String status) {
        List<Complaint> complaints = complaintRepository.findByStatus(status);
        return complaints.stream()
                .map(complaint -> new ComplaintDTO(
                        complaint.getComplaintId(),
                        complaint.getDescription(),
                        complaint.getSubmittedDate(),
                        complaint.getRating(),
                        complaint.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplaintDTO> searchBySubmittedDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<Complaint> complaints = complaintRepository.findBySubmittedDateBetween(startDate, endDate);
        return complaints.stream()
                .map(complaint -> new ComplaintDTO(
                        complaint.getComplaintId(),
                        complaint.getDescription(),
                        complaint.getSubmittedDate(),
                        complaint.getRating(),
                        complaint.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public ComplaintDTO addComplaint(ComplaintPayLoad complaintPayLoad) {
        // Kiểm tra giá trị của rating (chỉ cho phép từ 1 đến 5)
        if (complaintPayLoad.getRating() < 1 || complaintPayLoad.getRating() > 5) {
            throw new RuntimeException("Invalid rating. Rating must be between 1 and 5.");
        }
        Complaint complaint = new Complaint();
        complaint.setDescription(complaintPayLoad.getDescription());
        complaint.setSubmittedDate(ZonedDateTime.now(VIETNAM_ZONE).toLocalDateTime());
        complaint.setStatus("PENDING");
        complaint.setRating(complaintPayLoad.getRating()); // Set rating

        // Lấy User từ userId và gán vào complaint
        User user = userRepository.findById(complaintPayLoad.getUserId().intValue())
                .orElseThrow(() -> new RuntimeException("User not found"));
        complaint.setUser(user);

        // Lấy Ride từ rideId và gán vào complaint
        Ride ride = rideRepository.findById(complaintPayLoad.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        complaint.setRide(ride);

        complaintRepository.save(complaint);
        return new ComplaintDTO(
                complaint.getComplaintId(),
                complaint.getDescription(),
                complaint.getSubmittedDate(),
                complaint.getRating(),
                complaint.getStatus());
    }


    @Override
    public ComplaintDTO updateComplaintByUser(Long id, ComplaintPayLoad complaintPayLoad) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaint.setDescription(complaintPayLoad.getDescription());
        complaint.setSubmittedDate(ZonedDateTime.now(VIETNAM_ZONE).toLocalDateTime());
        complaint.setStatus("PENDING");

        if (complaintPayLoad.getRating() < 1 || complaintPayLoad.getRating() > 5) {
            throw new RuntimeException("Invalid rating. Rating must be between 1 and 5.");
        }
        complaint.setRating(complaintPayLoad.getRating());

        Ride ride = rideRepository.findById(complaintPayLoad.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        complaint.setRide(ride);

        complaintRepository.save(complaint);

        return new ComplaintDTO(
                complaint.getComplaintId(),
                complaint.getDescription(),
                complaint.getSubmittedDate(),
                complaint.getRating(),
                complaint.getStatus());
    }

    private final List<String> VALID_STATUSES =
            Arrays.asList("ACTIVE", "INACTIVE");
    @Override
    public ComplaintDTO getFeedbackByUserAndRide(Long userId, Long rideId) {
        Complaint complaint = complaintRepository.findByUser_UserIdAndRide_RideId(userId, rideId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        return new ComplaintDTO(
                complaint.getComplaintId(),
                complaint.getDescription(),
                complaint.getSubmittedDate(),
                complaint.getRating(),
                complaint.getStatus());
    }



    @Override
    public ComplaintDTO updateComplaintByStaff(Long userId, Long complaintId, String status) {
        // Kiểm tra nếu role không phải là STAFF
        if (!VALID_STATUSES.contains(status)) {
            throw new RuntimeException("Invalid status. Valid statuses are: " + VALID_STATUSES);
        }

        // Tìm kiếm complaint theo complaintId
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        // Tìm user (staff) theo userId
        User staff = userRepository.findById(userId.intValue())  // Ép kiểu từ Long sang Integer nếu cần
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        // Kiểm tra role của user có thực sự là "STAFF" không
        if (!"STAFF".equalsIgnoreCase(staff.getRole())) {
            throw new RuntimeException("Only staff can update complaints.");
        }

        // Chỉ cập nhật trạng thái và thời gian
        complaint.setSubmittedDate(ZonedDateTime.now(VIETNAM_ZONE).toLocalDateTime()); // Cập nhật thời gian hiện tại
        complaint.setStatus(status);

        // Lưu thay đổi
        complaintRepository.save(complaint);

        // Trả về DTO cập nhật
        return new ComplaintDTO(
                complaint.getComplaintId(),
                complaint.getDescription(),  // Giữ nguyên mô tả cũ
                complaint.getSubmittedDate(),
                complaint.getRating(),
                complaint.getStatus()
        );
    }

    @Override
    public void deleteComplaint(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setStatus("DEACTIVATED");
        complaintRepository.save(complaint);
    }

}
