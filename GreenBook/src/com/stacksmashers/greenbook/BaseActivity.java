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
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.codec.binary.StringUtils;

/**
 * 
 * this class calls all account activity within the application it make sure
 * basic stuff about activity life cycle
 * 
 */
public class BaseActivity extends Activity
{

	DBHelper dbase;
	SQLiteDatabase sqldbase;

	public BaseActivity()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 *            main method
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

		dbase = new DBHelper(getBaseContext());
		sqldbase = dbase.getWritableDatabase();

		Parse.initialize(this, "7003TMIrbLL91cSLNAhVD1dkHK2f6Xx1rtrVUtEY",
				"UooMQDvQLmMbzI2zsm4yHi1BYCTQFnSuSINL9elv");

		// test the different object
		ParseObject testObject = new ParseObject("HailCaeser");
		testObject.put("Caeser", "MarkAnthony");

		ParseObject crestObject = new ParseObject("HailBrutus");
		crestObject.put("Bruthus", "Romeo");

		// save the activity in background
		testObject.saveInBackground();
		crestObject.saveInBackground();

	}

	public void Log(String message)
	{
		Log.i("GreenBook", message);
	}
	
	public void NotifyUser(String title, String message, Intent intent)
	{
		
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		Notification noti = new NotificationCompat.Builder(this)
				.setContentTitle(title).setContentText(message)
				.setContentIntent(pIntent)
				.setSmallIcon(R.drawable.greenbooklauncher).build();
		NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nManager.notify(1, noti);

		
		
	}
	
	public String normalizeEmail(String email)
	{

		if (email.matches(".+@[a-z]+[.](com|edu|org|gov|batman)"))
		{
			return email.replace("@", "").replace(".", "");

		}

		else
			return null;
	}

	public String codeEmail(String email)
	{
		String norm = normalizeEmail(email);
		String code = "";
		//ravishchawlagmailcom
		
		char a = norm.charAt(0);
		String b = Character.toString(norm.charAt(norm.length() -1)).toUpperCase();
		int c = norm.length()%9;
		char d = norm.charAt(norm.length() -4);
		String e = Character.toString(norm.charAt((norm.length())/2)).toUpperCase();
		
		code = a + b + c + d + e;
		
		return code;
		
		
		
		//rM9oW
		
	}
	
	public void send(Mail mail)
	{
		new SendMail().execute(mail);
	}
	
	
	public class SendMail extends AsyncTask
	{
		
		

		@Override
		protected Object doInBackground(Object... mails)
		{

			if (mails[0] instanceof Mail)
			{
				try
				{

					
					Mail mail = (Mail) mails[0];

					boolean progress = mail.send();
					
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

			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Object... values)
		{
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		
			boolean val = (Boolean)values[0];
		
			if(val)
			{
				Toast.makeText(getApplicationContext(),
						"Email Sent Successfully", Toast.LENGTH_LONG)
						.show();
			}
			else
			{
				Toast.makeText(getApplicationContext(),"Email Not Sent", Toast.LENGTH_LONG).show();	
			}
		
		}
		

		protected void onPostExecute(Object result)
		{

		}
	}
	


	
	
}
	
