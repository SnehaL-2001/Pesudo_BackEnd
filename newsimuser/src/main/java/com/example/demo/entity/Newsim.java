package com.example.demo.entity;


import java.sql.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Newsim {
	@Id	
	 private String emailAddress;
	 private String firstName;
	 private String lastName;
	 private Date dob;
	 private String address;
	 private String location;
	 private String loginPassword;
	 private String phoneNumber;
	 private String status="inactive";
	 private String simNumber;
	 private String activationCode;
	 private String simStatus="inactive";
	 private int wallet;

}