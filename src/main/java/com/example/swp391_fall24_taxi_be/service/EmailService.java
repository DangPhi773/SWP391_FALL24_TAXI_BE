package com.example.swp391_fall24_taxi_be.service;


import com.example.swp391_fall24_taxi_be.dto.request.EmailDetails;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}