package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionHistory;
import com.example.demo.repository.TransactionHistoryRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class TransactionHistoryService {
	@Autowired
	public TransactionHistoryRepository transactionHistoryRepository;

	  public byte[] generatePdf(Transaction transaction) throws DocumentException {
		    Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();

		    try {
		      PdfWriter writer = PdfWriter.getInstance(document, baos);
		      writer.setPdfVersion(PdfWriter.VERSION_1_7);

		      Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.DARK_GRAY);
		      Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
		      Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

		      document.open();

		      Paragraph title = new Paragraph("Recharge Invoice", titleFont);
		      title.setAlignment(Element.ALIGN_CENTER);
		      document.add(title);

		      SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		      String invoiceDate = dateFormat.format(new Date());
		      Paragraph invoiceInfo = new Paragraph("\n\nInvoice ID: " + transaction.getInvoiceID() +
	                    "\nInvoice Date: " + invoiceDate, normalFont);
	            invoiceInfo.setAlignment(Element.ALIGN_RIGHT);
	            document.add(invoiceInfo);


		      Paragraph customerInfo = new Paragraph("\n\nCustomer Information:", headerFont);
	            String customerDetails = "\nName: " + transaction.getFirstName() + " " + transaction.getLastName() +
	                    "\nEmail: " + transaction.getEmailAddress() +
	                    "\nPhone: " + transaction.getPhoneNumber();
	            customerInfo.add(new Chunk(customerDetails, normalFont));
	            document.add(customerInfo);
	            Paragraph rechargeLabel = new Paragraph("\nRecharge Details:", headerFont);
	            document.add(rechargeLabel);

	            
	            StringBuilder rechargeDetails = new StringBuilder();
	            rechargeDetails.append("Transaction ID: ").append(transaction.getTransactionId()).append("\n");
	            rechargeDetails.append("Date: ").append(getFormattedDate(transaction.getDate())).append("\n");
	            rechargeDetails.append("Plan: ").append(transaction.getPlanName()).append("\n");
	            rechargeDetails.append("PlanPrice:").append(transaction.getPlanPrice()).append("\n");
	            rechargeDetails.append("Wallet Used:").append(transaction.getWallet()).append("\n");
	            rechargeDetails.append("Amount Paid: ").append(transaction.getPaid()).append("\n");
	            rechargeDetails.append("Transaction ID: ").append(transaction.getPaymentMethod()).append("\n");
	            rechargeDetails.append("Payment ID: ").append(transaction.getPaymentMethodId()).append("\n");
	            rechargeDetails.append("Next Payment On: ").append(getFormattedDate(transaction.getNextPaymentDate()));	
	           
	            Paragraph rechargeInfo = new Paragraph(rechargeDetails.toString(), normalFont);
	            document.add(rechargeInfo);


		      Paragraph total = new Paragraph("Total Amount:"+transaction.getPlanPrice(), headerFont);
		      total.setAlignment(Element.ALIGN_RIGHT);
		      document.add(total);

		      document.close();

		    } catch (DocumentException e) {
		      e.printStackTrace();
		    }

		    return baos.toByteArray();
		  }

	  private String getFormattedDate(Date date) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
	        return dateFormat.format(date);
	    }




	    public List<TransactionHistory> findByEmailAddress(String emailAddress) {
	       
	        return transactionHistoryRepository.findByEmailAddress(emailAddress);
	    }

	    public List<TransactionHistory> findByPhoneNumber(String phoneNumber) {
	        
	        return transactionHistoryRepository.findByPhoneNumber(phoneNumber);
	    }

		public List<TransactionHistory> getAllTransaction() {
			// TODO Auto-generated method stub
			return transactionHistoryRepository.findAll();
		}

}
