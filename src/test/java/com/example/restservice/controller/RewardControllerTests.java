package com.example.restservice.controller;

import com.example.restservice.exception.CustomerNotFoundException;
import com.example.restservice.model.Customer;
import com.example.restservice.model.RewardSummary;
import com.example.restservice.service.RewardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RewardController.class)
public class RewardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @Test
    public void getAllCustomersShouldReturnCustomersList() throws Exception {
        // Arrange
        Customer customer1 = new Customer(1L, "John Doe", "john@example.com");
        Customer customer2 = new Customer(2L, "Jane Smith", "jane@example.com");
        List<Customer> customers = Arrays.asList(customer1, customer2);
        
        when(rewardService.getAllCustomers()).thenReturn(customers);

        // Act & Assert
        this.mockMvc.perform(get("/api/rewards/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Jane Smith")));
    }

    @Test
    public void getCustomerByIdShouldReturnCustomer() throws Exception {
        // Arrange
        Customer customer = new Customer(1L, "John Doe", "john@example.com");
        
        when(rewardService.getCustomerById(1L)).thenReturn(customer);

        // Act & Assert
        this.mockMvc.perform(get("/api/rewards/customers").param("customerId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test
    public void getCustomerByIdShouldReturn404WhenCustomerNotFound() throws Exception {
        // Arrange
        when(rewardService.getCustomerById(999L)).thenThrow(new CustomerNotFoundException(999L));

        // Act & Assert
        this.mockMvc.perform(get("/api/rewards/customers").param("customerId", "999"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("Customer not found with ID: 999")));
    }

    @Test
    public void calculateRewardsShouldReturnRewardSummary() throws Exception {
        // Arrange
        Map<String, Integer> monthlyRewards = new HashMap<>();
        monthlyRewards.put("2023-01", 120);
        monthlyRewards.put("2023-02", 90);
        monthlyRewards.put("2023-03", 150);
        
        RewardSummary summary = new RewardSummary(1L, "John Doe");
        summary.setMonthlyRewards(monthlyRewards);
        summary.setTotalRewards(360);
        
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(3);
        
        when(rewardService.calculateRewards(eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(summary);

        // Act & Assert
        this.mockMvc.perform(get("/api/rewards/calculate").param("customerId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.customerName", is("John Doe")))
                .andExpect(jsonPath("$.totalRewards", is(360)))
                .andExpect(jsonPath("$.monthlyRewards.['2023-01']", is(120)))
                .andExpect(jsonPath("$.monthlyRewards.['2023-02']", is(90)))
                .andExpect(jsonPath("$.monthlyRewards.['2023-03']", is(150)));
    }

    @Test
    public void calculateRewardsWithDateRangeShouldReturnRewardSummary() throws Exception {
        // Arrange
        Map<String, Integer> monthlyRewards = new HashMap<>();
        monthlyRewards.put("2023-01", 120);
        monthlyRewards.put("2023-02", 90);
        
        RewardSummary summary = new RewardSummary(1L, "John Doe");
        summary.setMonthlyRewards(monthlyRewards);
        summary.setTotalRewards(210);
        
        when(rewardService.calculateRewards(eq(1L), eq(LocalDate.of(2023, 1, 1)), eq(LocalDate.of(2023, 2, 28))))
                .thenReturn(summary);

        // Act & Assert
        this.mockMvc.perform(get("/api/rewards/calculate")
                .param("customerId", "1")
                .param("startDate", "2023-01-01")
                .param("endDate", "2023-02-28"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.customerName", is("John Doe")))
                .andExpect(jsonPath("$.totalRewards", is(210)))
                .andExpect(jsonPath("$.monthlyRewards.['2023-01']", is(120)))
                .andExpect(jsonPath("$.monthlyRewards.['2023-02']", is(90)));
    }

    @Test
    public void calculateRewardsAsyncShouldReturnAccepted() throws Exception {
        // Arrange
        Map<String, Integer> monthlyRewards = new HashMap<>();
        monthlyRewards.put("2023-01", 120);
        
        RewardSummary summary = new RewardSummary(1L, "John Doe");
        summary.setMonthlyRewards(monthlyRewards);
        summary.setTotalRewards(120);
        
        when(rewardService.calculateRewardsAsync(eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(CompletableFuture.completedFuture(summary));

        // Act & Assert
        this.mockMvc.perform(get("/api/rewards/calculate")
                .param("customerId", "1")
                .param("async", "true"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string(containsString("Async calculation started for customer 1")));
    }
    
    @Test
    public void calculateRewardsForAllCustomersShouldReturnRewardSummaryList() throws Exception {
        // Arrange
        Map<String, Integer> monthlyRewards1 = new HashMap<>();
        monthlyRewards1.put("2023-01", 120);
        monthlyRewards1.put("2023-02", 90);
        
        RewardSummary summary1 = new RewardSummary(1L, "John Doe");
        summary1.setMonthlyRewards(monthlyRewards1);
        summary1.setTotalRewards(210);
        
        Map<String, Integer> monthlyRewards2 = new HashMap<>();
        monthlyRewards2.put("2023-01", 80);
        monthlyRewards2.put("2023-02", 150);
        
        RewardSummary summary2 = new RewardSummary(2L, "Jane Smith");
        summary2.setMonthlyRewards(monthlyRewards2);
        summary2.setTotalRewards(230);
        
        List<RewardSummary> summaries = Arrays.asList(summary1, summary2);
        
        when(rewardService.calculateRewardsForAllCustomers(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(summaries);

        // Act & Assert
        this.mockMvc.perform(get("/api/rewards/calculate"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customerId", is(1)))
                .andExpect(jsonPath("$[0].customerName", is("John Doe")))
                .andExpect(jsonPath("$[0].totalRewards", is(210)))
                .andExpect(jsonPath("$[1].customerId", is(2)))
                .andExpect(jsonPath("$[1].customerName", is("Jane Smith")))
                .andExpect(jsonPath("$[1].totalRewards", is(230)));
    }
}