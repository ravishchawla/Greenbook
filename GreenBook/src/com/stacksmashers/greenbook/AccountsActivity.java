package com.stacksmashers.greenbook;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

   /**
    * 
    * this class calls all account activity within the application 
    *it is registry of user's account. 
    *
    */
public class AccountsActivity extends Activity
{

	public AccountsActivity()
	{
		// TODO Auto-generated constructor stub
	}

    /**
	 * @param args
	 * main method that calls other method
     */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
      
	@Override
	/**called this method to start the activity.  
	 * Maintain the activity and application.
	 *@param savedInstanceState 
	 * @return void 
	 */
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_accounts);     // calling setcontentview from res 
		
		Bundle extras = getIntent().getExtras();
		
		TextView type = (TextView)findViewById(R.id.accountType);  // finding textview type 
		
		type.setText(extras.getString("Account Type"));
	
	
	}
	
}
