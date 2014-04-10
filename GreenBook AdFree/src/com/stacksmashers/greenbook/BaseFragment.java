package com.stacksmashers.greenbook;

import android.os.Bundle;
import android.util.Log;

/**
 * A basic Fragment class that all fragments inherit, with common 
 * variables and methods. 
 * @author Ravish Chawla
 */
public class BaseFragment extends android.support.v4.app.Fragment
{	
	/**
	 * Auto generated default constructor.
	 */
	public BaseFragment()
	{
	}

	/**
	 * Auto generated default main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
	}

	@Override
	/**Auto generated default onCreate method  
	 * @param savedInstanceState - saved state from previous instance
	 * @return void 
	 */
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);	
	}
	
	/**
	 * A general log method that prints to Log info stream.
	 *
	 * @param message - the message to print out
	 * @return void
	 */
	public void Log(String message)
	{
		Log.i("GreenBook", message);
	}
	
}
