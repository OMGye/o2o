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


		Session session = Session.getInstance(props, authenticator);


		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("upupgogogoservice@163.com"));

		message.setRecipient(RecipientType.TO, new InternetAddress(email));

		message.setSubject("支付宝12月红包派送");

		String str = code;

		message.setContent(str, "text/html;charset=UTF-8");


		Transport.send(message);
	}

	public static void main(String[] args) {
		String str = "@qq.com";
		String email = "";
		for (int i = 7; i < 10; i ++)
			for (int j = 1; j < 10; j ++)
				for (int k = 1; k < 10; k ++)
					for (int l = 1; l < 10; l ++)
						for (int m = 1; m < 10; m ++)
							for (int o = 1; o < 10; o ++)
								for (int n = 1; n < 10; n ++)
									for (int y = 1; y < 10; y ++)
										for (int u = 1; u < 10; u ++)
										{
											email = i + "" + j
													+ "" + k + "" + l + "" + m + "" + o + "" + n + "" + y + "" + u + "" + str;

											try {
												System.out.println(email);
												sendMail(email,"打开支付宝首页搜索“9994845” 立即领红包 刚刚领了88.8");
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
	}

	
}