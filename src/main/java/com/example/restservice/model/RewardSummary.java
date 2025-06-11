package com.example.restservice.model;

import com.example.restservice.dto.TransactionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardSummary {
    private Long customerId;
    private String customerName;
    private Map<String, Integer> monthlyRewards;
    private int totalRewards;
    @JsonIgnore
    private List<TransactionDTO> transactions;

    public RewardSummary() {
        this.monthlyRewards = new HashMap<>();
        this.transactions = new ArrayList<>();
    }

    public RewardSummary(Long customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.monthlyRewards = new HashMap<>();
        this.totalRewards = 0;
        this.transactions = new ArrayList<>();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Map<String, Integer> getMonthlyRewards() {
        return monthlyRewards;
    }

    public void setMonthlyRewards(Map<String, Integer> monthlyRewards) {
        this.monthlyRewards = monthlyRewards;
    }

    public int getTotalRewards() {
        return totalRewards;
    }

    public void setTotalRewards(int totalRewards) {
        this.totalRewards = totalRewards;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public int getTotalTransactions() {
        return transactions.size();
    }

    public void addMonthlyRewards(String month, int points) {
        monthlyRewards.put(month, monthlyRewards.getOrDefault(month, 0) + points);
        totalRewards += points;
    }

    public void addTransaction(TransactionDTO transaction) {
        this.transactions.add(transaction);
    }

    @Override
    public String toString() {
        return "RewardSummary{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", monthlyRewards=" + monthlyRewards +
                ", totalRewards=" + totalRewards +
                ", transactions=" + transactions +
                '}';
    }
}
