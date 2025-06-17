package com.example.restservice.service;

import org.springframework.stereotype.Component;

@Component
public class RewardCalculator {

    public static final int UPPER_THRESHOLD = 100;
    public static final int LOWER_THRESHOLD = 50;

    public int calculateRewardPoints(double amount) {
        int points = 0;
        if(amount < 0 || Double.isNaN(amount) || Double.isInfinite(amount)) {
            return points;
        }
        else if (amount > UPPER_THRESHOLD) {
            int over100 = (int)(amount - UPPER_THRESHOLD);
            points += over100 * 2;
            points += LOWER_THRESHOLD; // 1 point for each dollar between $50-$100 (50 points total)
        } else if (amount > LOWER_THRESHOLD) {
            int over50 = (int)(amount - LOWER_THRESHOLD);
            points += over50;
        }

        return points;
    }
}