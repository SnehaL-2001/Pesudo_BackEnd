package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.SendOtpConfig;
import com.example.demo.repository.NewsimRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class SendOtp {
@Autowired
private SendOtpConfig sendOtpConfig;
@Autowired
private NewsimRepository newsimRepository;
String generatedotp;
private Map<String, String> otpStore = new HashMap<>();


public String generateRandomOTP() {
   Random random = new Random();
   int otpValue = 100000 + random.nextInt(900000);
   generatedotp=String.valueOf(otpValue);// Generates a number between 100000 and 999999
   return generatedotp;
}
public boolean sendOtp(String phoneNumber, String otp) {
	try {
		Twilio.init(sendOtpConfig.getAccountSid(),sendOtpConfig.getAuthToken());
		 Message message = Message.creator(
			      new com.twilio.type.PhoneNumber("+91"+phoneNumber),
			      new com.twilio.type.PhoneNumber("+12315257644"),
			      "Your OTP is:"+otp)
			    .create();
		 otpStore.put(phoneNumber, otp);
		 return true;
	}catch(Exception e)
	{
		
	e.printStackTrace();
		return false;
	}
}

public String generateOtp() {
	
	return null;
}

public boolean validateOTP(String mobile, String userOTP) {
	 String storedOTP = otpStore.get(mobile);
	 System.out.println(userOTP);
     return storedOTP != null && storedOTP.equals(userOTP);
}

public boolean phoneNumberExistsInDatabase(String phoneNumber) {
	System.out.println(newsimRepository.existsByPhoneNumber(phoneNumber));
    return newsimRepository.existsByPhoneNumber(phoneNumber);
}
}

