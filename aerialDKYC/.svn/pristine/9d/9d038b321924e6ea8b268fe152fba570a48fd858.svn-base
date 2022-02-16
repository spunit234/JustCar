package com.edios.cdf.controller;

import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.support.ResourceBundleMessageSource;

public class SendMailUsingAuthentication {

	public boolean postMail(String emailRecipients[],String emailCCRecipients, String emailMessage, String emailSubject)
			throws MessagingException {
		Calendar calendar = Calendar.getInstance();
		boolean debug = false;
		try {
			ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
			messageSource.setBasename("app/AppConfiguration");
			System.out.println("Sending mail");
			Properties props = System.getProperties();
			System.out.println("_________" + messageSource.getMessage("SMTPHostName", null, "", Locale.US));
			props.put("mail.smtp.host", messageSource.getMessage("SMTPHostName", null, "", Locale.US));
			props.put("mail.smtp.port", messageSource.getMessage("SMTPPortNo", null, "", Locale.US));
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");
			Authenticator auth = new SMTPAuthenticator(messageSource.getMessage("SMTPUserName", null, "", Locale.US),
					messageSource.getMessage("SMTPPassword", null, "", Locale.US));
			Session session = Session.getInstance(props, auth);
			session.setDebug(debug);

			// create a message
			Message mailMessage = new MimeMessage(session);
			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(
					messageSource.getMessage("SMTPFromEmailAddress", null, "", Locale.US));
			mailMessage.setFrom(addressFrom);
			
			if(!emailCCRecipients.isEmpty())
				mailMessage.addRecipient(RecipientType.CC,new InternetAddress(emailCCRecipients));
			
			InternetAddress[] addressTo = new InternetAddress[emailRecipients.length];
			for (int addressToCounter = 0; addressToCounter < emailRecipients.length; addressToCounter++) {

				addressTo[addressToCounter] = new InternetAddress(emailRecipients[addressToCounter]);
			}
			mailMessage.setRecipients(Message.RecipientType.TO, addressTo);
			mailMessage.setSubject(emailSubject);
			mailMessage.setSentDate(calendar.getTime());
			mailMessage.setContent(emailMessage, "text/html");
			Transport.send(mailMessage);

		} catch (MessagingException exception) {
			exception.printStackTrace();
			return false;
		}
		return true;

	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {

		String username;

		String password;

		public SMTPAuthenticator(String username, String password) {
			this.username = username;
			this.password = password;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

	public static void main(String args[]) throws Exception {
		System.out.println("Sucessfully Sent Mail To New Registered User");
	}

}
