package com.example.restservice.service;

import com.example.restservice.exception.CustomerNotFoundException;
import com.example.restservice.mapper.RewardMapper;
import com.example.restservice.model.Customer;
import com.example.restservice.dto.RewardSummary;
import com.example.restservice.model.Transaction;
import com.example.restservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RewardServiceTests {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RewardMapper rewardMapper;

    @InjectMocks
    private RewardServiceImpl rewardService;

    private Customer customer;
    private List<Transaction> transactions;
    private RewardSummary expectedSummary;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void setUp() {
        // Setup test data
        customer = new Customer(1L, "John Doe", "john@example.com");

        transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, 1L, 120.50, LocalDate.now().minusDays(5), "Purchase at Electronics Store"));
        transactions.add(new Transaction(2L, 1L, 75.00, LocalDate.now().minusDays(15), "Purchase at Grocery Store"));
        customer.setTransactions(transactions);

        expectedSummary = new RewardSummary(1L, "John Doe");
        expectedSummary.setTotalRewards(90); // Example value

        startDate = LocalDate.now().minusMonths(3);
        endDate = LocalDate.now();
    }

    @Test
    void calculateRewardsShouldReturnRewardSummary() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(rewardMapper.filterTransactionsByDateRange(eq(transactions), eq(startDate), eq(endDate)))
                .thenReturn(transactions);
        when(rewardMapper.toRewardSummary(eq(customer), eq(transactions))).thenReturn(expectedSummary);

        // Act
        RewardSummary result = rewardService.calculateRewards(1L, startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(expectedSummary.getCustomerId(), result.getCustomerId());
        assertEquals(expectedSummary.getTotalRewards(), result.getTotalRewards());

        verify(customerRepository).findById(1L);
        verify(rewardMapper).filterTransactionsByDateRange(transactions, startDate, endDate);
        verify(rewardMapper).toRewardSummary(customer, transactions);
    }

    @Test
    void calculateRewardsShouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> {
            rewardService.calculateRewards(999L, startDate, endDate);
        });

        verify(customerRepository).findById(999L);
        verifyNoInteractions(rewardMapper);
    }

    @Test
    void calculateRewardsForAllCustomersShouldReturnListOfRewardSummaries() {
        // Arrange
        Customer customer2 = new Customer(2L, "Jane Smith", "jane@example.com");
        List<Customer> customers = Arrays.asList(customer, customer2);

        RewardSummary summary1 = new RewardSummary(1L, "John Doe");
        summary1.setTotalRewards(90);

        RewardSummary summary2 = new RewardSummary(2L, "Jane Smith");
        summary2.setTotalRewards(120);

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));

        when(rewardMapper.filterTransactionsByDateRange(eq(transactions), eq(startDate), eq(endDate)))
                .thenReturn(transactions);
        when(rewardMapper.toRewardSummary(eq(customer), eq(transactions))).thenReturn(summary1);

        List<Transaction> transactions2 = new ArrayList<>();
        customer2.setTransactions(transactions2);
        when(rewardMapper.filterTransactionsByDateRange(eq(transactions2), eq(startDate), eq(endDate)))
                .thenReturn(transactions2);
        when(rewardMapper.toRewardSummary(eq(customer2), eq(transactions2))).thenReturn(summary2);

        // Act
        List<RewardSummary> results = rewardService.calculateRewardsForAllCustomers(startDate, endDate);

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(summary1.getCustomerId(), results.get(0).getCustomerId());
        assertEquals(summary1.getTotalRewards(), results.get(0).getTotalRewards());
        assertEquals(summary2.getCustomerId(), results.get(1).getCustomerId());
        assertEquals(summary2.getTotalRewards(), results.get(1).getTotalRewards());

        verify(customerRepository).findAll();
        verify(customerRepository).findById(1L);
        verify(customerRepository).findById(2L);
    }

    @Test
    void calculateRewardsAsyncShouldReturnCompletableFuture() throws ExecutionException, InterruptedException {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(rewardMapper.filterTransactionsByDateRange(eq(transactions), eq(startDate), eq(endDate)))
                .thenReturn(transactions);
        when(rewardMapper.toRewardSummary(eq(customer), eq(transactions))).thenReturn(expectedSummary);

        // Act
        CompletableFuture<RewardSummary> future = rewardService.calculateRewardsAsync(1L, startDate, endDate);
        RewardSummary result = future.get(); // Wait for the async operation to complete

        // Assert
        assertNotNull(result);
        assertEquals(expectedSummary.getCustomerId(), result.getCustomerId());
        assertEquals(expectedSummary.getTotalRewards(), result.getTotalRewards());

        verify(customerRepository).findById(1L);
        verify(rewardMapper).filterTransactionsByDateRange(transactions, startDate, endDate);
        verify(rewardMapper).toRewardSummary(customer, transactions);
    }

    @Test
    void getAllCustomersShouldReturnAllCustomers() {
        // Arrange
        List<Customer> expectedCustomers = Arrays.asList(
                new Customer(1L, "John Doe", "john@example.com"),
                new Customer(2L, "Jane Smith", "jane@example.com")
        );
        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        // Act
        List<Customer> result = rewardService.getAllCustomers();

        // Assert
        assertNotNull(result);
        assertEquals(expectedCustomers.size(), result.size());
        assertEquals(expectedCustomers.get(0).getId(), result.get(0).getId());
        assertEquals(expectedCustomers.get(1).getId(), result.get(1).getId());

        verify(customerRepository).findAll();
    }

    @Test
    void getCustomerByIdShouldReturnCustomer() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Act
        Customer result = rewardService.getCustomerById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(customer.getId(), result.getId());
        assertEquals(customer.getName(), result.getName());

        verify(customerRepository).findById(1L);
    }

    @Test
    void getCustomerByIdShouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> {
            rewardService.getCustomerById(999L);
        });

        verify(customerRepository).findById(999L);
    }
}
