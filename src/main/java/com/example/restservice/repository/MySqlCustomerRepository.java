package com.example.restservice.repository;

import com.example.restservice.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class MySqlCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(MySqlCustomerRepository.class);
    private final JpaCustomerRepository jpaCustomerRepository;

    @Autowired
    public MySqlCustomerRepository(JpaCustomerRepository jpaCustomerRepository) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        logger.info("MySqlCustomerRepository initialized");
    }

    @Override
    public List<Customer> findAll() {
        return jpaCustomerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaCustomerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return jpaCustomerRepository.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        jpaCustomerRepository.deleteById(id);
    }
}