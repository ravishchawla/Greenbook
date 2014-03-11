package com.stacksmashers.greenbook;

import android.annotation.SuppressLint;
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
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
public class AccountsFragment extends BaseFragment
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
	private HomeActivity home;
	
	public AccountsFragment()
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
	 *@param inflater
	 *@param container
	 * @return void 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState); // create savedinstancestate 

		
		View view = inflater.inflate(R.layout.activity_accounts, container, false);
		
													// from res

		home = (HomeActivity)getActivity();  // get activity from home 
		
		list = (ListView) view.findViewById(R.id.accounts_list);  // listview from accounts_list 

		
		list.setOnItemClickListener(new OnItemClickListener()   
		{
/**
 * @param parent
 * @param view
 * @param pos
 * @param id 
 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id)
			{
				// TODO Auto-generated method stub
				// get new intent from intent 
				Intent intent = new Intent(getActivity(), TransactionsActivity.class);
				intent.putExtra("User ID", userID);
				intent.putExtra("Account ID", (int)id);
				Log.i("Account ID: ", "" + id);
				startActivity(intent);    // start intent activity 
				
				
			}
		});
		
		
		// Toast.makeText(getApplicationContext(), "User: " + accountsUser,
		// Toast.LENGTH_LONG).show();
		Log.i("TAG", "User: " + accountsUser);

		accountsUser = home.accountsUser;
		
		userID = home.userID;

		updateData(DBHelper.ACCOUNT_ID);
		return view;

	}

/**
 * @param item
 * @return boolean
 * we use this method to select option intem 
 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		if (item.getItemId() == R.id.accounts_add)  
		{
			addAccount();      // add acount 
			

		}

		else if(item.getItemId() == R.id.accounts_settings) 
			//get item id from account_settings 
		{
			// get new intent from intent 
			Intent intent = new Intent(getActivity(), SettingsActivity.class);
			intent.putExtra("User ID", userID);
			startActivity(intent);   // start intent activity 
		} 
		
		else if (item.getItemId() == R.id.accounts_name_sort)   // name sort 
		{

			updateData(DBHelper.ACCOUNT_NAME);      // update account name 

		}

		else if (item.getItemId() == R.id.accounts_balance_sort)  // balance sort 
		{

			updateData(DBHelper.ACCOUNT_BALANCE);
		}

		else if (item.getItemId() == R.id.accounts_color_sort)  // color sort 
		{
			updateData(DBHelper.ACCOUNT_COLOR);
		}

		else if (item.getItemId() == R.id.accounts_bank_sort)  // bank sort 
		{
			updateData(DBHelper.ACCOUNT_BANK);
		}

		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}


	/**
	 * @return void 
	 */
	public void addAccount()
	{
		Intent in = new Intent(getActivity(), SplashScreen.class); // get new intent 
		PendingIntent pIntent = PendingIntent.getActivity(getActivity(), 0, in, 0);

		// get notificationcompat from notification 
		Notification noti = new NotificationCompat.Builder(getActivity())
				.setContentTitle("Verify Email").setContentText("Please verify your email address")
				.setContentIntent(pIntent)
				.setSmallIcon(R.drawable.greenbooklauncher).build();
		NotificationManager nManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
		nManager.notify(1, noti);

         
		dialogView = getActivity().getLayoutInflater()
				.inflate(R.layout.dialog_accounts, null);

		add_name = (TextView) dialogView         // add name 
				.findViewById(R.id.accounts_dialog_name);
		add_balance = (TextView) dialogView     // add balance 
				.findViewById(R.id.accounts_dialog_balance);
		add_custom = (AutoCompleteTextView) dialogView  // add custom 
				.findViewById(R.id.accounts_display_custom);
		choose_bank = (Spinner) dialogView        // choose bank 
				.findViewById(R.id.accounts_dialog_bank);
		choose_color = (Spinner) dialogView        // choose color 
				.findViewById(R.id.accounts_dialog_color);

		check_interst = (CheckBox) dialogView      // check interest 
				.findViewById(R.id.accounts_interst);

		add_interst = (TextView) dialogView       // add interest 
				.findViewById(R.id.accounts_interst_name);

		add_interst.setVisibility(View.GONE);

		check_interst.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			/**
			 * @param buttonview
			 * @return void
			 * @return boolean 
			 */
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked)
			{
				// TODO Auto-generated method stub

				if (!isChecked)    
				{
					add_interst.setVisibility(View.GONE);  // not visible 
				}
				else
					add_interst.setVisibility(View.VISIBLE);  // visible 

			}
		});

		add_custom.setVisibility(View.GONE);   // 

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.Banks, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		choose_bank.setAdapter(adapter);

		adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Colors,
				android.R.layout.simple_spinner_item);
		choose_color.setAdapter(adapter);

		choose_bank.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			/**
			 * @param adapter
			 * @param view
			 * @param pos
			 * @param id 
			 * @return void
			 * we call this method to select the item 
			 */
			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int pos, long id)
			{
				// TODO Auto-generated method stub

				if (pos == (choose_bank.getCount() - 1))
				{
					add_custom.setVisibility(View.VISIBLE);  // visible 
				}

				else
					add_custom.setVisibility(View.GONE);    // not visible 

				Log.i("Selected id: ", choose_bank.getCount() + " - " + pos);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		final AlertDialog ArtDialog = builder.setView(dialogView)
				.setPositiveButton("Add", new OnClickListener()
				{

					/**
					 * @param dialog
					 * @param which
					 * @return void 
					 * we use this method to click. 
					 */
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub

						// Toast.makeText(getApplicationContext(),
						// choose_bank.getSelectedItem().toString(),
						// Toast.LENGTH_LONG).show();
					}
				}).setTitle("Enter Details")   // set title 
				.setNegativeButton("Cancel", new OnClickListener()  // set navigation button 
				{

		/**
		 * @param dialog
		 * @param which
		 * @return void 
		 */
					@Override
					public void onClick(DialogInterface dialog, int which)
					{

						dialog.cancel();    // cancle dialog 
					}
				}).create();   // create 

		ArtDialog.setOnShowListener(new DialogInterface.OnShowListener()
		{

		/**
		 * @return void
		 * @param dialog 
		 */
			@Override
			public void onShow(DialogInterface dialog)
			{
				// TODO Auto-generated method stub

				Button plus = ArtDialog.getButton(AlertDialog.BUTTON_POSITIVE); // alert dialog 

				plus.setOnClickListener(new View.OnClickListener()
				{

					@SuppressLint("NewApi")
					@Override
					/**
					 * @param v
					 * @return void 
					 */
					public void onClick(View v)
					{
						// TODO Auto-generated method stub

						String addName = add_name.getText().toString(); // add name 
						String addBal = add_balance.getText().toString(); // and bal 
						String addInt = add_interst.getText().toString(); // add int 
						 
						
						final ContentValues caeser = new ContentValues();
						caeser.put(DBHelper.ACCOUNT_NAME, add_name.getText()
								.toString());
						caeser.put(DBHelper.ACCOUNT_BALANCE, add_balance
								.getText().toString());
						caeser.put(DBHelper.ACCOUNT_BANK, choose_bank
								.getSelectedItem().toString());
						caeser.put(DBHelper.ACCOUNT_COLOR, choose_color
								.getSelectedItem().toString());
						caeser.put(DBHelper.ACCOUNT_USER, userID);

						
						boolean exit_now = false;
						if(addName.equals(""))   // addname 
						{
							add_name.setBackground(getResources().getDrawable(R.drawable.error_edittext));
							exit_now = true;
						}
						
						if(addBal.equals(""))   // addbal 
						{
							add_balance.setBackground(getResources().getDrawable(R.drawable.error_edittext));
							exit_now = true;
						}
						
						if(check_interst.isChecked() && addInt.equals(""))
							{
								add_interst.setBackground(getResources().getDrawable(R.drawable.error_edittext));
								exit_now = true;    // exit now 
							}
						
						
						if(exit_now)
							{
							
							exit_now = false;   // dont exit 
							return;
							
							
							}
						
						
						if (check_interst.isChecked())   // check interest is checked 
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
									getActivity(),
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
									caeser);

							Log.d("cv: ", caeser.toString());
							Log.d("cv: ",
									caeser.getAsString(DBHelper.ACCOUNT_NAME));

							ArtDialog.dismiss();
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
	 * we use this method to update data 
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
			adap = new SimpleCursorAdapter(getActivity(),
					R.layout.listblock_accounts, csr, from, to)
			{

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent)
				{

					View v = super.getView(position, convertView, parent);

					String color = csr.getString(4);
					
                  // set different colors 
					int setColor;
					if (color.equals("Red"))
						setColor = getResources().getColor(
								android.R.color.holo_red_light);
					else if (color.equals("Green"))
						setColor = getResources().getColor(
								android.R.color.holo_green_dark);
					else if (color.equals("Blue"))
						setColor = getResources().getColor(
								android.R.color.holo_blue_light);
					else if (color.equals("Yellow"))
						setColor = getResources().getColor(
								android.R.color.holo_green_light);
					else if (color.equals("Cyan"))
						setColor = getResources().getColor(
								android.R.color.holo_blue_bright);
					else if (color.equals("Magenta"))
						setColor = getResources().getColor(
								android.R.color.holo_purple);
					else
						setColor = -1;

					if(setColor != -1)
						v.setBackgroundColor(setColor);
					
					
					csr.moveToNext();

					return v;              // return v 

				}

			};

			list.setAdapter(adap);    // set adapter 
		} 

	}

}
