package com.stacksmashers.greenbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
	 * @param savedinstancestate
	 * @return void 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);  // create savedinstancestate 
	
		DBDriver driver = new DBDriver(getBaseContext());
		setContentView(R.layout.splash_screen); // set contentview 
		
new Handler().postDelayed(new Runnable(){
			
			@Override
			/**
			 * @return void 
			 */
			public void run(){
				
				
				
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				// get new intent from intent I
				SplashScreen.this.startActivity(i); // start activity 
				SplashScreen.this.finish();   // finish 
			}
			
			
			
		}, SPLASH_TIME_OUT);

	
	
	}
	

}
