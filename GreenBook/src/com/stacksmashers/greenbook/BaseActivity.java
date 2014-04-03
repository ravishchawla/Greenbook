package com.stacksmashers.greenbook;

import java.util.Locale;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * A Base SuperClass that generalizes alot of variables and methods common to
 * many activities All Activities in this project inherit this class.
 * 
 * @author Ravish Chawla
 */
public class BaseActivity extends FragmentActivity
{

	/** The currency. */
	String currency = "���";

	/**
	 * Auto-generated default constructor.
	 */
	public BaseActivity()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * Auto-generated default main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * Default auto-generated on Create() method
	 * 
	 * @param savedInstanceState
	 *            - saved data from previous instance
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	/**
	 * A simplified Log Method that writes to debug LogStream.
	 * 
	 * @param message
	 *            - message to print out
	 * @return void
	 */
	public void Log(String message)
	{
		Log.d("GreenBook", message);
	}

	/**
	 * Notifier method that pushes a message to the Notifcation Center .
	 * 
	 * @param id
	 *            - unique id of notifaction
	 * @param title
	 *            - the title of the notification
	 * @param message
	 *            - the message to print
	 * @param intent
	 *            - action to call when touched
	 * @return NotifactionManager - the manager, can be used to cancel message
	 *         outside scope
	 */
	public NotificationManager NotifyUser(int id, String title, String message,
			Intent intent)
	{
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		Notification noti = new NotificationCompat.Builder(this)
				.setContentTitle(title).setContentText(message)
				.setContentIntent(pIntent).setSmallIcon(R.drawable.greenbook)
				.build();
		
		NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nManager.notify(id, noti);

		return nManager;
	}

	/**
	 * 
	 * Returns a normalized email address without `@` and `.` symbols
	 * 
	 * @param email
	 *            - the email to normalize
	 * @return String - normalized email
	 */
	public String normalizeEmail(String email)
	{

		if (email.matches(".+@[a-z]+[.](com|edu|org|gov|batman)"))

		{
			return email.replace("@", "").replace(".", "");
		}

		else
			return null;
	}

	/**
	 * Generates a unique code based on an email address.
	 * 
	 * @param email
	 *            - the email to codify
	 * @return String - the codified email address
	 */
	public String codeEmail(String email)
	{
		String norm = normalizeEmail(email);
		String code = "";
		char a = norm.charAt(0);
		String b = Character.toString(norm.charAt(norm.length() - 1))
				.toUpperCase(Locale.US);
		int c = norm.length() % 9;
		char d = norm.charAt(norm.length() - 4);
		String e = Character.toString(norm.charAt((norm.length()) / 2))
				.toUpperCase(Locale.US);

		code = a + b + c + d + e;

		return code;
	}

	/**
	 * Sends a Mail object through the Network.
	 * 
	 * @param mail
	 *            - the Mail to send
	 * @return void
	 */
	public void send(Mail mail)
	{
		new SendMail().execute(mail); // execute mail
	}

	/**
	 * Send an Email by running the process in a background process.
	 * 
	 * @author Ravish Chawla
	 */
	public class SendMail extends AsyncTask<Mail, Boolean, Boolean>
	{
		/**
		 * Run a process in a Background Thread.
		 * 
		 * @param mails
		 *            the Mail Object to send
		 * @return the object
		 */
		@Override
		protected Boolean doInBackground(Mail... mails)
		{

			if (mails[0] instanceof Mail)
			{
				try
				{
					Mail mail = (Mail) mails[0];
					boolean progress  = mail.send();
					publishProgress(progress);

				}
				catch (Exception e)
				{
					Log("Could not send email - " + e);
					Log(e.getStackTrace().toString());
				}
			}

			else
			{
				Log("Class Cast Exception Mail from Object");
			}

			return null;
		}

		/**
		 * Notifies user on status of the Email Sent.
		 * 
		 * @param values
		 *            - the values
		 * @return void
		 */
		@Override
		protected void onProgressUpdate(Boolean... values)
		{
			super.onProgressUpdate(values);
			boolean val = (Boolean) values[0];
			if (val)
			{
				Toast.makeText(getApplicationContext(),
						"Email Sent Successfully", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Email Not Sent",
						Toast.LENGTH_LONG).show();
			}
		}

		/**
		 * On post execute.
		 * 
		 * @param result
		 *            - the result
		 * @return void this method execute result for any object
		 */
		@Override
		protected void onPostExecute(Boolean result)
		{
		}
	}

}
