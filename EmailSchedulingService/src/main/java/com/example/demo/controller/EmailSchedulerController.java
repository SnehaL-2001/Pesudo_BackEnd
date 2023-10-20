package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.EmailService;

import jakarta.mail.MessagingException;

@RestController

public class EmailSchedulerController {
	  @Autowired
	    public TransactionRepository transactionRepository;
	    @Autowired
	    public EmailService emailService;

	    @Scheduled(cron = "0 50 6 * * ?")
	    public void sendLicenseExpiryEmails() throws MessagingException {
	        System.out.println("Scheduled mail executed at 06:45 AM.");
	        LocalDate currentDate = LocalDate.now();

	        for (int i = 1; i <= 3; i++) { 
	            LocalDate notificationDate = currentDate.plusDays(i);

	            List<Transaction> usersWithUpcomingPayments = transactionRepository.findByNextPaymentDate(notificationDate);

	            for (Transaction user : usersWithUpcomingPayments) {
	                sendPaymentReminderEmail(user, i);
	            }
	        }
	    }

	    private void sendPaymentReminderEmail(Transaction user, int daysBeforeExpiry) throws MessagingException {
	        
	        String emailBody = "<html><body>"
	                + "<h2 style='color: red;'>Upcoming Payment Reminder</h2>"
	                + "<p style='font-size: 16px;'>Dear " + user.getFirstName() + ",</p>"
	                + "<p style='font-size: 16px;'>This is a reminder that your plan expires in " + daysBeforeExpiry + " days. To ensure uninterrupted services, please recharge now.</p>"
	                + "<p style='font-size: 16px;'>Thank you for choosing ProWaveX, and feel free to contact our support team if you have any questions or need assistance.</p>"
	                + "</body></html>";

	        emailService.sendMail(user.getEmailAddress(), "Plan Expiry Reminder", emailBody);
	    }
}
