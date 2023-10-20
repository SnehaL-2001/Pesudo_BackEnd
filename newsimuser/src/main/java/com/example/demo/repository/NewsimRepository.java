package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Newsim;
@Repository
public interface NewsimRepository extends JpaRepository<Newsim, String> {

	Newsim findByPhoneNumber(String phoneNumber);
	Newsim findByEmailAddress(String emailAddress);
	Newsim findByActivationCode(String code);
	boolean existsByPhoneNumber(String phoneNumber);
	 
}
