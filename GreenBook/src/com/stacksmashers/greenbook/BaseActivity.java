package com.stacksmashers.greenbook;

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
 * A Base SuperClass that generalizes alot of variables and methods common to many activities
 * All Activities in this project inherit this class.
 * 
 */
public class BaseActivity extends FragmentActivity
{

	String currency = "���";
	
	/**
	 * Auto-generated default constructor
	 * 
	 */
	public BaseActivity()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * Auto-generated default main method
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * Default auto-generated on Create() method
	 * @param savedInstanceState saved data from previous instance 
	 */
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        //  calling bese context from dbhelper


	}

	/**
	 * 
	 * A simplified Log Method that writes to debug LogStream
	 * @param message message to print out
	 * @return void 
	 */
	public void Log(String message)
	{
		Log.d("GreenBook", message);
	}
	
	/**
	 * 
	 * Notifier method that pushes a message to the Notifcation Center 
	 * @param id - unique id of notifaction
	 * @param title - the title of the notification
	 * @param message - the message to print
	 * @param intent  - action to call when touched
	 * @return NotifactionManager - the manager, can be used to cancel message outside scope
	 */
	public NotificationManager NotifyUser(int id, String title, String message, Intent intent)
	{
		// get pending activity 
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		Notification noti = new NotificationCompat.Builder(this)
		// send new notification 
				.setContentTitle(title).setContentText(message)  // setcontent title and text 
				.setContentIntent(pIntent) // setcontent intent 
				.setSmallIcon(R.drawable.greenbooklauncher).build();  //set small icon 
		// manage notification 
		NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nManager.notify(id, noti);

		return nManager;
	
	}
	
	/**
	 * 
	 * Returns a normalized email address without `@` and `.` symbols
	 * @param email - the email to normalize
	 * @return String - normalized email 
	 */
	
	public String normalizeEmail(String email)
	{

		if (email.matches(".+@[a-z]+[.](com|edu|org|gov|batman)"))
			// if email is .com,.edu, .org, .gov, .batman 
		{
			return email.replace("@", "").replace(".", "");
			// return that its true 

		}

		else
			return null;   // return nothing 
	}

	/**
	 * Generates a unique code based on an email address.  
	 * @param email - the email to codify
	 * @return String - the codified email address
	 */
	public String codeEmail(String email)
	{
		String norm = normalizeEmail(email);   // normalize email 
		String code = "";
		//ravishchawlagmailcom 
		
		// descrive all different char, string, int , etc 
		char a = norm.charAt(0);
		String b = Character.toString(norm.charAt(norm.length() -1)).toUpperCase();
		int c = norm.length()%9;
		char d = norm.charAt(norm.length() -4);
		String e = Character.toString(norm.charAt((norm.length())/2)).toUpperCase();
		
		code = a + b + c + d + e;
		
		return code;        // return code 
		
		
		
		//rM9oW
		
	}
	
	/**
	 * Sends a Mail object through the Network
	 * @param mail - the Mail to send
	 * @return void 
	 */
	public void send(Mail mail)
	{
		new SendMail().execute(mail);    // execute mail
	}
	
	
	
	/**
	 * Send an Email by running the process in a background process
	 * @author Ravish Chawla
	 *
	 */
	public class SendMail extends AsyncTask
	{
		
		
        /**
         * Run a process in a Background Thread
         * @param mails the Mail Object to send
         */
		@Override
		protected Object doInBackground(Object... mails)
		{

			if (mails[0] instanceof Mail)       // if not mail 
			{
				try
				{

					
					Mail mail = (Mail) mails[0];     // there are no mails 

					boolean progress = mail.send();  // send mail 
					
					publishProgress(progress);       // progress 

				}
				catch (Exception e)
				{  
					Log("Could not send email - " + e);   // cant send email 
					Log(e.getStackTrace().toString());
				}
			}

			else
			{
				Log("Class Cast Exception Mail from Object");
			}

			// TODO Auto-generated method stub
			return null;     // return null 
		}
		
		
		/**
		 * 
		 * Notifies user on status of the Email Sent
		 * @param values 
		 * @return void
		 * 
		 */
		@Override
		protected void onProgressUpdate(Object... values)
		{
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		
			boolean val = (Boolean)values[0];     // check boolean val 
		
			if(val)      
			{
				Toast.makeText(getApplicationContext(),   // get application context 
						"Email Sent Successfully", Toast.LENGTH_LONG)
						// email sent successfully 
						.show();  // we can see it 
			}
			else
			{
				Toast.makeText(getApplicationContext(),"Email Not Sent", Toast.LENGTH_LONG).show();	
				// email not sent 
			}
		
		}
		

		/**
		 * @param result
		 * @return void 
		 * this method execute result for any object 
		 */
		@Override
		protected void onPostExecute(Object result)
		{

		}
	}
	


	
	
}
	
