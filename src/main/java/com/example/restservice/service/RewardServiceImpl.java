package com.example.restservice.service;

import com.example.restservice.mapper.RewardMapper;
import com.example.restservice.model.Customer;
import com.example.restservice.dto.RewardSummary;
import com.example.restservice.model.Transaction;
import com.example.restservice.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class RewardServiceImpl implements RewardService {

    private static final Logger logger = LoggerFactory.getLogger(RewardServiceImpl.class);
    private final CustomerRepository customerRepository;
    private final RewardMapper rewardMapper;

    @Autowired
    public RewardServiceImpl(CustomerRepository customerRepository, RewardMapper rewardMapper) {
        this.customerRepository = customerRepository;
        this.rewardMapper = rewardMapper;
        logger.info("RewardService initialized");
    }

    @Override
    public RewardSummary calculateRewards(Long customerId, LocalDate startDate, LocalDate endDate) {
        // Default to last 3 months if dates not provided
        LocalDate end = (endDate != null) ? endDate : LocalDate.now();
        LocalDate start = (startDate != null) ? startDate : end.minusMonths(3);
        if(start.isAfter(end)) {
            throw new IllegalStateException("Start date is after end date");
        }

        logger.info("Calculating rewards for customer {} from {} to {}", customerId, start, end);

        Optional<Customer> customer = customerRepository.findById(customerId);

        // Filter transactions within the date range
        List<Transaction> filteredTransactions = rewardMapper.filterTransactionsByDateRange(
                customer.get().getTransactions(), start, end);

        // Create reward summary using mapper
        RewardSummary summary = rewardMapper.toRewardSummary(customer.get(), filteredTransactions);

        logger.info("Customer {} earned {} total points from {} transactions", 
                customerId, summary.getTotalRewards(), summary.getTransactions().size());

        return summary;
    }

    @Override
    public List<RewardSummary> calculateRewardsForAllCustomers(LocalDate startDate, LocalDate endDate) {
        // Default to last 3 months if dates not provided
        LocalDate end = (endDate != null) ? endDate : LocalDate.now();
        LocalDate start = (startDate != null) ? startDate : end.minusMonths(3);

        if(start.isAfter(end)) {
            throw new IllegalStateException("Start date is after end date");
        }
        logger.info("Calculating rewards for all customers from {} to {}", start, end);

        List<RewardSummary> summaries = new ArrayList<>();

        for (Customer customer : customerRepository.findAll()) {
            RewardSummary summary = calculateRewards(customer.getId(), start, end);
            if (summary != null) {
                summaries.add(summary);
            }
        }

        logger.info("Calculated rewards for {} customers", summaries.size());
        return summaries;
    }

    @Override
    public CompletableFuture<RewardSummary> calculateRewardsAsync(Long customerId, LocalDate startDate, LocalDate endDate) {
        logger.info("Asynchronously calculating rewards for customer {}", customerId);

        return CompletableFuture.supplyAsync(() ->
            calculateRewards(customerId, startDate, endDate)
        ).exceptionally(ex -> {
            if (ex.getCause() instanceof com.example.restservice.exception.CustomerNotFoundException) {
                logger.warn("Async calculation: {}", ex.getCause().getMessage());
            } else {
                logger.error("Error calculating rewards asynchronously", ex);
            }
            RewardSummary fallback = new RewardSummary();
            fallback.setTotalRewards(0);
            fallback.setTransactions(new ArrayList<>());
            fallback.setCustomerId(customerId);
            return fallback;
        });
    }

}
