package com.nazdaq.ybms.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.nazdaq.ybms.model.User;

public class SendEmail implements Constants{	

	public static void main(String[] args) throws MessagingException {
		// TODO Auto-generated method stub
		//SendEmail se = new SendEmail();		
	}
	
	public void testMail(JavaMailSender mailSender, String ccEmailAddresss, String email) throws MessagingException, javax.mail.MessagingException{
		String empMail=email;
		
		// Create a new StringBuilder.
		StringBuilder builder = new StringBuilder();
		
		builder.append("<body>");
		builder.append("<h1> Test </h1>");
		builder.append("</body>");
		
		// Convert to string. 
		String result = builder.toString();
		sendMailTo(mailSender, empMail,"Test Mail", result, ccEmailAddresss);
	}

	private String sendMailTo(JavaMailSender mailSender, String to, String b, String c, String ccEmailAddresss) throws MessagingException, javax.mail.MessagingException{
		
		
		String [] ccAll = ccEmailAddresss.split(",");
		Address[] ia = null;
		if(ccEmailAddresss != null && ccEmailAddresss.trim().length() > 0) {
			ia = new InternetAddress[ccAll.length];
		    int i = 0;
		    for (String address : ccAll) {
		        ia[i] = new InternetAddress(address);
		        i++;
		    }
		}
	    
		// creates a simple e-mail object
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(to.trim());
		email.setSubject(b);
		email.setText(c);
		email.setFrom("HRMSMailService");
		MimeMessage message = mailSender.createMimeMessage();
		if(ccEmailAddresss != null && ccEmailAddresss.trim().length() > 0) {
			message.addRecipients(RecipientType.CC, ia);
		}
		
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");	
	    helper.setFrom(email.getFrom());
	    helper.setTo(email.getTo());
	    helper.setSubject(email.getSubject());
	    helper.setText("<html><body>"+email.getText()+"</body></html>",true);
		
		// sends the e-mail
		mailSender.send(message);		
		// forwards to the view named "Result"
		return "Result";
	}
	
	private String sendMailToWithoutCC(JavaMailSender mailSender, String to, String b, String c) throws MessagingException, javax.mail.MessagingException{
		
		// creates a simple e-mail object
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(to.trim());
		email.setSubject(b);
		email.setText(c);
		email.setFrom("HRMSMailService");
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");	
	    helper.setFrom(email.getFrom());
	    helper.setTo(email.getTo());
	    helper.setSubject(email.getSubject());
	    helper.setText("<html><body>"+email.getText()+"</body></html>",true);
		
		// sends the e-mail
		mailSender.send(message);		
		// forwards to the view named "Result"
		return "Result";
	}
	
	public boolean isValidEmailAddress(String email) {
		boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (Exception ex) {
		      result = false;
		   }
		return result;
	}

}
