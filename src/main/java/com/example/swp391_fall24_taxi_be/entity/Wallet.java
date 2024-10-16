package com.example.swp391_fall24_taxi_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    private Double balance;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;

    // Getters and Setters
}
