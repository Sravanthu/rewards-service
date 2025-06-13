package com.example.restservice.mapper;

import com.example.restservice.dto.TransactionDTO;
import com.example.restservice.model.Customer;
import com.example.restservice.dto.RewardSummary;
import com.example.restservice.model.Transaction;
import com.example.restservice.service.RewardCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RewardMapper {

    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    private final RewardCalculator rewardCalculator;

    @Autowired
    public RewardMapper(RewardCalculator rewardCalculator) {
        this.rewardCalculator = rewardCalculator;
    }

    public RewardSummary toRewardSummary(Customer customer, List<Transaction> transactions) {
        RewardSummary summary = new RewardSummary();
        summary.setCustomerId(customer.getId());
        summary.setCustomerName(customer.getName());

        Map<String, Integer> monthlyRewards = new HashMap<>();
        int totalRewards = 0;

        List<TransactionDTO> transactionDTOs = transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        for (TransactionDTO transaction : transactionDTOs) {
            int points = rewardCalculator.calculateRewardPoints(transaction.getAmount());
            String month = transaction.getDate().format(MONTH_FORMATTER);

            monthlyRewards.put(month, monthlyRewards.getOrDefault(month, 0) + points);
            totalRewards += points;
        }

        summary.setMonthlyRewards(monthlyRewards);
        summary.setTotalRewards(totalRewards);
        summary.setTransactions(transactionDTOs);

        return summary;
    }

    public List<Transaction> filterTransactionsByDateRange(List<Transaction> transactions,
                                                           LocalDate startDate,
                                                           LocalDate endDate) {
        return transactions.stream()
                .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getCustomerId(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getDescription()
        );
    }
}
