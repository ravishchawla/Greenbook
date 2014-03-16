package com.stacksmashers.greenbook;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class TransactionsFragment extends BaseFragment implements
		DataFragmentInterface
{

	private ListView list;
	private HomeActivity home;
	private TransactionsActivity trans;
	private int userID;
	private String currency;
	private boolean isHome = false;
	private int accountID;
	private String accountName;
	private TextView totalBalance;
	private Cursor dataCursor;
	private SimpleCursorAdapter listAdapter;
	
	public TransactionsFragment()
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
	public void refresh(int mode)
	{
		// TODO Auto-generated method stub

		updateData();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState); // create
		// savedinstancestate

		View view = inflater.inflate(R.layout.fragment_transactions, container,
				false);// calling activity register


		
		list = (ListView) view.findViewById(R.id.transactions_list);

		if (getActivity() instanceof HomeActivity)
		{

			home = (HomeActivity) getActivity();
			userID = home.userID;
			currency = home.currency;
			isHome = true;

		}
		else
		{
			((TransactionsActivity)getActivity()).setTransactionTag(getTag());
			trans = (TransactionsActivity) getActivity();
			userID = trans.userID;
			currency = trans.currency;
			isHome = false;
			accountID = trans.accountID;
			accountName = trans.accountName;
		}

		totalBalance = (TextView) view
				.findViewById(R.id.transactions_total_balance);

		updateData();

		return view;
	}

	
	public void query(int accountID, int userID)
	{
		dataCursor = DBDriver.GET_ACCOUNT_TRANSACTIONS(accountID, userID);
		dataCursor.moveToFirst();
		
		Log.i("Trans", DatabaseUtils.dumpCursorToString(dataCursor));
	}
	
	public void refreshList()
	{
		listAdapter.changeCursor(dataCursor);
		listAdapter.notifyDataSetChanged();
	}
	
	/**
	 * @return void we use this method to updatedata
	 */
	public void updateData()
	{
		if (isHome)
			totalBalance.setText(currency + getBalanceSum(userID));
		else
			totalBalance.setText(currency + getBalance(userID, accountID));

		Log.i("calling", "updated data");
		
		query(accountID, userID);
		
		if (isHome)
			dataCursor = DBDriver.GET_ACCOUNT_TRANSACTIONS(0, userID);
		else
			dataCursor = DBDriver.GET_ACCOUNT_TRANSACTIONS(accountID, userID);

		
		if (dataCursor.getCount() == 0)
			return;

		
		String[] from = { DBHelper.TRANSACTION_VALUE };
		int[] to = { R.id.transaction_balancew };

		listAdapter = new SimpleCursorAdapter(getActivity(), // call new
														// simplecursoradapter
														// from adap
				R.layout.transactions_list_block, dataCursor, from, to)
		{


			/**
			 * @param position
			 * @param convertview
			 * @param parent
			 *            this method displays the data to data set
			 */
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				Log.i("Trans", "Refreshing Data LIst " + position);

				View v = super.getView(position, convertView, parent);
				ImageView image = (ImageView) v
						.findViewById(R.id.transactions_total_icon);
				

				TextView title = (TextView) v
						.findViewById(R.id.transaction_title);
				TextView descriptio = (TextView) v
						.findViewById(R.id.transactions_total_balance);

				int balances = dataCursor.getInt(8);
				if (balances > 0) // balances less than 0
				{

					title.setText("Deposit " + currency + balances);

					descriptio.setText(" For " + dataCursor.getString(4) + " on: "
							+ dataCursor.getString(7));

					image.setImageDrawable(getResources().getDrawable(
							R.drawable.content_positive));

				}
				else
				{

					title.setText("Withraw " + currency + ((-1) * balances));

					descriptio.setText(" For " + dataCursor.getString(6) + " on: "
							+ dataCursor.getString(7));

					image.setImageDrawable(getResources().getDrawable(
							R.drawable.content_negative));
				}

				return v;

			}

		};

		list.setAdapter(listAdapter);

	}

	/**
	 * return void we use this method to add transction
	 */
	public void addTransaction()
	{

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_transactions, null);

		Log.i("class widget ", view.findViewById(R.id.transaction_switch)
				.getClass().getName());
		final Switch whichSwitch = (Switch) view
				.findViewById(R.id.transaction_switch);

		final TextView balanceText = (TextView) view
				.findViewById(R.id.transaction_add_value);
		final TextView sourceText = (TextView) view
				.findViewById(R.id.transaction_add_source);
		final TextView reasonText = (TextView) view
				.findViewById(R.id.transaction_add_reasno);
		final Spinner spinner = (Spinner) view
				.findViewById(R.id.transactions_account_select);

		if (!isHome)
			spinner.setVisibility(View.GONE);
		else
			spinner.setVisibility(View.VISIBLE);

		String[] from = { DBHelper.ACCOUNT_NAME };
		int[] to = { android.R.id.text1 };

		final Cursor caeser = DBDriver.GET_ACCOUNT_INFO_FOR_USER(userID);

		SimpleCursorAdapter adap = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_item, caeser, from, to);
		adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adap);

		whichSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener()
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

				if (isChecked)
				{
					sourceText.setHint("Spending Category"); // set hint text
					reasonText.setVisibility(View.VISIBLE);
				}
				else
				{
					sourceText.setHint("Money Source"); // set hint text
					reasonText.setVisibility(View.GONE);
				}

			}
		});

		final AlertDialog dialog = new AlertDialog.Builder(getActivity())
				.setTitle("Add Details")
				.setView(view)
				.setPositiveButton("Enter",
						new DialogInterface.OnClickListener()
						{

							/**
							 * @param dialog
							 * @param which
							 * @return void
							 */
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								// TODO Auto-generated method stub

							}

						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener()
						// set navigate button
						{
							/**
							 * @param dialog
							 * @param which
							 * @return void
							 */
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								// TODO Auto-generated method stub

								dialog.dismiss();
							}
						}).create();
		dialog.setOnShowListener(new OnShowListener()
		{
			/**
			 * @param dyalog
			 * @return void we use this method to show
			 */
			@Override
			public void onShow(DialogInterface dyalog)
			{

				Button plus = dialog.getButton(AlertDialog.BUTTON_POSITIVE); // get
																				// dialog
																				// button

				plus.setOnClickListener(new OnClickListener() // set on click
																// listener
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

						String balanceStr = balanceText.getText().toString();
						String source = sourceText.getText().toString();
						// string souce from to string
						String reason = reasonText.getText().toString();
						boolean checked = whichSwitch.isChecked();

						// TODO Auto-generated method stub
						

						boolean exit_now = false;

						if (balanceStr.equals("") && !balanceStr.matches("\\d"))
						{
							balanceText.setBackground(getResources()
									.getDrawable(R.drawable.error_edittext));
							exit_now = true; // exit now
						}

						if (source.equals(""))
						{
							sourceText.setBackground(getResources()
									.getDrawable(R.drawable.error_edittext));
							exit_now = true; // exit now
						}

						if (checked && reason.equals(""))
						{
							reasonText.setBackground(getResources()
									.getDrawable(R.drawable.error_edittext));
							exit_now = true; // exit now
						}

						if (exit_now)
						{
							exit_now = false;
							return;
						}

						Integer balance = Integer.parseInt(balanceText
								.getText().toString());

						Cursor caeser = DBDriver.GET_ACCOUNT_FROM_ID(spinner
								.getSelectedItemId());

						if (caeser.getCount() != 0)
						{
							caeser.moveToFirst();
							accountName = caeser.getString(0);

						}

						if (isHome)
							accountID = (int) spinner.getSelectedItemId();

						Date date = new Date();
						Log.i("date == null", "" + (date == null));

						String string = Utility.dateFormat.format(date)
								.toString();
						Log.i("date string", string);

						if (checked)
						{

							DBDriver.INSERT_WITHRAWAL(source, reason, balance,
									userID, accountName, accountID);

						}
						else
							DBDriver.INSERT_DEPOSIT(source, balance, userID,
									accountName, accountID);

						fixBalance(userID, accountID, balance);

						updateData(); // update data
						dialog.dismiss(); // dismiss dialog

					}
				});

				// TODO Auto-generated method stub

			}
		});

		dialog.show(); // show dialog

	}

	/**
	 * @parm userid
	 * @param accountID
	 * @return int
	 * 
	 */
	public int getBalance(int userId, int accountID)
	{
		Cursor caeser = DBDriver.GET_ACCOUNT_BALANCE(userId, accountID);

		int bal = 0;
		if (caeser.getCount() != 0)
		{
			caeser.moveToFirst();
			bal = caeser.getInt(0);

		}

		Log.i("Balance: ", "" + bal);
		return bal;

	}

	/**
	 * @param userid
	 * @return int we use this method to getblanace sum
	 */
	public int getBalanceSum(int userId)
	{
		
		Cursor caeser = DBDriver.GET_ACCOUNTS_IDS_FOR_USER(userID);
		
		int sum = 0;

		if (caeser.getCount() != 0)
		{
			// caeser.moveToFirst();

			while (caeser.moveToNext())
			{
				int bal = caeser.getInt(0);
				Log("Balance: " + bal);
				sum += getBalance(userId, caeser.getInt(0));
				Log("sum: " + sum);

			}
		}

		return sum; // return sum

	}

	/**
	 * @param userID
	 * @param account
	 *            ID
	 * @param change
	 * @eturn void we use this method to fix the balance
	 * 
	 */
	public void fixBalance(int userID, int accountID, int change)
	{

		Cursor caeser = DBDriver.GET_ACCOUNT_BALANCE(userID, accountID);
		int bal = getBalance(userID, accountID);
		bal += change;

		
												
		

		

	}

}
