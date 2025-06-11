package com.example.restservice.dto;

import java.time.LocalDate;

public class TransactionDTO {
    private Long id;
    private Long customerId;
    private double amount;
    private LocalDate date;
    private String description;

    public TransactionDTO() {
    }

    public TransactionDTO(Long id, Long customerId, double amount, LocalDate date, String description) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}