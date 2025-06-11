package com.example.restservice.controller;

import com.example.restservice.model.Customer;
import com.example.restservice.model.RewardSummary;
import com.example.restservice.service.RewardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
        logger.info("RewardController initialized");
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getCustomers(@RequestParam(required = false) Long customerId) {
        if (customerId != null) {
            logger.info("Request received to get customer with ID: {}", customerId);
            Customer customer = rewardService.getCustomerById(customerId);
            return ResponseEntity.ok(customer);
        } else {
            logger.info("Request received to get all customers");
            return ResponseEntity.ok(rewardService.getAllCustomers());
        }
    }

    @GetMapping("/calculate")
    public ResponseEntity<?> calculateRewards(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "false") boolean async) {

        logger.info("Request received to calculate rewards");


        if (customerId != null) {
            if (async) {
                rewardService.calculateRewardsAsync(customerId, startDate, endDate);
                return ResponseEntity.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("Async calculation started for customer " + customerId +
                                ". Results will be processed in the background.");
            } else {
                RewardSummary summary = rewardService.calculateRewards(customerId, startDate, endDate);
                logger.info("Calculated rewards for customer {}: {}", customerId, summary);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(summary);
            }
        } else {
            List<RewardSummary> summaries = rewardService.calculateRewardsForAllCustomers(startDate, endDate);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(summaries);
        }
    }
}
