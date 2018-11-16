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

		props.setProperty("mail.smtp.auth", "true");


		Authenticator authenticator = new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("upupgogogoservice@163.com", "omg147258");
			}
		};


		Session session = Session.getDefaultInstance(props, authenticator);


		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("upupgogogoservice@163.com"));

		message.setRecipient(RecipientType.TO, new InternetAddress(email));

		message.setSubject("o2o密码找回");

		String str = "你的密码为" + code + "请及时修改您的密码，防止他人盗取您的账户";

		message.setContent(str, "text/html;charset=UTF-8");


		Transport.send(message);
	}

	
}