package mtt.smxp;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import mtt.smtt.Application;

public class Postal {

    static String from = Application.getConfigurationProperty("FromAddress"); // "notifyService@mtt.ru";
    String host; // "192.168.0.222";
    static Properties SystemProperties = System.getProperties();
    static Session session = Session.getDefaultInstance(SystemProperties);    
	
	public Postal()    {
	  System.setProperty("java.net.preferIPv4Stack" , "true");
	  host = Application.getConfigurationProperty("SmtpHost"); // "192.168.0.222";
	  SystemProperties.setProperty("mail.smtp.host", host);
	  SystemProperties.setProperty("mail.smtp.port", Application.getConfigurationProperty("SmtpPort"));
	}
     
	public void sendNote(String to, String text, String subject) {
		try {
			MimeMessage message = new MimeMessage(session);
			message.setContent(text, Application.getConfigurationProperty("ContentCoding")); //"text/html; charset=utf-8");
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(Application.getConfigurationProperty("PreSubject") + subject);
			//Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
	
	public void sendText(String to, String text) {
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("This is the formatted message from MTT-Notification.");
			message.setText(text);
			Transport.send(message);
			System.out.println("Sent text.");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}
}
