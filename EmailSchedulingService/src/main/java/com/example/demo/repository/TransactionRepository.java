package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

	Transaction findByEmailAddress(String emailAddress);

	 List<Transaction> findByNextPaymentDate(LocalDate nextPaymentDate);

}


