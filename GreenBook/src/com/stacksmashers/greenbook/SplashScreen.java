package com.stacksmashers.greenbook;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

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
		setContentView(R.layout.splash_screen); // set contentview 
		new Vars();
		
new Handler().postDelayed(new Runnable(){
			
			@Override
			/**
			 * @return void 
			 */
			public void run(){
				
			//	defineCurrencies();
				
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				SharedPreferences.Editor editor = prefs.edit();
				if(!prefs.getBoolean("firstTimeRun", false))
				{
					defineCurrencies();
					
					
					
					
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
			
			
			
		}, SPLASH_TIME_OUT);

	
	
	}
	
	public void defineCurrencies()
	{
		DBDriver.INSERT_CURRENCY("Australian Dollar", "AUD", Vars.DOLLAR);
		DBDriver.INSERT_CURRENCY("Brazillian Real", "BRL", Vars.REAL);
		DBDriver.INSERT_CURRENCY("British Pound", "GBP", Vars.POUND);
		DBDriver.INSERT_CURRENCY("Canadian Dollar", "CAD", Vars.DOLLAR);
		DBDriver.INSERT_CURRENCY("Chinese Yuan", "CNY", Vars.YUAN);
		DBDriver.INSERT_CURRENCY("Europian Euro", "EUR", Vars.EURO);
		DBDriver.INSERT_CURRENCY("Hong Kong Dollar", "HKD", Vars.DOLLAR);
		DBDriver.INSERT_CURRENCY("Indian Rupee", "INR", Vars.RUPEE);
		DBDriver.INSERT_CURRENCY("Japanese Yen", "JPY", Vars.YUAN);
		DBDriver.INSERT_CURRENCY("Mexican Peso", "MXN", Vars.DOLLAR);
		DBDriver.INSERT_CURRENCY("Russian Ruble", "RUB", Vars.RUBLE);
		DBDriver.INSERT_CURRENCY("South African Rand", "ZAR", Vars.RAND);
		DBDriver.INSERT_CURRENCY("Swiss Franc", "CHF", Vars.FRANC);
		DBDriver.INSERT_CURRENCY("United States Dollar", "USD", Vars.DOLLAR);
	}
	

}
