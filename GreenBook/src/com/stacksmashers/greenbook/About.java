package com.stacksmashers.greenbook;


import com.stacksmashers.greenbook.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * A simple class that inflates the About page, and handles
 * a single click event for contacting the developer
 * @author Ravish
 *
 */
public class About extends BaseActivity
{
	
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.about);

	}
	
	/**
	 * automatically called when the contact button is pressed. 
	 * it automatically opens the default email client, and 
	 * allows user to send an email to the developer. 
	 * @param view
	 */
	
	public void contact(View view)
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"rchawla8@yahoo.com"});
		intent.setType("message/rfc822");
		try{
			startActivity(Intent.createChooser(intent, "Send email..."));
		}
		catch(android.content.ActivityNotFoundException exe)
			{
				Toast.makeText(getApplicationContext(), "There are no Email Clients installed", Toast.LENGTH_LONG).show();
			}
	
	}
	
}
