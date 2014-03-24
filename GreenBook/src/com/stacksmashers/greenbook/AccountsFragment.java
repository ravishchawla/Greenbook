package com.stacksmashers.greenbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

/**
 * 
 * this class calls all account activity within the application it is registry
 * of user's account.
 * 
 */
public class AccountsFragment extends BaseActivity
{
	private View dialogView;
	private ListView list;
	private TextView add_name;
	private TextView add_balance;
	private TextView add_custom;
	private Spinner choose_bank;
	private CheckBox check_interst;
	private TextView add_interst;
	private String accountsUser;
	private String userID;
	private ArrayAdapter listAdapter;

	private ParseQuery<ParseObject> duplicateQuery = ParseQuery
			.getQuery(ParseDriver.ACCOUNT_TABLE);

	private List<ParseObject> parseList;

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
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState); // create savedinstancestate

		userID = Vars.userObjectID;

		setContentView(R.layout.activity_accounts);

		list = (ListView) findViewById(R.id.accounts_list); // listview from
															// accounts_list

		duplicateQuery
				.whereEqualTo(ParseDriver.USER_ACCOUNT, Vars.userParseObj);

		// Toast.makeText(getApplicationContext(), "User: " + accountsUser,
		// Toast.LENGTH_LONG).show();
		Log.i("TAG", "User: " + accountsUser);

		listAdapter = new AccountsAdapter(getApplicationContext(),
				new ArrayList<ParseObject>());

		list.setAdapter(listAdapter); // set adapter
		query(ParseDriver.OBJECT_ID);

	}

	/**
	 * @param item
	 * @return boolean we use this method to select option intem
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		if (item.getItemId() == R.id.accounts_add)
		{
			addAccount(); // add acount

		}

		else if (item.getItemId() == R.id.accounts_settings)
		// get item id from account_settings
		{
			// get new intent from intent
			Intent intent = new Intent(getApplicationContext(),
					SettingsActivity.class);
			intent.putExtra("User ID", userID);
			startActivity(intent); // start intent activity
		}

		else if (item.getItemId() == R.id.accounts_name_sort) // name sort
		{

			Vars.ACCOUNT_SORT_TYPE = Vars.SORT_BY_NAME;
			reorder();

		}

		else if (item.getItemId() == R.id.accounts_balance_sort) // balance sort
		{

			Vars.ACCOUNT_SORT_TYPE = Vars.SORT_BY_BALANCE;
			reorder();

		}
		else if (item.getItemId() == R.id.accounts_bank_sort) // bank sort
		{
			Vars.ACCOUNT_SORT_TYPE = Vars.SORT_BY_BANK;
			reorder();

		}
		
		else if (item.getItemId() == R.id.accounts_date_sort)
		{
			Vars.ACCOUNT_SORT_TYPE = Vars.SORT_BY_DATE_CREATED;
			reorder();
		}

		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO Auto-generated method stub

		getMenuInflater().inflate(R.menu.menu_accounts, menu);

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * @return void
	 */
	public void addAccount()
	{

		dialogView = getLayoutInflater()
				.inflate(R.layout.dialog_accounts, null);

		add_name = (TextView) dialogView // add name
				.findViewById(R.id.accounts_dialog_name);
		add_balance = (TextView) dialogView // add balance
				.findViewById(R.id.accounts_dialog_balance);
		add_custom = (AutoCompleteTextView) dialogView // add custom
				.findViewById(R.id.accounts_display_custom);
		choose_bank = (Spinner) dialogView // choose bank
				.findViewById(R.id.accounts_dialog_bank);

		check_interst = (CheckBox) dialogView // check interest
				.findViewById(R.id.accounts_interst);

		add_interst = (TextView) dialogView // add interest
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
					add_interst.setVisibility(View.GONE); // not visible
				}
				else
					add_interst.setVisibility(View.VISIBLE); // visible

			}
		});

		add_custom.setVisibility(View.GONE); //

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.Banks, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		choose_bank.setAdapter(adapter);
		choose_bank.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			/**
			 * @param adapter
			 * @param view
			 * @param pos
			 * @param id
			 * @return void we call this method to select the item
			 */
			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int pos, long id)
			{
				// TODO Auto-generated method stub

				if (pos == (choose_bank.getCount() - 1))
				{
					add_custom.setVisibility(View.VISIBLE); // visible
				}

				else
					add_custom.setVisibility(View.GONE); // not visible

				Log.i("Selected id: ", choose_bank.getCount() + " - " + pos);

			}

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
					 * @return void we use this method to click.
					 */
					@Override
					public void onClick(DialogInterface dialog, int which)
					{

					}
				}).setTitle("Enter Details") // set title
				.setNegativeButton("Cancel", new OnClickListener() // set
						{
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{

								dialog.cancel(); // cancel dialog
							}
						}).create(); // create

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

				Button plus = ArtDialog.getButton(AlertDialog.BUTTON_POSITIVE); // alert
																				// dialog

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

						String addName = add_name.getText().toString(); // add
																		// name
						String addBal = add_balance.getText().toString(); // and
																			// bal
						String addInt = add_interst.getText().toString(); // add
																			// int

						final ParseObject account = new ParseObject(
								ParseDriver.ACCOUNT_TABLE);

						account.put(ParseDriver.ACCOUNT_NAME, add_name
								.getText().toString());

						account.put(ParseDriver.ACCOUNT_BALANCE, Double.parseDouble(add_balance
								.getText().toString()));

						account.put(ParseDriver.ACCOUNT_BANK, choose_bank
								.getSelectedItem().toString());

						account.put(ParseDriver.USER_ACCOUNT, Vars.userParseObj);

						boolean exit_now = false;
						if (addName.equals("")) // addname
						{
							add_name.setBackground(getResources().getDrawable(
									R.drawable.error_edittext));
							exit_now = true;
						}

						if (addBal.equals("")) // addbal
						{
							add_balance.setBackground(getResources()
									.getDrawable(R.drawable.error_edittext));
							exit_now = true;
						}

						if (check_interst.isChecked() && addInt.equals(""))
						{
							add_interst.setBackground(getResources()
									.getDrawable(R.drawable.error_edittext));
							exit_now = true; // exit now
						}

						if (exit_now)
						{

							exit_now = false; // dont exit
							return;

						}

						if (check_interst.isChecked()) // check interest is
														// checked
						{

							account.put(ParseDriver.ACCOUNT_INTEREST,
									add_interst.getText().toString());

						}
						else
							account.put(ParseDriver.ACCOUNT_INTEREST, "-1");

						duplicateQuery.whereEqualTo(ParseDriver.ACCOUNT_NAME,
								add_name.getText().toString());

						duplicateQuery
								.findInBackground(new FindCallback<ParseObject>()
								{

									@Override
									public void done(
											List<ParseObject> accountList,
											ParseException exe)
									{
										// TODO Auto-generated method stub

										if (accountList != null)
										{

											if (accountList.size() != 0)
												Toast.makeText(
														getApplicationContext(),
														"Account Alreaddy Exists\nPlease Rename the Account",
														Toast.LENGTH_LONG)
														.show();

											else
											{
												Log.i("starting save", "starting save");
												account.saveInBackground(new SaveCallback()
												{
													
													@Override
													public void done(ParseException arg0)
													{
														// TODO Auto-generated method stub
														if(arg0 == null)
														Log.i("starting save", "what the FUCK is going on here.");
														else
															Log.i("starting save", arg0.getMessage());
													}
												});
												ArtDialog.dismiss();
												query(ParseDriver.OBJECT_ID);
											}
										}

									}
								});

						// caeser.put(DBHelper.ACCOUNT, value)

					}

				});
			}
		});

		ArtDialog.show();

	}

	public void reorder()
	{
		Collections.sort(Vars.accountsParseList, new AccountsComparator());
		listAdapter.clear();
		listAdapter.addAll(Vars.accountsParseList);
	}
	
	public void query(String Sort)
	{
		ParseQuery<ParseObject> accountQuery = ParseQuery
				.getQuery(ParseDriver.ACCOUNT_TABLE);
		accountQuery.whereEqualTo(ParseDriver.USER_ACCOUNT, Vars.userParseObj);
		accountQuery.orderByAscending(Sort);

		Log.i("Accounts ", "inquery()");
		accountQuery.findInBackground(new FindCallback<ParseObject>()
		{

			@Override
			public void done(List<ParseObject> accountList, ParseException exe)
			{
				// TODO Auto-generated method stub

				if (accountList != null)
				{
					Vars.accountsParseList = accountList;
					listAdapter.clear();
					listAdapter.addAll(accountList);
					Log.i("Acconts ", "" + accountList.size());
				}

			}
		});

	}

}

class AccountsAdapter extends ArrayAdapter<ParseObject>
{

	private Context context;
	private List<ParseObject> parseList;

	public AccountsAdapter(Context _context, List<ParseObject> _parseList)
	{
		super(_context, R.layout.listblock_accounts, _parseList);
		context = _context;
		parseList = _parseList;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listblock_accounts, null);
		}

		ParseObject object = parseList.get(position);

		Log.i("accounts ", "filling up screen");
		((TextView) convertView.findViewById(R.id.account_display_name))
				.setText(object.getString(ParseDriver.ACCOUNT_NAME));
		((TextView) convertView.findViewById(R.id.account_display_bank))
				.setText(object.getString(ParseDriver.ACCOUNT_BANK));
		((TextView) convertView.findViewById(R.id.account_display_balance))
				.setText(object.getString(ParseDriver.ACCOUNT_BALANCE));

		return convertView;

	}
}

	class AccountsComparator implements Comparator<ParseObject>
	{

		public int compare(ParseObject one, ParseObject two)
		{
			switch (Vars.ACCOUNT_SORT_TYPE)
			{
				case Vars.SORT_BY_NAME:
					return one.getString(ParseDriver.ACCOUNT_NAME).compareTo(
							two.getString(ParseDriver.ACCOUNT_NAME));

				case Vars.SORT_BY_BANK:

					return one.getString(ParseDriver.ACCOUNT_BANK).compareTo(
							two.getString(ParseDriver.ACCOUNT_BANK));

				case Vars.SORT_BY_BALANCE:

					return (int) (one.getDouble(ParseDriver.ACCOUNT_NAME) - two
							.getDouble(ParseDriver.ACCOUNT_NAME));

				case Vars.SORT_BY_DATE_CREATED:
					return one.getCreatedAt().compareTo(two.getCreatedAt());

				default:
					return 0;

			}
		}

	}

