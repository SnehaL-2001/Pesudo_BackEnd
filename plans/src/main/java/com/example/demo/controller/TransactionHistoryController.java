package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionHistory;
import com.example.demo.service.TransactionHistoryService;
import com.itextpdf.text.DocumentException;

@RestController
public class TransactionHistoryController {
@Autowired
public TransactionHistoryService transactionHistoryService;
//@CrossOrigin(origins = "http://localhost:4200/")
//@GetMapping("/api/download-pdf")
//public ResponseEntity<byte[]> downloadPdf() throws DocumentException, IOException {
//    // Generate the PDF using the existing method
//    byte[] pdfContent = transactionHistoryService.generateBillByEmail();
//
//    // Set the response headers
//    HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.parseMediaType("application/pdf"));
//    headers.setContentDispositionFormData("attachment", "RechargeInvoice.pdf");
//
//    // Return the PDF content as a ResponseEntity
//    return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
//}
@CrossOrigin(origins="http://localhost:4200/")
@PostMapping("/api/generate-pdf")
public ResponseEntity<byte[]> generatePdf(@RequestBody Transaction transaction) {
  try {
    byte[] pdfData = transactionHistoryService.generatePdf(transaction);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentLength(pdfData.length);

    ResponseEntity<byte[]> response = new ResponseEntity<>(pdfData, headers, HttpStatus.OK);

    return response;
  } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

@CrossOrigin(origins = "http://localhost:4200")
@GetMapping("/getTransactionHistoryByEmailAddress")
public ResponseEntity<List<TransactionHistory>> getTransactionByEmail(@RequestParam String emailAddress) {
    List<TransactionHistory> transactionHistoryList = transactionHistoryService.findByEmailAddress(emailAddress);
    if (!transactionHistoryList.isEmpty()) {
        return new ResponseEntity<>(transactionHistoryList, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

@CrossOrigin(origins = "http://localhost:4200")
@GetMapping("/getTransactionHistoryByPhoneNumber")
public ResponseEntity<List<TransactionHistory>> getTransactionByPhoneNumber(@RequestParam String phoneNumber) {
    List<TransactionHistory> transactionHistoryList = transactionHistoryService.findByPhoneNumber(phoneNumber);
    if (!transactionHistoryList.isEmpty()) {
        return ResponseEntity.ok(transactionHistoryList);
    } else {
        return ResponseEntity.notFound().build();
    }
}
@CrossOrigin(origins = "http://localhost:4200/")
@GetMapping("/allTransaction")
public List<TransactionHistory> getAllUsers() {
    return transactionHistoryService.getAllTransaction();
} 



}
