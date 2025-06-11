package com.example.restservice.service;

import com.example.restservice.exception.CustomerNotFoundException;
import com.example.restservice.mapper.RewardMapper;
import com.example.restservice.model.Customer;
import com.example.restservice.model.RewardSummary;
import com.example.restservice.model.Transaction;
import com.example.restservice.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

        logger.info("Calculating rewards for customer {} from {} to {}", customerId, start, end);

        Customer customer = getCustomerById(customerId);

        // Filter transactions within the date range
        List<Transaction> filteredTransactions = rewardMapper.filterTransactionsByDateRange(
                customer.getTransactions(), start, end);

        // Create reward summary using mapper
        RewardSummary summary = rewardMapper.toRewardSummary(customer, filteredTransactions);

        logger.info("Customer {} earned {} total points from {} transactions", 
                customerId, summary.getTotalRewards(), summary.getTotalTransactions());

        return summary;
    }

    @Override
    public List<RewardSummary> calculateRewardsForAllCustomers(LocalDate startDate, LocalDate endDate) {
        // Default to last 3 months if dates not provided
        LocalDate end = (endDate != null) ? endDate : LocalDate.now();
        LocalDate start = (startDate != null) ? startDate : end.minusMonths(3);

        logger.info("Calculating rewards for all customers from {} to {}", start, end);

        List<RewardSummary> summaries = new ArrayList<>();

        for (Customer customer : getAllCustomers()) {
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

        return CompletableFuture.supplyAsync(() -> {
            // Simulate network delay
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Async calculation interrupted", e);
            }

            return calculateRewards(customerId, startDate, endDate);
        }).exceptionally(ex -> {
            if (ex.getCause() instanceof com.example.restservice.exception.CustomerNotFoundException) {
                logger.warn("Async calculation: {}", ex.getCause().getMessage());
            } else {
                logger.error("Error calculating rewards asynchronously", ex);
            }
            return null;
        });
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    logger.warn("Customer not found: {}", customerId);
                    return new CustomerNotFoundException(customerId);
                });
    }
}
