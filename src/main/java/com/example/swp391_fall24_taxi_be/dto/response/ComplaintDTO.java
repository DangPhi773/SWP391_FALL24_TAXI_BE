package com.example.swp391_fall24_taxi_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ComplaintDTO {
    private Long complaintId;
    private String description;
    private LocalDateTime submittedDate;
    private Integer rating;
    private String status;

}
