package com.vigilfuoco.mgr.utility;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class MailSenderNOPROXY {

	/**
	   Outgoing Mail (SMTP) Server
	   requires TLS or SSL: smtp.gmail.com (use authentication)
	   Use Authentication: Yes
	   Port for SSL: 465
	 */
	public static void main(String[] args) {

		sendEmail("Oggetto","Messaggio", "Email");
	}
	
	
	public static void sendEmail(String oggetto, String messaggio, String toEmail) {
		
		//READ PROPERTIES
		String fromEmail = PropertiesReader.getProperty("fromEmail");
		String password = PropertiesReader.getProperty("password");
		//String toEmail = PropertiesReader.getProperty("toEmail");
		String mailSmtpHost = PropertiesReader.getProperty("mailSmtpHost");
		String mailSmtpSocketFactoryPort = PropertiesReader.getProperty("mailSmtpSocketFactoryPort");
		String mailSmtpAuth = PropertiesReader.getProperty("mailSmtpAuth");
		String mailSmtpPort = PropertiesReader.getProperty("mailSmtpPort");

		//System.out.println("SSLEmail Inizio");
		Properties props = new Properties();
		props.put("mail.smtp.host", mailSmtpHost); 									//SMTP Host
		props.put("mail.smtp.socketFactory.port", mailSmtpSocketFactoryPort); 		//SSL Port
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory"); 									//SSL Factory Class
		props.put("mail.smtp.auth", mailSmtpAuth); 									//Enabling SMTP Authentication
		props.put("mail.smtp.port", mailSmtpPort); 									//SMTP Port
		
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		
		Session session = Session.getDefaultInstance(props, auth);
		//System.out.println("Session send mail created");
	        MailUtils.sendEmail(session, toEmail,oggetto, messaggio);
	}

}