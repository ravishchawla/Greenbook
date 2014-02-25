package com.stacksmashers.greenbook;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * called when the activity first created 
 */
public class MainActivity extends BaseActivity
{
	Button loginButton, registerButton;     // call loginbutton and register button from res 
	
	public final String TAG = "activity_main";

	
	/**
	 * @param savedInstanceState
	 * @return void 
	 * call this method to do initial setup  
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)   
	{
		super.onCreate(savedInstanceState);  // create saveinstancestate 
		setContentView(R.layout.activity_main);
		
		
		loginButton = (Button)findViewById(R.id.loginButton);       // login button 
		registerButton = (Button)findViewById(R.id.registerButton); //register button 
	}

	/**
	 * @param menu
	 * @return true or false 
	 * call this method to show the cotextmenu for the menu 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)   
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	/**
	 * @param view 
	 * @return void 
	 * called when view has been clicked. (login)
	 * 
	 */

	public void onClickLoginMainButton(View view)   // to show the contextmenu for the view 
	{

		Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);  // intent login 
		startActivity(loginIntent);                  // login activity
			
	}
	
	/**
	 * @param view
	 * @return void 
	 * called when view has been clicked. (register) 
	 */
	public void onClickRegisterMainButton(View view)   
	{

		Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class); // intent register
		startActivity(registerIntent);                // register activity 
			
	}
	
	
}
