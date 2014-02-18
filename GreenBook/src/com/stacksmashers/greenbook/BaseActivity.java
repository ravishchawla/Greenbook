package com.stacksmashers.greenbook;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

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
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		dbase = new DBHelper(getBaseContext());
		sqldbase = dbase.getWritableDatabase();
		
		
		Parse.initialize(this, "7003TMIrbLL91cSLNAhVD1dkHK2f6Xx1rtrVUtEY", "UooMQDvQLmMbzI2zsm4yHi1BYCTQFnSuSINL9elv");
		
		ParseObject testObject = new ParseObject("HailCaeser");
		testObject.put("Caeser", "MarkAnthony");
		
		ParseObject crestObject = new ParseObject("HailBrutus");
		crestObject.put("Bruthus", "Romeo");
		
		testObject.saveInBackground();
		crestObject.saveInBackground();
		

	
	}
	
}
