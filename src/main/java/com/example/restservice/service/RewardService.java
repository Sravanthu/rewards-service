package com.example.restservice.service;

import com.example.restservice.dto.RewardSummary;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface RewardService {
    
    RewardSummary calculateRewards(Long customerId, LocalDate startDate, LocalDate endDate);
    List<RewardSummary> calculateRewardsForAllCustomers(LocalDate startDate, LocalDate endDate);
    CompletableFuture<RewardSummary> calculateRewardsAsync(Long customerId, LocalDate startDate, LocalDate endDate);

}