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
 * this method helps to make splashscreen 
 * @author CARE
 *
 */
public class SplashScreen extends Activity
{

	public static int SPLASH_TIME_OUT = 3000;
	
	
	
	public SplashScreen()
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
	
	/**
	 * @param savedInstanceState
	 * @return void 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);  // create savedinstancestate 
		
		
		DBDriver driver = new DBDriver(getBaseContext());
		ParseDriver parseDriver = new ParseDriver(this, getIntent());
		setContentView(R.layout.splash_screen); // set contentview 
		new Vars(getApplicationContext());
	//	defineCurrencies();

		
new Handler().post(new Runnable(){
			
			@Override
			/**
			 * @return void 
			 */
			
			public void run(){
				
				
				//defineCurrencies();
				getCurrencies();
				
				
				
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				SharedPreferences.Editor editor = prefs.edit();
				if(!prefs.getBoolean("firstTimeRun", false))
				{
		//			defineCurrencies();
					
					
					
					
					editor.putBoolean("firstTimeRun", true);
					editor.commit();
				}
				
				if(prefs.getString("Currency", "-1").equals("-1"))
				{
					editor.putString("Currency", "USD");
					editor.commit();
				}
				
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				// get new intent from intent I
				SplashScreen.this.startActivity(i); // start activity 
				SplashScreen.this.finish();   // finish 
			}
			
			
			
		});

	
	
	}
	
	public void getCurrencies()
	{
		ParseQuery<ParseObject> currencyQuery = ParseQuery.getQuery(ParseDriver.CURRENCY_TABLE);
		currencyQuery.findInBackground(new FindCallback<ParseObject>()
		{
			
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1)
			{
				// TODO Auto-generated method stub
			
				Vars.currencyParseList = arg0; 
				
			}
		});
	}
	
	
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
