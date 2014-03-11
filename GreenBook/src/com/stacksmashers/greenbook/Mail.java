package com.stacksmashers.greenbook;

import java.util.Date; 
import java.util.Properties; 
import javax.activation.CommandMap; 
import javax.activation.DataHandler; 
import javax.activation.DataSource; 
import javax.activation.FileDataSource; 
import javax.activation.MailcapCommandMap; 
import javax.mail.BodyPart; 
import javax.mail.Multipart; 
import javax.mail.PasswordAuthentication; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeBodyPart; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart; 

import android.os.AsyncTask;


// extends mail from javax 
public class Mail extends javax.mail.Authenticator 
{
	
	private String username;
	private String password;
	
	private String addressTo;
	private String addressFrom;
	
	private String subject;
	private String body;
	
	private String port;
	private String sport;
	
	private String host;
	
	private boolean auth;
	private boolean debuggable;
	
	private Multipart multipart;
	

	public Mail()     // public mail 
	{
		// TODO Auto-generated constructor stub
	
		host = "smtp.gmail.com";
		port = "465";
		sport = "465";
		
		username = password = addressFrom = addressTo = subject = body = "";
		auth = debuggable = true;
		multipart = new MimeMultipart();
		
		
		MailcapCommandMap mCap = (MailcapCommandMap)CommandMap.getDefaultCommandMap();
	    mCap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
	    mCap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
	    mCap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
	    mCap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
	    mCap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
	    CommandMap.setDefaultCommandMap(mCap); 
	
	}
	
	// get public mail usermane and password 
	public Mail (String username, String password)
	{
		this();
		this.username = username;
		this.password = password;
	}
	
	/**
	 * @param from
	 * @return void 
	 */
	public void setFrom(String from)
	{
		this.addressFrom = from;
	}
	
	/**
	 * @return void
	 * @param to 
	 */
	public void setTo(String to)
	{
		this.addressTo = to;
	}
	
	/**
	 * @param sub
	 * @return void 
	 * this methos setsubject 
	 */
	public void setSubject(String sub)
	{
		this.subject = sub;
	}
	
	/**
	 * @param body
	 * @reutn void 
	 * this method setmessge 
	 */
	public void setMessage(String body)
	{
		this.body = body;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(username, password);
	}
	
	
	public boolean send() throws Exception {
		
		Properties properties = setProperties(); // set properties 
		
		
		if(!username.equals("") && !password.equals("") && !addressTo.equals("") && !addressFrom.equals("") && !subject.equals("") && !body.equals(""))
		{
			
			Session session = Session.getInstance(properties, this);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(addressFrom));
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(addressTo));
			message.setSubject(subject);
			message.setSentDate(new Date());
			
			MimeBodyPart bodyPart = new MimeBodyPart();  // get new minebodypart from bodypart 
			bodyPart.setText(body);      
			multipart.addBodyPart(bodyPart);
			message.setContent(multipart);
			
			Transport.send(message);    // send messege 
			
			return true;
			
			
			
			
		}
		else
			return false;
		
	}
	
	private Properties setProperties()     // set properties 
	
	{
		
		
		
		Properties properties = new Properties();   // get new properties 
		
		properties.put("mail.smtp.host", host);
		
		if(debuggable)
			properties.put("mail.debug", "true");
		
		if(auth)
			properties.put("mail.smtp.auth", "true");
		
		
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.socketFactory.port", sport);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");
		
		
		return properties;
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	
	
	
	
	
}
