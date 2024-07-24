package com.vigilfuoco.mgr.utility;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {

	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(Session session, String toEmail, String subject, String body){
		try
	    {
		  //READ PROPERTIES
		  String noreplyAddress = PropertiesReader.getProperty("noreplyAddress");
		  String noreplyAlias = PropertiesReader.getProperty("noreplyAlias");

	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress(noreplyAddress, noreplyAlias));

	      msg.setReplyTo(InternetAddress.parse(noreplyAddress, false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setText(body, "UTF-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      //System.out.println("Messaggio pronto per l'invio");
    	  Transport.send(msg);  

	      System.out.println("e-Mail Inviata con Successo!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
}