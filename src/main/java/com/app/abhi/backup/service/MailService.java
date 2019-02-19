package com.app.abhi.backup.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jasypt.util.text.StrongTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.abhi.backup.controller.BackupController;

@Service
public class MailService {
	
	Logger logger = LoggerFactory.getLogger(BackupController.class);
	
	@Value("${mail.username}")
	private String username;
	
	@Value("${mail.password}")
	private String password;
	
	public void sendMail(String subject, String body) {
		logger.debug("Inside Mail service");
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		
		StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
		textEncryptor.setPassword("password");
		password = textEncryptor.decrypt(this.password);
		 Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username,password);
	            }
	        });
		MimeMessage message = new MimeMessage(session);
		try {

			message.setFrom(new InternetAddress(username));

			InternetAddress[] toAddress = new InternetAddress[1];
			toAddress[0] = new InternetAddress(username);
			message.addRecipient(Message.RecipientType.TO, toAddress[0]);

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			logger.debug("Mail sent for notification");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
