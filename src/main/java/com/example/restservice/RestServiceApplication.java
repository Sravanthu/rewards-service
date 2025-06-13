package com.example.restservice;

import com.example.restservice.model.Customer;
import com.example.restservice.model.Transaction;
import com.example.restservice.repository.CustomerRepository;
import com.example.restservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@Transactional
public class RestServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	TransactionRepository transactionRepository;


	@Override
	public void run(String... args) throws Exception {


		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Ram Krishna");
		customer.setEmail("ram.krishna@example.com");

		customerRepository.save(customer);

		System.out.println("customerRepository = " + customerRepository.findAll());


		Transaction transaction = new Transaction();
		transaction.setId(1L);
		transaction.setCustomer(customer);
		transaction.setAmount(10.00);
		transaction.setDate(LocalDate.now());
		transaction.setDescription("Ram Krishna");
		transaction.setCustomerId(1L);

		transactionRepository.save(transaction);


		System.out.println("transactionRepository.findAll() = " + transactionRepository.findAll());
	}

}
