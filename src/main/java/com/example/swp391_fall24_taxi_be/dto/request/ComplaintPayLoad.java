package com.example.swp391_fall24_taxi_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintPayLoad {
    private String description;
    private LocalDateTime submittedDate;
    private String status;
}