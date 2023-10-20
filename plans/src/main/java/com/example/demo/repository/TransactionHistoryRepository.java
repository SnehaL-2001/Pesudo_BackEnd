package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.entity.TransactionHistory;
@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {

	List<TransactionHistory> findByEmailAddress(String emailAddress);

	List<TransactionHistory> findByPhoneNumber(String phoneNumber);

	
}
