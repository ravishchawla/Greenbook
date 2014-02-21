package com.stacksmashers.greenbook;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/*
 * called when the activity first created 
 */
public class MainActivity extends BaseActivity
{
	Button loginButton, registerButton;
	
	public final String TAG = "activity_main";

	
	/*
	 * @param savedInstanceState
	 * call this method to do initial setup  
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)   
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		loginButton = (Button)findViewById(R.id.loginButton);
		registerButton = (Button)findViewById(R.id.registerButton);
	}

	/*
	 * @param menu
	 * call this method to show the cotextmenu for the menu 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)   
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*
	 * @param view
	 * called when view has been clicked. (login)
	 * 
	 */

	public void onClickLoginMainButton(View view)   // to show the contextmenu for the view 
	{

		Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(loginIntent);                  // login activity
			
	}
	
	/*
	 * @param view
	 * called when view has been clicked. (register) 
	 */
	public void onClickRegisterMainButton(View view)   
	{

		Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
		startActivity(registerIntent);                // register activity 
			
	}
	
	
}
