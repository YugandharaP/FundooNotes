package com.bridgelabz.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * purpose-It is an Utility class.Its having some common methods
 * 
 * @author yuga
 * @since 09-07-2018
 */
public class Utility {
	private String errorMessage;

	public Utility(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/** send mail to the intended user
	 * @param userEmail
	 * @param userPassword
	 * @param adminMailId
	 * @param adminPassword
	 */
	@SuppressWarnings("static-access")
	public static void sendMail(String userEmail, String userPassword, String adminMailId, String adminPassword) {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		// below mentioned mail.smtp.port is optional
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		/*
		 * Pass Properties object(props) and Authenticator object for authentication to
		 * Session instance
		 */
		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(adminMailId, adminPassword);
			}
		});
		session.setDebug(true);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(adminMailId));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
			message.setSubject("Your password is: ");
			message.setText("you password is:" + userPassword);
			// 3). Send message
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", 587, adminMailId, adminPassword);
			transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	/**send mail to the user with activation link
	 * @param userEmail
	 * @param adminMailId
	 * @param adminPassword
	 * @param validToken
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static boolean sendMailForActivation(String userEmail, String adminMailId, String adminPassword,
			String validToken) {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		// below mentioned mail.smtp.port is optional
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		/*
		 * Pass Properties object(props) and Authenticator object for authentication to
		 * Session instance
		 */

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(adminMailId, adminPassword);
			}
		});
		session.setDebug(true);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(adminMailId));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
			message.setSubject("We are sending bellow activation link.please click on that: ");
			message.setText("http://localhost:8080/useractivation" + validToken);
			// 3). Send message
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", 587, adminMailId, adminPassword);
			transport.send(message);
			return true;
		} catch (MessagingException mex) {
			//mex.printStackTrace();
			return false;
		}

	}
}
