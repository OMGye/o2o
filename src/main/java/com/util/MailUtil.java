package com.util;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class MailUtil {
	public static void sendMail(String email,String code) throws Exception {

		Properties props = new Properties();

		props.setProperty("mail.host", "smtp.163.com");

		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");

		props.setProperty("mail.smtp.auth", "true");


		Authenticator authenticator = new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("upupgogogoservice@163.com", "omg147258");
			}
		};


		Session session = Session.getInstance(props, authenticator);


		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("upupgogogoservice@163.com"));

		message.setRecipient(RecipientType.TO, new InternetAddress(email));

		message.setSubject("PCB工程在线订单平台");

		String str = code;

		message.setContent(str, "text/html;charset=UTF-8");


		Transport.send(message);
	}



	
}