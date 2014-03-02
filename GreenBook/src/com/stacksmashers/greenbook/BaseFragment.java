package com.stacksmashers.greenbook;

import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

/**
 * 
 * this class calls all account activity within the application 
 * it make sure basic stuff about activity life cycle 
 *
 */
public class BaseFragment extends Fragment
{

	DBHelper dbase;
	SQLiteDatabase sqldbase;
	
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
	
		dbase = new DBHelper(getActivity());
		sqldbase = dbase.getWritableDatabase();
		
		
				

	
	}
	
	
	public void Log(String message)
	{
		Log.i("GreenBook", message);
	}
	
}
