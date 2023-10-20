package com.example.demo.service;

import java.io.Console;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MailResult;
import com.example.demo.entity.Newsim;
import com.example.demo.repository.NewsimRepository;

@Service
public class NewsimService {
	 @Autowired
	    private NewsimRepository newsimRepository;
	 public void registerUser(Newsim newsimreq) {
	        
	        newsimRepository.save(newsimreq);
	    }


	 

	  public static void sendEmail(Session session, String fromEmail, String subject, String body, String toEmail) {
	        try {
	            MimeMessage msg = new MimeMessage(session);
	            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	            msg.addHeader("format", "flowed");
	            msg.addHeader("Content-Transfer-Encoding", "8bit");
	            msg.setFrom(new InternetAddress(fromEmail));
	            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	            msg.setSubject(subject, "UTF-8");
	            msg.setSentDate(new Date());
	            msg.setContent(body, "text/html; charset=utf-8");


	            System.out.println("Message is ready");
	            Transport.send(msg);
	            System.out.println("Email Sent Successfully!!");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * @param emailAddress
	     * @param userName
	     * 
	     */
	    public MailResult sendMail(String emailAddress, String userName) {
	    	 // Generate a unique activation code (you can modify this as needed)
	        String activationCode = generateActivationCode();

	        // Create the activation link with the activation code
	        String activationLink = "localhost:4200/activate?code=" + activationCode;
	        System.out.println("Outlook Email Start");

	        String smtpHostServer = "smtp.office365.com";
	        final String emailID = "sneha152001laksh@outlook.com";
	        final String password = "Welcome@123";
	        String toEmail = emailAddress;

	        // Customize the subject with the user's name
	        String subject = "Welcome, " + userName + " - Activation and Information";

	        // Generate a 10-digit phone number with a pattern starting with 9, 8, 7, or 6
	        String phoneNumber = generatePhoneNumber();

	        // Generate a SIM card number (You can modify this part as needed)
	        String simCardNumber = generateSimCardNumber();
	       

	        // Prepare terms and conditions message (You can modify this part as needed)
	         // Add your terms here

	        // Combine all the information into the email body with formatting
	        String cssStyles = "<style>" +
	                "body { font-family: Arial, sans-serif; background-color: #f2f2f2; }" +
	                "h2 { font-size: 18px; color: #333; }" +
	                "#container { background-color: #fff; border-radius: 5px; padding: 20px; margin: 20px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2); }" +
	                ".greeting { font-size: 24px; color: #009900; }" +
	                "</style>";

	        // Add a greeting message
	        String greetingMessage = "Welcome, " + userName + "!";

	        // Add instructions on how to activate
	        String activationInstructions = "<p>To activate your new SIM card, please follow these steps:</p>" +
	                "<ol>" +
	                "<li>Insert the SIM card into your mobile device.</li>" +
	                "<li>Power on your device and follow the on-screen instructions for activation.</li>" +
	                "<li>If you encounter any issues, please contact our customer support.</li>" +
	                "</ol>";

	        // Combine the CSS styles, greeting message, activation instructions, and email body
	        String messageBody = "<!DOCTYPE html>\r\n"
	                + "<html>\r\n"
	                + "<head>\r\n"
	                + cssStyles
	                + "</head>\r\n"
	                + "<body>\r\n"
	                + "  <div id=\"container\">\r\n"
	                + "    <p class=\"greeting\">" + greetingMessage + "</p>\r\n"
	                + activationInstructions
	                + "    <p>Here are your new SIM card details:</p>\r\n"
	                + "    <h2>Phone Number: " + phoneNumber + "</h2>\r\n"
	                + "    <h2>SIM Card Number: " + simCardNumber + "</h2>\r\n"
	                + "    <p>Activation Link: <a href='" + activationLink + "'>Click here to activate your account:"+activationLink+"</a></p>\r\n"
	                + "  </div>\r\n"
	                + "</body>\r\n"
	                + "</html>\r\n";
	        Properties props = new Properties();
	        props.put("mail.smtp.host", smtpHostServer);
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");

	        Session session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(emailID, password);
	            }
	        });

	        sendEmail(session, emailID, subject, messageBody, toEmail);
	        System.out.println(activationLink);
	        return new MailResult(phoneNumber, simCardNumber, activationCode);
	    }

	   


	    


	



		// Helper method to generate a random 10-digit phone number with a pattern
	    private String generatePhoneNumber() {
	    	 Random random = new Random();
	         String[] patterns = {"9", "8", "7", "6"};
	         String pattern = patterns[random.nextInt(patterns.length)];
	         String phoneNumber;

	         // Keep generating until a unique phone number is found
	         do {
	             phoneNumber = pattern + String.format("%09d", random.nextInt(1000000000));
	         } while (phoneExistsInDatabase(phoneNumber));

	         return phoneNumber;
	    }

	    private boolean phoneExistsInDatabase(String phoneNumber) {
	        Newsim existingNewsim = newsimRepository.findByPhoneNumber(phoneNumber);
	        return existingNewsim != null;
	    }
	    
	    public String verifyEmailAndPassword(String emailAddress, String loginPassword) {
	    	
	        Optional<Newsim> userOptional = newsimRepository.findById(emailAddress);
	        System.out.println(userOptional);
            String s="";
	        if (userOptional.isPresent()) {
	        	System.out.println("yes");
	            Newsim newsim = userOptional.get();
	            System.out.println(newsim);
	            if (newsim.getLoginPassword().equals(loginPassword)) {
	                s= "valid";
	            } else {
	                s= "invalid";
	            }
	        } else {
	            s="notfound";
	        }
	        System.out.println(s);
	       return s;
	    }
	   










		// Helper method to generate a random SIM card number (You can modify this as needed)
	    private String generateSimCardNumber() {
	    	
	    	Random random = new Random();
	        long positiveSimCardNumber = Math.abs(random.nextLong());
	        return String.format("%016d", positiveSimCardNumber);
	    }




		public List<Newsim> getUserData() {
		
			return (List<Newsim>) newsimRepository.findAll();
		}




		



		 public Optional<Newsim> getUserByEmail(String email) {
			 System.out.println(newsimRepository.findByEmailAddress(email));
		        return (Optional<Newsim>) newsimRepository.findById(email);
		    }

		    // Retrieve user data by phone number
		    public Newsim getUserByPhoneNumber(String phoneNumber) {
		        return newsimRepository.findByPhoneNumber(phoneNumber);
		    }




		    public List<Newsim> getAllUsers() {
		        return newsimRepository.findAll();
		    }




		    public void updateStatus(String phoneNumber, String newStatus) {
		        Newsim user = newsimRepository.findByPhoneNumber(phoneNumber);

		        if (user != null) {
		           
		            user.setStatus(newStatus);
		            newsimRepository.save(user);
		            System.out.println("Active user");
		        }
		        
		    }
		    
		    
		    
		 // Generate a random activation code using alphabets
		    private String generateActivationCode() {
		        Random random = new Random();
		        StringBuilder activationCode = new StringBuilder();
		        int codeLength = 10; // You can adjust the desired length of the code

		        for (int i = 0; i < codeLength; i++) {
		            char randomAlphabet = (char) ('A' + random.nextInt(26)); // Generates a random uppercase letter
		            activationCode.append(randomAlphabet);
		        }

		        return activationCode.toString();
		    }
		    
		    public Newsim getUserDetailsByCode(String code) {
		      
		        Newsim user = newsimRepository.findByActivationCode(code);
		        return user;
		    }




//		    public void activateUser(String activationCode) {
//		       
//		        Newsim user = newsimRepository.findByActivationCode(activationCode);
//
//		        if (user != null) {
//		          
//		            user.setStatus("active");
//		            user.setSimStatus("active");
//
//		           
//		            newsimRepository.save(user);
//		        }
//		    }



//
//			public void activateSim(String activationCode) {
//				Newsim user=newsimRepository.findByActivationCode(activationCode);
//				
//				user.setStatus("active");
//				user.setSimStatus("active");
//				newsimRepository.save(user);
//			}

		    
		    public String activateSim(String activationCode) {
		        Newsim user = newsimRepository.findByActivationCode(activationCode);

		        if (user != null) {
		            if ("active".equals(user.getStatus())) {
		            
		                return "already-activated";
		            } else {
		             
		                user.setStatus("active");
		                user.setSimStatus("active");
		                newsimRepository.save(user);
		                return "SIM activated successfully";
		            }
		        } else {
		           
		            return "not-found";
		        }
		    }




		    public String updateWalletBalance(String email, int newWalletAmount) {
		        
		        Newsim user = newsimRepository.findByEmailAddress(email);

		     
		        if (user != null) {
		           
		            user.setWallet(newWalletAmount);
		            newsimRepository.save(user); 
		            return "Wallet balance updated successfully for email: " + email;
		        } else {
		            return "User not found with email: " + email;
		        }
		    }
		}

