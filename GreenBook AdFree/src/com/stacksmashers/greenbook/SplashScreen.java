package com.stacksmashers.greenbook;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * The first activity shown the User, a Splash Screen, that also initializes
 * alot of variables
 * 
 * @author Ravish Chawla
 */
public class SplashScreen extends Activity
{
	/** The splash time out. */
	public static int SPLASH_TIME_OUT = 3000;

	private static long START_TIME = System.currentTimeMillis();

	/**
	 * Instantiates a new splash screen.
	 */
	public SplashScreen()
	{
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

	/**
	 * called automatically when the activity is created. layout is inflated,
	 * views are identified yby id, and listeners and adapter are set
	 * 
	 * @param savedInstanceState
	 *            - saved data from a previous instance
	 * @return void
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		new DBDriver(getBaseContext());
		new ParseDriver(this, getIntent());
		setContentView(R.layout.splash_screen); // set contentview
		new Vars(getApplicationContext());
		getCurrencies();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = prefs.edit();
		if (!prefs.getBoolean("firstTimeRun", false))
		{
			editor.putBoolean("firstTimeRun", true);
			editor.commit();
		}

		if (prefs.getString("Currency", "-1").equals("-1"))
		{
			editor.putString("Currency", "USD");
			editor.commit();
		}

		
		
		new Handler().postDelayed(new Runnable()
		{

			@Override
			/**
			 * run when the post times out
			 * @return  
			 */
			public void run()
			{
			
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				SplashScreen.this.startActivity(i);
				SplashScreen.this.finish();
			}
		}, SPLASH_TIME_OUT - (START_TIME - System.currentTimeMillis()));
	}

	/**
	 * Retreives currencies from the Database, and places them in a list
	 * 
	 * @return void
	 */
	public void getCurrencies()
	{
		ParseQuery<ParseObject> currencyQuery = ParseQuery
				.getQuery(ParseDriver.CURRENCY_TABLE);
		currencyQuery.findInBackground(new FindCallback<ParseObject>()
		{

			/**
			 * called automatically as a callback when Parse finishes
			 * finding in the background
			 * @param arg0 - the list of items it has found
			 * @param arg1 - an exception, null, if worked properly
			 */
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1)
			{
				Vars.currencyParseList = arg0;
			}
		});
	}

	/**
	 * method called to insert new currencies into the database. called rarely,
	 * when the state of the database is changed inadvertently.
	 * 
	 * @return void
	 */
	public void defineCurrencies()
	{
		ParseDriver.INSERT_CURRENCY("Australian Dollar", "AUD", Vars.DOLLAR);
		ParseDriver.INSERT_CURRENCY("Brazillian Real", "BRL", Vars.REAL);
		ParseDriver.INSERT_CURRENCY("British Pound", "GBP", Vars.POUND);
		ParseDriver.INSERT_CURRENCY("Canadian Dollar", "CAD", Vars.DOLLAR);
		ParseDriver.INSERT_CURRENCY("Chinese Yuan", "CNY", Vars.YUAN);
		ParseDriver.INSERT_CURRENCY("Europian Euro", "EUR", Vars.EURO);
		ParseDriver.INSERT_CURRENCY("Hong Kong Dollar", "HKD", Vars.DOLLAR);
		ParseDriver.INSERT_CURRENCY("Indian Rupee", "INR", Vars.RUPEE);
		ParseDriver.INSERT_CURRENCY("Japanese Yen", "JPY", Vars.YUAN);
		ParseDriver.INSERT_CURRENCY("Mexican Peso", "MXN", Vars.DOLLAR);
		ParseDriver.INSERT_CURRENCY("Russian Ruble", "RUB", Vars.RUBLE);
		ParseDriver.INSERT_CURRENCY("South African Rand", "ZAR", Vars.RAND);
		ParseDriver.INSERT_CURRENCY("Swiss Franc", "CHF", Vars.FRANC);
		ParseDriver.INSERT_CURRENCY("United States Dollar", "USD", Vars.DOLLAR);
	}

}
