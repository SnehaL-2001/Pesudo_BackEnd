package com.example.demo.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionHistory;
import com.example.demo.service.TransactionService;
import com.itextpdf.text.DocumentException;
@RestController
public class TransactionController {
	@Autowired
	public TransactionService transactionservice;
	 @CrossOrigin(origins="http://localhost:4200/")
	    @PostMapping("/transactions")
	    public ResponseEntity<String> saveTransaction(@RequestBody Transaction transaction) {
		 try {
		        transactionservice.saveTransaction(transaction);
		        return ResponseEntity.ok().build(); // Return an empty response with a status code of 200
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return an empty response with a status code of 500
		    }
	 }
	 @CrossOrigin(origins = "http://localhost:4200")
	 @PostMapping("/generate-bill")
	 public ResponseEntity<Map<String, String>> generateBill(@RequestBody String emailAddress) {
		    Map<String, String> response = new HashMap<>();
		    
		    try {
		        transactionservice.generateBillByEmail(emailAddress);
		        System.out.println("emailAddress");

		        String result = transactionservice.sendMail(emailAddress);
		        
		        response.put("message", "Bill generated and email sent successfully");
		        return ResponseEntity.ok(response);
		    } catch (Exception e) {
		        response.put("message", "Error generating bill and sending email");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
		}
	 @CrossOrigin(origins="http://localhost:4200/")
	 @GetMapping("/getByEmailAddress")
	    public ResponseEntity<Transaction> getTransactionByEmail(@RequestParam String emailAddress) {
	        Transaction transaction = transactionservice.findByEmailAddress(emailAddress);
	        if (transaction != null) {
	            return new ResponseEntity<>(transaction, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	 @CrossOrigin(origins="http://localhost:4200/")
	 @GetMapping("/getTransactionByPhoneNumber")
	    public ResponseEntity<Optional<Transaction>> getTransactionByPhoneNumber(@RequestParam String phoneNumber) {
	        Optional<Transaction> transaction = transactionservice.findByPhoneNumber(phoneNumber);
	        if (transaction != null) {
	            return ResponseEntity.ok(transaction);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	 @CrossOrigin(origins="http://localhost:4200/")
	    @PostMapping("/transactionssave")
	    public ResponseEntity<String> saveTransactionHistory(@RequestBody TransactionHistory transactionHistory) {
		 System.out.println(transactionHistory);
	        try {
	            transactionservice.saveTransactionHistory(transactionHistory);
	            return ResponseEntity.ok("Transaction saved successfully");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving transaction");
	        }
	    }
	

	 
}

