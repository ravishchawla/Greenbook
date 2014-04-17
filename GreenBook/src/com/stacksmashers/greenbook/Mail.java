package com.stacksmashers.greenbook;

import java.util.Date;
import java.util.Properties;
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * The Class Mail, intended to create a connection with an 
 * email server and send an email message over a network connection. 
 * 
 * @author Ravish Chawla
 */
public class Mail extends javax.mail.Authenticator
{

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The address to. */
	private String addressTo;

	/** The address from. */
	private String addressFrom;

	/** The subject. */
	private String subject;

	/** The body. */
	private String body;

	/** The port. */
	private String port;

	/** The sport. */
	private String sport;

	/** The host. */
	private String host;

	/** The auth. */
	private boolean auth;

	/** The debuggable. */
	private boolean debuggable;

	/** The multipart. */
	private Multipart multipart;

	/**
	 * Email sent for forgotten password.
	 * 
	 * @param name
	 *            - the name of the person being sent to
	 * @param pass
	 *            - the password of the user
	 * @return String - the compiled message
	 */
	public static String EMAIL_FOR_FORGOTTEN_PASSWORD(String name, String pass)
	{
		String message = "Hello " + name + ",\n"
				+ "You have indicated that you forgot the password"
				+ "for your Greenbook account." + "Here's your Password:"
				+ "\n" + "\n" + pass + "\n" + "\n" + "--"
				+ "The GreenBook Team";

		return message;
	}

	/**
	 * Email sent for a new registration of a user
	 * 
	 * @param name
	 *            - the name of the user being sent to
	 * @param username
	 *            - the username of the user being sent to
	 * @param code
	 *            - the verification code of the user
	 * @param password
	 *            - the password of the user
	 * @return - the compiled message
	 */
	public static String EMAIL_FOR_NEW_REGISTRATION(String name,
			String username, String code, String password)
	{
		String message = "Welcome to Greenbook!"
				+ name
				+ ",\n"
				+ "Thank you for creating an account with GreenBook!\n"
				+ "We're glad you're taking the next step to financial management.\n"
				+ "Greenbook is an app that allows you to manage your finances in a simple and colorful way.\n"
				+ "With Greenbook you will have the ability to manage multiple bank accounts as well as monthly bill payments.\n"
				+ "You will be able to make use of sleek graphs to visualize spending reports and create budgets. \n"
				+ "Thank you for being apart of the early stages of Greenbook. There will be many new features and updates soon!\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "To get started please verify your email address by using the following code:  "
				+ code
				+ "\n"
				+ "\n"
				+ "Other Account Information"
				+ "For your reference here is your login credentials for Greenbook."
				+ "\nUsername: " + username + "\nPassword: " + password + "\n"
				+ "\n" + "--" + "The GreenBook Team";

		return message;

	}

	/**
	 * Email sent for resending verification code.
	 * 
	 * @param name
	 *            - the name of the user being sent to
	 * @param code
	 *            - the verification code of the user
	 * @return string the compiled message
	 */
	public static String EMAIL_FOR_RESENDING_VERIFICATION_CODE(String name,
			String code)
	{
		String message = "Hello " + name + ",\n"
				+ "The following is the verification code you requested" + "\n"
				+ "\n" + "\n" + code + "\n" + "\n" + "--"
				+ "The GreenBook Team";

		return message;
	}

	/**
	 * Instantiates a new mail.
	 */
	public Mail()
	{
		host = "smtp.gmail.com";
		port = "465";
		sport = "465";
		username = password = addressFrom = addressTo = subject = body = "";
		auth = debuggable = true;
		multipart = new MimeMultipart();

		MailcapCommandMap mCap = (MailcapCommandMap) CommandMap
				.getDefaultCommandMap();
		mCap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mCap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mCap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mCap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mCap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mCap);
	}

	/**
	 * Instantiates a new mail.
	 * 
	 * @param username
	 *            - the email of the user being sent to
	 * @param password
	 *            - the password of the user being sent to
	 */
	public Mail(String username, String password)
	{
		this();
		this.username = username;
		this.password = password;
	}

	/**
	 * Sets the sending address of the email
	 * 
	 * @param from
	 *            - the sending address
	 * @return void
	 */
	public void setFrom(String from)
	{
		this.addressFrom = from;
	}

	/**
	 * Sets which address email is being sent to
	 * 
	 * @param to
	 *            - address
	 * @return void
	 */
	public void setTo(String to)
	{
		this.addressTo = to;
	}

	/**
	 * Sets the subject of the message
	 * 
	 * @param sub
	 *            - the subject
	 * @return void
	 */
	public void setSubject(String sub)
	{
		this.subject = sub;
	}

	/**
	 * Sets the message of the email
	 * 
	 * @param body
	 *            - the body of the email
	 * @reutn void
	 */
	public void setMessage(String body)
	{
		this.body = body;
	}

	/**
	 * This method verifies that the email address provided has correct IMAP
	 * authorization.
	 * 
	 * @return PasswordAuthentication - an object containg correct authorization
	 */
	@Override
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(username, password);
	}

	/**
	 * called to actually send the message, using the JavaMail Transport
	 * Objects.
	 * 
	 * @return true, if successful
	 * @throws Exception
	 *             - if fails
	 */
	public boolean send() throws Exception
	{

		Properties properties = setProperties();

		if (!username.equals("") && !password.equals("")
				&& !addressTo.equals("") && !addressFrom.equals("")
				&& !subject.equals("") && !body.equals(""))
		{

			Session session = Session.getInstance(properties, this);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(addressFrom));
			message.setRecipient(RecipientType.TO, new InternetAddress(
					addressTo));
			message.setSubject(subject);
			message.setSentDate(new Date());

			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText(body);
			multipart.addBodyPart(bodyPart);
			message.setContent(multipart);

			Transport.send(message);

			return true;
		}
		else
			return false;
	}

	/**
	 * Sets properties of the email connection, including which port the email
	 * is sent over, as well as the socket of the receiving address.
	 * 
	 * @return Properties - object containg these attributes.
	 */
	private Properties setProperties()
	{
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		if (debuggable)
			properties.put("mail.debug", "true");

		if (auth)
			properties.put("mail.smtp.auth", "true");

		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.socketFactory.port", sport);
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");

		return properties;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{

	}

}
