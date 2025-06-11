package com.example.restservice.repository;

import com.example.restservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaCustomerRepository extends JpaRepository<Customer, Long> {

}