package com.stacksmashers.greenbook;

import android.os.Bundle;
import android.util.Log;


/**
 * A basic Fragment class that all fragments inherit, with common 
 * variables and methods. 
 * @author Ravish Chawla
 *
 */
public class BaseFragment extends android.support.v4.app.Fragment
{

	
	
	/**
	 * Auto generated default constructor
	 * 
	 */
	public BaseFragment()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * Auto generated default main method
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	@Override
	/**Auto generated default onCreate method  
	 * @param savedInstanceState - saved state from previous instance
	 * @return void 
	 */
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		
		
				

	
	}
	
	/**
	 * A general log method that prints to Log info stream
	 * @param message - the message to print out
	 * @return void   
	 */
	
	
	public void Log(String message)
	{
		Log.i("GreenBook", message);
	}
	
}
