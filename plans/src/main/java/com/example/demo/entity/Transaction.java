package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Transaction {
	@Id
    private String phoneNumber;
    private String firstName;
    private String lastName;
	private Date date;
    private String invoiceID;
    private Date nextPaymentDate;
    private String emailAddress;
    private String paymentMethod;
    private String paymentMethodId;
    private String planName;
    private double planPrice;
    private String transactionId;
    private String rechargedId;
    private int wallet;
    private int paid;
}
