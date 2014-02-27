package com.stacksmashers.greenbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_main);
		
new Handler().postDelayed(new Runnable(){
			
			@Override
			public void run(){
				
				
				
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				SplashScreen.this.startActivity(i);
				SplashScreen.this.finish();
			}
			
			
			
		}, SPLASH_TIME_OUT);

	
	
	}
	

}
