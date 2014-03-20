package com.stacksmashers.greenbook;

import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;



/**
 * 
 * this class calls all account activity within the application 
 * it make sure basic stuff about activity life cycle 
 *
 */
public class BaseFragment extends android.support.v4.app.Fragment
{

	
	
	
	public BaseFragment()
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
	/**called this method to start the activity.  
	 * @param savedInstanceState
	 * @return void 
	 */
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		
		
				

	
	}
	
	/**
	 * @param message
	 * @return void
	 * we use this method to log string messege  
	 */
	
	
	public void Log(String message)
	{
		Log.i("GreenBook", message);
	}
	
}
