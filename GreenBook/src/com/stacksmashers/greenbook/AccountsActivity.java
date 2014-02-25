package com.stacksmashers.greenbook;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

   /**
    * 
    * this class calls all account activity within the application 
    *it is registry of user's account. 
    *
    */
public class AccountsActivity extends BaseActivity
{
	View dialogView;
	ListView list;
	TextView add_name;
	TextView add_balance;
	Spinner choose_bank;
	Spinner choose_color;
	SeperatedListAdapter adapter;
	
	
	

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
		
		String userType = extras.getString("Account Type");
		
		ListView list = (ListView)findViewById(R.id.accounts_list);
		
		dialogView = getLayoutInflater().inflate(R.layout.dialog_accounts, null);;
		
		add_name = (TextView)dialogView.findViewById(R.id.accounts_dialog_name);
		add_balance = (TextView)dialogView.findViewById(R.id.accounts_dialog_balance);
		choose_bank = (Spinner)dialogView.findViewById(R.id.accounts_dialog_bank);
		choose_color = (Spinner)dialogView.findViewById(R.id.accounts_dialog_color);
		
		
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Banks, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		choose_bank.setAdapter(adapter);
				
		adapter = ArrayAdapter.createFromResource(this, R.array.Colors, android.R.layout.simple_spinner_item);
		choose_color.setAdapter(adapter);
		
	
	
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
	
		getMenuInflater().inflate(R.menu.menu_accounts, menu);
		
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	
		if(item.getItemId() == R.id.accounts_add)
		{
			addAccount();
			
			
		}
		
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
	
	public void addAccount()
	{
		
		
		
			new AlertDialog.Builder(this).setView(dialogView).setPositiveButton("Add", new OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					
					//Toast.makeText(getApplicationContext(), choose_bank.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
						
					
					ContentValues caeser = new ContentValues();
					caeser.put(DBHelper.ACCOUNT_NAME, add_name.getText().toString());
					caeser.put(DBHelper.ACCOUNT_BALANCE, add_balance.getText().toString());
					caeser.put(DBHelper.ACCOUNT_BANK, choose_bank.getSelectedItem().toString());
					caeser.put(DBHelper.ACCOUNT_COLOR, choose_color.getSelectedItem().toString());
					
					sqldbase.insert(DBHelper.ACCOUNT_TABLE, null, caeser);

					//caeser.put(DBHelper.ACCOUNT, value)
					
				}
			}).show();
			
		
			
			
			
			
			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
