package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailResult {
	private String phoneNumber;
    private String simCardNumber;
    private String activationCode;
}
