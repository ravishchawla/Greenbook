package com.stacksmashers.greenbook;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AccountsActivity extends Activity
{

	public AccountsActivity()
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
		
		setContentView(R.layout.activity_accounts);
		
		Bundle extras = getIntent().getExtras();
		
		TextView type = (TextView)findViewById(R.id.accountType);
		
		type.setText(extras.getString("Account Type"));
	
	
	}
	
}
