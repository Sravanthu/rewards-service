package com.example.restservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardSummary {
    private Long customerId;
    private String customerName;
    private Map<String, Integer> monthlyRewards;
    private int totalRewards;
    @JsonIgnore
    private List<TransactionDTO> transactions;
}
