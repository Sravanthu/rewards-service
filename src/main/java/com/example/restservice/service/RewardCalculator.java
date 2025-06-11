package com.example.restservice.service;

import org.springframework.stereotype.Component;

@Component
public class RewardCalculator {

    public int calculateRewardPoints(double amount) {
        int points = 0;

        if (amount > 100) {
            points += 2 * (int)(amount - 100);
            points += 50; // 1 point for each dollar between $50-$100 (50 points total)
        } else if (amount > 50) {
            points += (int)(amount - 50);
        }

        return points;
    }
}