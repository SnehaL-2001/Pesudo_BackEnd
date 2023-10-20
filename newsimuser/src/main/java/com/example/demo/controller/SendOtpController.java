package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SendOtpRequestDto;
import com.example.demo.service.SendOtp;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@Slf4j
public class SendOtpController {
	@Autowired
	public SendOtp otpService;
	@CrossOrigin(origins="http://localhost:4200/")
	@PostMapping("/requestotp")
	
	public ResponseEntity<Map<String, String>> requestOTP(@RequestBody Map<String, String> requestBody) {
	    String phoneNumber = requestBody.get("phoneNumber");
	    System.out.println(phoneNumber);

	    String otp = otpService.generateRandomOTP();
	    if (otpService.sendOtp(phoneNumber, otp)) {
	    	   Map<String, String> response = new HashMap<>();
	           response.put("message", "OTP sent successfully");
	           return ResponseEntity.ok(response);
	    }
	    return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(null);
	
	}
	@CrossOrigin(origins="http://localhost:4200/")
	@PostMapping("/validate-otp")
    public ResponseEntity<Boolean> validateOTP(@RequestBody SendOtpRequestDto otpRequest) {
        String mobile = otpRequest.getMobile();
        String otp = otpRequest.getOtp();

        boolean isValid = otpService.validateOTP(mobile, otp); // Perform actual OTP validation logic

        if (isValid) {
        	System.out.println("TwilioOtpController.validateOTP()");
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(false);
        }
    }

}
