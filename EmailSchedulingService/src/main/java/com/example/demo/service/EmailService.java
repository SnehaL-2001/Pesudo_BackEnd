package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RestController
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendMail(String toMail,String subject,String body) throws MessagingException {
        MimeMessage helper = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(helper, true);

        message.setFrom("svarsha0108@gmail.com");
        message.setTo(toMail);
        message.setText(body,true);
        message.setSubject(subject);

        mailSender.send(helper);
        System.out.println("Mail sent successfully");
    }
}
