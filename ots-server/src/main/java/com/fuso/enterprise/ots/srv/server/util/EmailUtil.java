package com.fuso.enterprise.ots.srv.server.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	
	@Value("${ots.support.mailId}")
	private String supportMailId;
	
	@Value("${ots.support.mailpassword}")
	private String supportMailPassword;
	
	@Value("${ots.finance.mailId}")
	private String financeMailId;
	
	final String username = "support@microproducershub.com";
	
	public static void sendEmailBill(String to,String cc,String sub,String msg,String billFileName,String filePath){   
		
		final String username = "support@microproducershub.com";
	    final String password = "Otssupport@20@!";

	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", false);
	    props.put("mail.smtp.host", "mail.microproducershub.com");
	    props.put("mail.smtp.port", "587");

	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });
	    try {

	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("support@microproducershub.com"));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(to));
	        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
	        message.setSubject(sub);
	        message.setText(msg);

	        MimeBodyPart messageBodyPart = new MimeBodyPart();

	        Multipart multipart = new MimeMultipart();

	        messageBodyPart = new MimeBodyPart();
	        String file = filePath;
	        String fileName = billFileName;
	        DataSource source = new FileDataSource(file);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(fileName);
	        multipart.addBodyPart(messageBodyPart);
	        message.setContent(multipart);
	        Transport.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	  
	 }    
	
	public void sendOTP(String to,String cc,String sub,String msg){   
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.hostinger.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	
	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(supportMailId, supportMailPassword);
	                }
	            });
	    try {
	
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(supportMailId));
	        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
	        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
	        message.setSubject(sub);
	        
	        // create the message part   
	        MimeBodyPart messageBodyPart = new MimeBodyPart();  
	        //To convert mail content into HTML
	    	messageBodyPart.setText(msg, "utf-8", "html");
	    	
	    	Multipart multipart = new MimeMultipart();  
		    multipart.addBodyPart(messageBodyPart); 
		    message.setContent(multipart); 
		    
	        Transport.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	  
	 } 
	
	public static void sendDonationMail(String to,String cc,String sub,String msg){   
		
		final String username = "support@microproducershub.com";
		final String password = "Otssupport@20@!";
	
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", false);
	    props.put("mail.smtp.host", "mail.microproducershub.com");
	    props.put("mail.smtp.port", "587");
	
	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });
	    try {
	
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("support@microproducershub.com"));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(to));
	        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
	        message.setSubject(sub);
	        message.setText(msg);
	        Transport.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	  
	 } 
	
	//Neha
	
	public static void productNotification(String to,String cc,String sub,String msg){   
		
		final String username = "support@microproducershub.com";
		final String password = "Otssupport@20@!";
	
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", false);
	    props.put("mail.smtp.host", "mail.microproducershub.com");
	    props.put("mail.smtp.port", "587");
	
	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });
	    try {
	
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("support@microproducershub.com"));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(to));
	        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
	        message.setSubject(sub);
	        message.setText(msg);
	        Transport.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	  
	 } 
	
	public void sendDistributermail(String to,String cc,String sub,String msg){   
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.hostinger.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	
	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(supportMailId, supportMailPassword);
	                }
	            });
	    try {
	
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(supportMailId));
	        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
	        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
	        message.setSubject(sub);
	        
	        // create the message part   
	        MimeBodyPart messageBodyPart = new MimeBodyPart();  
	        //To convert mail content into HTML
	    	messageBodyPart.setText(msg, "utf-8", "html");
	    	
	    	Multipart multipart = new MimeMultipart();  
		    multipart.addBodyPart(messageBodyPart); 
		    message.setContent(multipart); 
		    
	        Transport.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	  
	 } 
	
	public void sendDistributermailWithAttachment(String to,String cc, String sub, String msg, byte[] attachmentBytes, String fileName) {
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.hostinger.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

	    Session session = Session.getInstance(props,
	        new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(supportMailId, supportMailPassword);
	            }
	        });

	    try {
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(supportMailId));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
	        message.setSubject(sub);

	        // Create the message body part
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        //To convert mail content into HTML
	    	messageBodyPart.setText(msg, "utf-8", "html");

	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);

	        // Create the attachment part from byte array
	        MimeBodyPart attachmentPart = new MimeBodyPart();
	        DataSource source = new ByteArrayDataSource(attachmentBytes, "application/pdf");
	        attachmentPart.setDataHandler(new DataHandler(source));
	        attachmentPart.setFileName(fileName);

	        multipart.addBodyPart(attachmentPart);
	        message.setContent(multipart);

	        Transport.send(message);

	        System.out.println("Attachment Mail sent successfully.");
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}
		
	public void sendEmployeemail(String to,String cc,String sub,String msg){   
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.hostinger.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	
	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(supportMailId, supportMailPassword);
	                }
	            });
	    try {
	
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(supportMailId));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
	        message.setSubject(sub);
	        message.setText(msg);
	        Transport.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	 } 
	
	public void sendCustomermail(String to,String cc,String sub,String msg){   
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.hostinger.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	
	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(supportMailId, supportMailPassword);
	                }
	            });
	    try {
	
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(supportMailId));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
	        message.setSubject(sub);
	        
	        // create the message part   
	        MimeBodyPart messageBodyPart = new MimeBodyPart();  
	        //To convert mail content into HTML
	    	messageBodyPart.setText(msg, "utf-8", "html");
	    	
	    	Multipart multipart = new MimeMultipart();  
		    multipart.addBodyPart(messageBodyPart); 
		    message.setContent(multipart); 
	        
	        Transport.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	 }
	
	public void sendAdminMail(String to,String cc,String sub,String msg){   
	
	    Properties props = new Properties(); 
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.hostinger.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	
	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(supportMailId, supportMailPassword);
	                }
	            });
	    try {
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(supportMailId));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
	        message.setSubject(sub);
	        
	        // create the message part   
	        MimeBodyPart messageBodyPart = new MimeBodyPart();  
	        //To convert mail content into HTML
	    	messageBodyPart.setText(msg, "utf-8", "html");
	    	
	    	Multipart multipart = new MimeMultipart();  
		    multipart.addBodyPart(messageBodyPart); 
		    message.setContent(multipart); 
		    
	        Transport.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	 } 
	
	public void sendFinanceMailWithAttachment(String cc, String sub, String msg, byte[] attachmentBytes, String fileName) {
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.hostinger.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

	    Session session = Session.getInstance(props,
	        new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(supportMailId, supportMailPassword);
	            }
	        });

	    try {
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(supportMailId));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(financeMailId));
	        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
	        message.setSubject(sub);

	        // Create the message body part
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        //To convert mail content into HTML
	    	messageBodyPart.setText(msg, "utf-8", "html");

	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);

	        // Create the attachment part from byte array
	        MimeBodyPart attachmentPart = new MimeBodyPart();
	        DataSource source = new ByteArrayDataSource(attachmentBytes, "application/pdf");
	        attachmentPart.setDataHandler(new DataHandler(source));
	        attachmentPart.setFileName(fileName);

	        multipart.addBodyPart(attachmentPart);
	        message.setContent(multipart);

	        Transport.send(message);

	        System.out.println("Attachment Mail sent successfully.");
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}
	
}
