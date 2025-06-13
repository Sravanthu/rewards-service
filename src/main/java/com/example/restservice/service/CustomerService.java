package com.example.restservice.service;

import com.example.restservice.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CustomerService {

    List<Customer> getAllCustomers();
    Customer getCustomerById(Long customerId);
}
