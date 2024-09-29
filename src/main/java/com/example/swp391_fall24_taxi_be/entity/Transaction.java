package com.example.swp391_fall24_taxi_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private Double amount;
    private String transactionType;
    private LocalDateTime transactionDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "walletId")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "rideId")
    private Ride ride;

    // Getters and Setters
}

