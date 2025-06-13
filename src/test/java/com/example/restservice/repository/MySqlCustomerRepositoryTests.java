package com.example.restservice.repository;

import com.example.restservice.model.Customer;
import com.example.restservice.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(MySqlCustomerRepository.class)
public class MySqlCustomerRepositoryTests {

    @Autowired
    private MySqlCustomerRepository repository;

    @Test
    void findAllShouldReturnAllCustomers() {
        // Act
        List<Customer> customers = repository.findAll();

        // Assert
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        // The repository is initialized with 3 customers in data123.sql
        assertEquals(3, customers.size());
    }

    @Test
    void findByIdShouldReturnCustomerWhenExists() {
        // Act
        Optional<Customer> result = repository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Ram Krishna", result.get().getName());
        assertEquals("ram.krishna@example.com", result.get().getEmail());
    }

    @Test
    void findByIdShouldReturnEmptyWhenCustomerDoesNotExist() {
        // Act
        Optional<Customer> result = repository.findById(999L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void saveShouldAddNewCustomer() {
        // Arrange - Create a customer with a unique email
        String uniqueEmail = "new_" + System.currentTimeMillis() + "@example.com";
        Customer newCustomer = new Customer(null, "New Customer", uniqueEmail);

        // Act
        Customer savedCustomer = repository.save(newCustomer);

        // Assert
        assertNotNull(savedCustomer.getId());
        assertEquals("New Customer", savedCustomer.getName());
        assertEquals(uniqueEmail, savedCustomer.getEmail());

        // Verify the customer was added to the repository
        Optional<Customer> retrievedCustomer = repository.findById(savedCustomer.getId());
        assertTrue(retrievedCustomer.isPresent());
        assertEquals(savedCustomer.getId(), retrievedCustomer.get().getId());
        assertEquals(uniqueEmail, retrievedCustomer.get().getEmail());
    }

    @Test
    void saveShouldUpdateExistingCustomer() {
        // Arrange
        Optional<Customer> existingCustomer = repository.findById(1L);
        assertTrue(existingCustomer.isPresent());

        Customer customerToUpdate = existingCustomer.get();
        customerToUpdate.setName("Updated Name");
        customerToUpdate.setEmail("updated@example.com");

        // Act
        Customer updatedCustomer = repository.save(customerToUpdate);

        // Assert
        assertEquals(1L, updatedCustomer.getId());
        assertEquals("Updated Name", updatedCustomer.getName());
        assertEquals("updated@example.com", updatedCustomer.getEmail());

        // Verify the customer was updated in the repository
        Optional<Customer> retrievedCustomer = repository.findById(1L);
        assertTrue(retrievedCustomer.isPresent());
        assertEquals("Updated Name", retrievedCustomer.get().getName());
        assertEquals("updated@example.com", retrievedCustomer.get().getEmail());
    }

    @Test
    void deleteByIdShouldRemoveCustomer() {
        // Arrange
        Optional<Customer> existingCustomer = repository.findById(1L);
        assertTrue(existingCustomer.isPresent());

        // Act
        repository.deleteById(1L);

        // Assert
        Optional<Customer> deletedCustomer = repository.findById(1L);
        assertFalse(deletedCustomer.isPresent());
    }

    @Test
    void customerShouldHaveTransactions() {
        // Act
        Optional<Customer> customer = repository.findById(1L);

        // Assert
        assertTrue(customer.isPresent());
        List<Transaction> transactions = customer.get().getTransactions();
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());

        // Verify transaction details
        Transaction firstTransaction = transactions.get(0);
        assertNotNull(firstTransaction.getId());
        assertEquals(1L, firstTransaction.getCustomerId());
        assertTrue(firstTransaction.getAmount() > 0);
        assertNotNull(firstTransaction.getDate());
        assertNotNull(firstTransaction.getDescription());
    }
}
