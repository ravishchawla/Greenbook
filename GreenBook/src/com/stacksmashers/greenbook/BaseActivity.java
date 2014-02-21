package com.stacksmashers.greenbook;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

/**
 * 
 * this class calls all account activity within the application 
 * it make sure basic stuff about activity life cycle 
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
	 * main method 
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	@Override
	/*called this method to start the activity.  
	 *  
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		dbase = new DBHelper(getBaseContext());
		sqldbase = dbase.getWritableDatabase();
		
		
		Parse.initialize(this, "7003TMIrbLL91cSLNAhVD1dkHK2f6Xx1rtrVUtEY", "UooMQDvQLmMbzI2zsm4yHi1BYCTQFnSuSINL9elv");
		
		//test the different object
		ParseObject testObject = new ParseObject("HailCaeser");
		testObject.put("Caeser", "MarkAnthony");
		
		ParseObject crestObject = new ParseObject("HailBrutus");
		crestObject.put("Bruthus", "Romeo");
		
		//save the activity in background 
		testObject.saveInBackground();
		crestObject.saveInBackground();
		

	
	}
	
}
