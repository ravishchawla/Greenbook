package com.stacksmashers.greenbook;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;



/**
 * 
 * this class calls all account activity within the application it make sure
 * basic stuff about activity life cycle
 * 
 */
public class BaseActivity extends FragmentActivity
{

	String currency = "�";
	public BaseActivity()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 *   main method
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	@Override
	/**called this method to start the activity.  
	 * @param savedInstanceState
	 * @return void 
	 */
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        //  calling bese context from dbhelper


	}

	/**
	 * @param message
	 * @return void 
	 * 
	 */
	public void Log(String message)
	{
		Log.i("GreenBook", message);
	}
	
	/**
	 * 
	 * in this method we notify our user about title, messege and intent
	 * @param title
	 * @param message
	 * @param intent 
	 */
	public void NotifyUser(String title, String message, Intent intent)
	{
		// get pending activity 
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		Notification noti = new NotificationCompat.Builder(this)
		// send new notification 
				.setContentTitle(title).setContentText(message)  // setcontent title and text 
				.setContentIntent(pIntent)     // setcontent intent 
				.setSmallIcon(R.drawable.greenbooklauncher).build();  //set small icon 
		// manage notification 
		NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nManager.notify(1, noti);

		
		
	}
	
	/**
	 * @param email
	 * this method normalize email for all string type eail 
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
	 * this method talk about code email for string email 
	 * @param email
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
	 * this method send mail
	 * @param mail
	 * @return void 
	 */
	public void send(Mail mail)
	{
		new SendMail().execute(mail);    // execute mail
	}
	
	
	
	public class SendMail extends AsyncTask
	{
		
		
         // this method runs in backgrond for object mail 
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
		 * @param values
		 * @return void
		 * this method check on progress update for object values 
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
		protected void onPostExecute(Object result)
		{

		}
	}
	


	
	
}
	
