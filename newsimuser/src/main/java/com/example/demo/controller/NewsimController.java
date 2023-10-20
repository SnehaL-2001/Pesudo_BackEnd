package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MailResult;
import com.example.demo.dto.VerificationRequest;
import com.example.demo.entity.Newsim;
import com.example.demo.service.NewsimService;


@RestController
@RequestMapping
public class NewsimController {
	@Autowired
	private NewsimService newsimservice;
	@CrossOrigin(origins="http://localhost:4200/")
	 @PostMapping("/newsimrequest")
	    public ResponseEntity<String> registerUser(@RequestBody Newsim newsimReq) {
	        try {
	            newsimservice.registerUser(newsimReq);
	            return ResponseEntity.ok("User registered successfully");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed");
	        }
	        
	      
	    } 
	@CrossOrigin(origins="http://localhost:4200/")
	@PostMapping("/sendmail")
	public ResponseEntity<Map<String, String>> sendEmail(@RequestBody Map<String, String> mail) {
	    String emailAddress = mail.get("emailAddress");
	    String userName = mail.get("userName"); // Updated to match the JSON key

	    // Pass both emailAddress and userName to the NewsimService
	    MailResult mailResult = newsimservice.sendMail(emailAddress, userName);

	    System.out.println(emailAddress);

	    Map<String, String> response = new HashMap<>();
	    response.put("phoneNumber", mailResult.getPhoneNumber());
	    response.put("activationCode", mailResult.getActivationCode());
	    response.put("simCardNumber", mailResult.getSimCardNumber());
System.out.println(response);
	    return ResponseEntity.ok(response);
	}
	@CrossOrigin(origins="http://localhost:4200/")
	 @PostMapping("/verify")
	    public ResponseEntity<Map<String,String>> verifyEmailAndPassword(@RequestBody VerificationRequest request) {
		Map<String, String> response = new HashMap<>();
      
	        String a= newsimservice.verifyEmailAndPassword(request.getEmailAddress(), request.getLoginPassword());
	        response.put("something",a);
	        
	        
	        return ResponseEntity.ok(response);
	    }


	
	
	@CrossOrigin(origins="http://localhost:4200/")
	    @GetMapping("/getuserdetails")
	    public ResponseEntity<List<Newsim>> getUserData() {
	        // Assuming you have a service that retrieves user data
	        List<Newsim> userData = newsimservice.getUserData();
	        
	        return ResponseEntity.ok(userData);
	    }

	@CrossOrigin(origins = "http://localhost:4200/")
	@GetMapping("/userDataByEmail")
	public ResponseEntity<Optional<Newsim>> getUserDataByEmail(@RequestParam String email) {
	    Optional<Newsim> users = newsimservice.getUserByEmail(email);
	    if (users != null && !users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@CrossOrigin(origins="http://localhost:4200/")
	    @GetMapping("/userDataByPhoneNumber")
	    public ResponseEntity<Newsim> getUserDataByPhoneNumber(@RequestParam String phoneNumber) {
	       Newsim user = (Newsim) newsimservice.getUserByPhoneNumber(phoneNumber);
	        if (user != null) {
	            return ResponseEntity.ok(user);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	@CrossOrigin(origins="http://localhost:4200/")
    @GetMapping("/allusers")
    public List<Newsim> getAllUsers() {
        return newsimservice.getAllUsers();
    } 
	
	@CrossOrigin(origins="http://localhost:4200/")
	@PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestParam String phoneNumber) {
        try {
            newsimservice.updateStatus(phoneNumber, "active");
            return ResponseEntity.ok("Status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status");
        }
    }
	@CrossOrigin(origins = "http://localhost:4200/")
	@GetMapping("/activate")
    public Newsim getUserDetailsByCode(@RequestParam String code) {
        return newsimservice.getUserDetailsByCode(code);
    }
	
	
//	@CrossOrigin(origins = "http://localhost:4200/")
//	@PostMapping("/activate-sim")
//	public void activateSim(@RequestBody Map<String, String> activationCode) {
//	    try {
//	        System.out.println(activationCode);
//	        String code = activationCode.get("code");
//	        newsimservice.activateSim(code);
//	    } catch (Exception e) {
//	        // Handle the exception here if needed
//	    }
//	}

	@CrossOrigin(origins = "http://localhost:4200")
	    @PostMapping("/activate-sim")
	    public ResponseEntity<Map<String,String>> activateSim(@RequestBody Map<String, String> activationCode) {
	        Map<String,String> response=new HashMap<>();
	            String code = activationCode.get("code");
	            String s= newsimservice.activateSim(code);
	            response.put("something",s);
	           System.out.println(response);
	            return ResponseEntity.ok(response);
	                
	    }
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/update-wallet/{email}")
    public String updateWalletBalance(@PathVariable String email, @RequestBody Newsim newSim) {
        int newWalletAmount = newSim.getWallet();
        return newsimservice.updateWalletBalance(email, newWalletAmount);
    }
    
	}


