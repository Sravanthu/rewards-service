package com.example.restservice.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", insertable = false, updatable = false)
    @Nonnull
    private Long customerId;

    @Column(nullable = false)
    @Nonnull
    private double amount;

    @Column(nullable = false)
    @Nonnull
    private LocalDate date;

    @Column(nullable = false)
    @Nonnull
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;


}
