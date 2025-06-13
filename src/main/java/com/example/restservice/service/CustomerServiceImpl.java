package com.example.restservice.service;

import com.example.restservice.exception.CustomerNotFoundException;
import com.example.restservice.mapper.RewardMapper;
import com.example.restservice.model.Customer;
import com.example.restservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

   @Autowired
   CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.warn("Customer not found: {}", customerId);
                    return new CustomerNotFoundException(customerId);
                });
    }
}
