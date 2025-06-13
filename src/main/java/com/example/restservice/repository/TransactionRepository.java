package com.example.restservice.repository;

import com.example.restservice.model.Customer;
import com.example.restservice.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    
  
}