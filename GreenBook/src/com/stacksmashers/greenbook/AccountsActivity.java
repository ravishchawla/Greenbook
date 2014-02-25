package com.stacksmashers.greenbook;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

   /**
    * 
    * this class calls all account activity within the application 
    *it is registry of user's account. 
    *
    */
public class AccountsActivity extends BaseActivity 
{
	private View dialogView;
	private ListView list;
	private TextView add_name;
	private TextView add_balance;
	private TextView add_custom;
	private Spinner choose_bank;
	private Spinner choose_color;
	private SimpleCursorAdapter adap;
	private String accountsUser, userType;
	private int userID;
	
	

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
		
		userType = extras.getString("Account Type");
		accountsUser = extras.getString("Account User");
		
		list = (ListView)findViewById(R.id.accounts_list);
		
		dialogView = getLayoutInflater().inflate(R.layout.dialog_accounts, null);;
		
		add_name = (TextView)dialogView.findViewById(R.id.accounts_dialog_name);
		add_balance = (TextView)dialogView.findViewById(R.id.accounts_dialog_balance);
		add_custom = (AutoCompleteTextView)dialogView.findViewById(R.id.accounts_display_custom);
		choose_bank = (Spinner)dialogView.findViewById(R.id.accounts_dialog_bank);
		choose_color = (Spinner)dialogView.findViewById(R.id.accounts_dialog_color);
		
		add_custom.setVisibility(View.GONE);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Banks, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		choose_bank.setAdapter(adapter);
				
		adapter = ArrayAdapter.createFromResource(this, R.array.Colors, android.R.layout.simple_spinner_item);
		choose_color.setAdapter(adapter);
		
		
		
		choose_bank.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int pos, long id)
			{
				// TODO Auto-generated method stub
				

				if(pos == (choose_bank.getCount() -1))
				{
					add_custom.setVisibility(View.VISIBLE);
				}
			
				else
					add_custom.setVisibility(View.GONE);
				
				Log.i("Selected id: ", choose_bank.getCount() + " - " + pos);
			
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//Toast.makeText(getApplicationContext(), "User: " + accountsUser, Toast.LENGTH_LONG).show();
		Log.i("TAG", "User: " + accountsUser);
		
		Cursor csr = sqldbase.query(DBHelper.USER_TABLE, new String[]{DBHelper.USERS_ID}, DBHelper.USER_NAME + " = '" + accountsUser + "'", null,null,null,null); 
		
		if (csr.getCount() != 0)
		{
			csr.moveToFirst();
			userID = csr.getInt(0);
			Log.i("User ID: ", ""+userID);	
		}
		
		
		
		
		updateData();
	
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
					caeser.put(DBHelper.ACCOUNT_USER, userID);
					sqldbase.insert(DBHelper.ACCOUNT_TABLE, null, caeser);
					updateData();
					//caeser.put(DBHelper.ACCOUNT, value)
					
				}
			}).show();
			
			
		
			
			
			
			
			
	}
	
	
	@SuppressWarnings("deprecation")
	public void updateData()
	{
		
		
		
		String from[] = {DBHelper.ACCOUNT_NAME, DBHelper.ACCOUNT_BANK, DBHelper.ACCOUNT_BALANCE};
		String query[] = {DBHelper.ACCOUNT_ID, DBHelper.ACCOUNT_NAME, DBHelper.ACCOUNT_BANK, DBHelper.ACCOUNT_BALANCE};
		int to[] = {R.id.account_display_name, R.id.account_display_bank, R.id.account_display_balance};
		
		
		
		
		Cursor csr = sqldbase.query(DBHelper.ACCOUNT_TABLE, query, DBHelper.ACCOUNT_USER + " = '" + userID + "'", null, null, null, null);
		
		Log.i("TAG", "Cursor Adap = null: " + csr.getCount());
		
		if(csr.getCount() != 0)
		{
			adap = new SimpleCursorAdapter(getBaseContext(), R.layout.listblock_accounts, csr, from, to);
		
			
			list.setAdapter(adap);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
