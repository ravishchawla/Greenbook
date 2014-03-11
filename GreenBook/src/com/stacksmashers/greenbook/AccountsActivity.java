package com.stacksmashers.greenbook;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * this class calls all account activity within the application it is registry
 * of user's account.
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
	private CheckBox check_interst;
	private TextView add_interst;
	private String accountsUser, userType;
	private int userID;
	private static boolean redo = false;
	
	// acount activity 

	public AccountsActivity()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 *            main method that calls other method
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

		setContentView(R.layout.activity_accounts); // calling setcontentview
													// from res

		Bundle extras = getIntent().getExtras();

		userType = extras.getString("Account Type");
		accountsUser = extras.getString("Account User");

		list = (ListView) findViewById(R.id.accounts_list);

		// Toast.makeText(getApplicationContext(), "User: " + accountsUser,
		// Toast.LENGTH_LONG).show();
		Log.i("TAG", "User: " + accountsUser);

		Cursor csr = sqldbase.query(DBHelper.USER_TABLE,
				new String[] { DBHelper.USERS_ID }, DBHelper.USER_EMAIL + " = '"
						+ accountsUser + "'", null, null, null, null);

		if (csr.getCount() != 0)
		{
			csr.moveToFirst();
			userID = csr.getInt(0);
			Log.i("User ID: ", "" + userID);
		}

		updateData(DBHelper.ACCOUNT_ID);

	}

	/**
	 * @param menu
	 * @return boolean 
	 * we call this method for option menu 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		getMenuInflater().inflate(R.menu.menu_accounts, menu);

		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * @param item 
	 * @return boolean 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		if (item.getItemId() == R.id.accounts_add)
		{
			addAccount();
			

		}

		else if(item.getItemId() == R.id.accounts_settings)
		{
			Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
			intent.putExtra("User ID", userID);
			startActivity(intent);
		}
		
		else if (item.getItemId() == R.id.accounts_name_sort)   // sort name 
		{

			updateData(DBHelper.ACCOUNT_NAME);

		}

		else if (item.getItemId() == R.id.accounts_balance_sort)  // sort balance 
		{

			updateData(DBHelper.ACCOUNT_BALANCE);
		}

		else if (item.getItemId() == R.id.accounts_color_sort)   // sort color accounts 
		{
			updateData(DBHelper.ACCOUNT_COLOR);
		}

		else if (item.getItemId() == R.id.accounts_bank_sort)  // sort bank accounts 
		{
			updateData(DBHelper.ACCOUNT_BANK);
		}

		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}


	public void addAccount()    // add account 
	{
		Intent in = new Intent(this, SplashScreen.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, in, 0);

		Notification noti = new NotificationCompat.Builder(this) 
		// get new notoficationcomplar builder from notification 
				.setContentTitle("Verify Email").setContentText("Please verify your email address")
				.setContentIntent(pIntent)  // set contentintent 
				.setSmallIcon(R.drawable.greenbooklauncher).build();
		NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nManager.notify(1, noti);


		dialogView = getLayoutInflater()  // dialogview 
				.inflate(R.layout.dialog_accounts, null);

		add_name = (TextView) dialogView  // add name 
				.findViewById(R.id.accounts_dialog_name);
		add_balance = (TextView) dialogView   // add balance 
				.findViewById(R.id.accounts_dialog_balance);
		add_custom = (AutoCompleteTextView) dialogView  // add custom 
				.findViewById(R.id.accounts_display_custom);
		choose_bank = (Spinner) dialogView        // choose bank 
				.findViewById(R.id.accounts_dialog_bank);
		choose_color = (Spinner) dialogView      // choose color 
				.findViewById(R.id.accounts_dialog_color);

		check_interst = (CheckBox) dialogView    // check interest 
				.findViewById(R.id.accounts_interst);

		add_interst = (TextView) dialogView      // add interst 
				.findViewById(R.id.accounts_interst_name);

		add_interst.setVisibility(View.GONE);

		check_interst.setOnCheckedChangeListener(new OnCheckedChangeListener()
		// set checkedchangelistener 
		{

			/**
			 * @param buttonview
			 * @param  ischecked
			 * @return void
			 * @return boolean 
			 * we use this method to check 
			 */
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked)
			{
				// TODO Auto-generated method stub

				if (!isChecked)
				{
					add_interst.setVisibility(View.GONE);
				}
				else
					add_interst.setVisibility(View.VISIBLE);

			}
		});

		add_custom.setVisibility(View.GONE);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.Banks, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		choose_bank.setAdapter(adapter);     

		adapter = ArrayAdapter.createFromResource(this, R.array.Colors,
				android.R.layout.simple_spinner_item);
		choose_color.setAdapter(adapter);

		
		choose_bank.setOnItemSelectedListener(new OnItemSelectedListener()
		{
/**
 * @param adapter
 * @param view
 * @param pos
 * @param id
 * @void return 
 */
			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int pos, long id)
			{
				// TODO Auto-generated method stub

				if (pos == (choose_bank.getCount() - 1))
				{
					add_custom.setVisibility(View.VISIBLE);
				}

				else
					add_custom.setVisibility(View.GONE);

				Log.i("Selected id: ", choose_bank.getCount() + " - " + pos);

			}

		/**
		 * @param arg0
		 * @return void 
		 * we use this method to select nothing 
		 */
			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		final AlertDialog ArtDialog = builder.setView(dialogView)
				.setPositiveButton("Add", new OnClickListener()
				{

	/**
	 * @param dialog
	 * @param which
	 * @return void 
	 */
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub

						// Toast.makeText(getApplicationContext(),
						// choose_bank.getSelectedItem().toString(),
						// Toast.LENGTH_LONG).show();
					}
				}).setTitle("Enter Details")    // set title 
				.setNegativeButton("Cancel", new OnClickListener()
				{

	/**
	 * @param dialog
	 * @param which 
	 * @return void 
	 */
					@Override
					public void onClick(DialogInterface dialog, int which)
					{

						dialog.cancel();
					}
				}).create();

		ArtDialog.setOnShowListener(new DialogInterface.OnShowListener()
		{

			/**
			 * @param dialog
			 * @return void
			 */
			@Override
			public void onShow(DialogInterface dialog)
			{
				// TODO Auto-generated method stub

				Button plus = ArtDialog.getButton(AlertDialog.BUTTON_POSITIVE);  // get alert dialog 

				plus.setOnClickListener(new View.OnClickListener()
				{

	/**
	 * @return void
	 * @param v
	 * we use this method to make sure about click meth
	 */
					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub

						final ContentValues caeser = new ContentValues(); 
						// get new content values from content values 
						caeser.put(DBHelper.ACCOUNT_NAME, add_name.getText()
								.toString());
						caeser.put(DBHelper.ACCOUNT_BALANCE, add_balance
								.getText().toString());
						caeser.put(DBHelper.ACCOUNT_BANK, choose_bank
								.getSelectedItem().toString());
						caeser.put(DBHelper.ACCOUNT_COLOR, choose_color
								.getSelectedItem().toString());
						caeser.put(DBHelper.ACCOUNT_USER, userID);

						if (check_interst.isChecked())
						{
							caeser.put(DBHelper.ACCOUNT_INTEREST, add_interst
									.getText().toString());
						}
						else
							caeser.put(DBHelper.ACCOUNT_INTEREST, "-1");

						Cursor csr = sqldbase.query(DBHelper.ACCOUNT_TABLE,
								new String[] { DBHelper.ACCOUNT_NAME },
								DBHelper.ACCOUNT_USER + " = '" + userID
										+ "' AND " + DBHelper.ACCOUNT_NAME
										+ " = '"
										+ add_name.getText().toString() + "'",
								null, null, null, null);

						if (csr.getCount() != 0)
						{
							// if (!redo)
							// {

							Toast.makeText(
									getApplicationContext(),
									"Account Alreaddy Exists\nPlease Rename the Account",
									Toast.LENGTH_LONG).show();
							// redo = true;
							// }
							// else
							// {
							// sqldbase.insert(DBHelper.ACCOUNT_TABLE, null,
							// caeser);
							// redo = false;

							// ArtDialog.dismiss();
							// updateData();
							// }
						}
						else
						{
							sqldbase.insert(DBHelper.ACCOUNT_TABLE, null,
									caeser);   // inseart sqldbase 
 
							Log.d("cv: ", caeser.toString());
							Log.d("cv: ",
									caeser.getAsString(DBHelper.ACCOUNT_NAME));

							ArtDialog.dismiss();   // dismiss artlog 
							updateData(DBHelper.ACCOUNT_ID);
						}
						// caeser.put(DBHelper.ACCOUNT, value)

					}

				});
			}
		});

		ArtDialog.show();

	}
	
	/**
	 * @param sort
	 * @return void
	 * we update our data by using this method 
	 */

	@SuppressWarnings("deprecation")
	public void updateData(String Sort)
	{

		String from[] = { DBHelper.ACCOUNT_NAME, DBHelper.ACCOUNT_BANK,
				DBHelper.ACCOUNT_BALANCE };
		String query[] = { DBHelper.ACCOUNT_ID, DBHelper.ACCOUNT_NAME,
				DBHelper.ACCOUNT_BANK, DBHelper.ACCOUNT_BALANCE,
				DBHelper.ACCOUNT_COLOR };
		int to[] = { R.id.account_display_name, R.id.account_display_bank,
				R.id.account_display_balance };

		final Cursor csr = sqldbase.query(DBHelper.ACCOUNT_TABLE, query,
				DBHelper.ACCOUNT_USER + " = '" + userID + "'", null, null,
				null, Sort);

		Log.i("TAG", "Cursor Adap = null: " + csr.getCount());

		if (csr.getCount() != 0)
		{
			csr.moveToFirst();
			adap = new SimpleCursorAdapter(getBaseContext(),  // get new simplecurseradp from adap 
					R.layout.listblock_accounts, csr, from, to)
			{

/**
 * @return view
 * @param position
 * @param convertview
 * @param parent 
 * 
 */
				@Override
				public View getView(int position, View convertView,
						ViewGroup parent)
				{

					View v = super.getView(position, convertView, parent);

					String color = csr.getString(4);

					int setColor;     // set color 
					if (color.equals("Red"))   //red 
						setColor = getResources().getColor(
								android.R.color.holo_red_light);
					else if (color.equals("Green"))    // green 
						setColor = getResources().getColor(
								android.R.color.holo_green_dark);
					else if (color.equals("Blue"))     // blue 
						setColor = getResources().getColor(
								android.R.color.holo_blue_light);
					else if (color.equals("Yellow"))   // yellow 
						setColor = getResources().getColor(
								android.R.color.holo_green_light);
					else if (color.equals("Cyan"))     // cyan
						setColor = getResources().getColor(
								android.R.color.holo_blue_bright);
					else if (color.equals("Magenta"))  // magenta 
						setColor = getResources().getColor(
								android.R.color.holo_purple);
					else
						setColor = getResources().getColor(
								android.R.color.white);    // white

					v.setBackgroundColor(setColor);      //set color 
					csr.moveToNext();                   // move curser to next 

					return v;                          // return v 

				}

			};

			list.setAdapter(adap);                  // list set adapter 
		}

	}

}
