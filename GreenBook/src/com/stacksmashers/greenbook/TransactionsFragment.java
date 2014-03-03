package com.stacksmashers.greenbook;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
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

public class TransactionsFragment extends BaseFragment
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState); // create
		// savedinstancestate

		View view = inflater.inflate(R.layout.activity_transactions, container,
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

	public void updateData()
	{
		if (isHome)
			totalBalance.setText(currency + getBalanceSum(userID));
		else
			totalBalance.setText(currency + getBalance(userID, accountID));

		
		
		Log.i("calling", "updated data");
		String condition = "";
		if(isHome)
			condition = DBHelper.TRANSACTION_USER
			+ " = '" + userID + "'";
			
		else
			condition = DBHelper.TRANSACTION_USER
			+ " = '" + userID + "' AND " + DBHelper.TRANSACTION_ACCOUNT + " ='" + accountID + "'";
		
		final Cursor caeser = sqldbase.query(DBHelper.TRANSACTION_TABLE,
				new String[] { DBHelper.TRANSACTION_ID,
						DBHelper.TRANSACTION_USER,
						DBHelper.TRANSACTION_ACCOUNT,
						DBHelper.TRANSACTION_ACCOUNT_NAME,
						DBHelper.TRANSACTION_DEPOSIT_SOURCE,
						DBHelper.TRANSACTION_WITHRAWAL_REASON,
						DBHelper.TRANSACTION_CATEGORY,
						DBHelper.TRANSACTION_VALUE }, condition, null, null, null, null);
		caeser.moveToFirst();
		if (caeser.getCount() == 0)
			return;

		SimpleCursorAdapter adap;
		String[] from = { DBHelper.TRANSACTION_VALUE };
		int[] to = { R.id.transaction_balancew };

		Log(DatabaseUtils.dumpCursorToString(caeser));

		adap = new SimpleCursorAdapter(getActivity(),
				R.layout.transactions_list_block, caeser, from, to)
		{

			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				View v = super.getView(position, convertView, parent);
				ImageView image = (ImageView) v
						.findViewById(R.id.transactions_total_icon);

				TextView title = (TextView) v
						.findViewById(R.id.transaction_title);
				TextView descriptio = (TextView) v
						.findViewById(R.id.transactions_total_balance);

				int balances = caeser.getInt(7);
				if (balances > 0)
				{

					title.setText("Deposit " + currency + balances);

					if (isHome)
						descriptio.setText(" For " + caeser.getString(4)
								+ ", From: " + caeser.getString(3));
					else
						descriptio.setText(" For " + caeser.getString(4));

					image.setImageDrawable(getResources().getDrawable(
							R.drawable.content_positive));

				}
				else
				{

					title.setText("Withraw " + currency + ((-1) * balances));
					if (isHome)
						descriptio.setText(" For " + caeser.getString(5)
								+ ", From: " + caeser.getString(3));
					else
						descriptio.setText(" For " + caeser.getString(5)
								+ ", in: " + caeser.getString(6));
					image.setImageDrawable(getResources().getDrawable(
							R.drawable.content_negative));
				}

				return v;

			}

		};

		list.setAdapter(adap);


	}

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

		Cursor caeser = sqldbase.query(DBHelper.ACCOUNT_TABLE, new String[] {
				DBHelper.ACCOUNT_ID, DBHelper.ACCOUNT_NAME },
				DBHelper.ACCOUNT_USER + " = '" + userID + "'", null, null,
				null, null);

		SimpleCursorAdapter adap = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_item, caeser, from, to);
		adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adap);

		whichSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked)
			{
				// TODO Auto-generated method stub

				if (isChecked)
				{
					sourceText.setHint("Spending Category");
					reasonText.setVisibility(View.VISIBLE);
				}
				else
				{
					sourceText.setHint("Money Source");
					reasonText.setVisibility(View.GONE);
				}

			}
		});

		final AlertDialog dialog = new AlertDialog.Builder(getActivity())
				.setTitle("Add Details").setView(view)
				.setPositiveButton("Enter", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						
					}
					
					
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						
						dialog.dismiss();
					}
				}).create();
		dialog.setOnShowListener(new OnShowListener()
		{

			@Override
			public void onShow(DialogInterface dyalog)
			{

				Button plus = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

				plus.setOnClickListener(new OnClickListener()
				{

					@SuppressLint("NewApi")
					@Override
					
					public void onClick(View v)
					{
						// TODO Auto-generated method stub

						Integer balance = Integer.parseInt(balanceText
								.getText().toString());
						String source = sourceText.getText().toString();
						String reason = reasonText.getText().toString();
						boolean checked = whichSwitch.isChecked();

						// TODO Auto-generated method stub
						ContentValues csr = new ContentValues();
						
						boolean exit_now = false;
						
						if(balance.equals(""))
						{
							balanceText.setBackground(getResources().getDrawable(R.drawable.error_edittext));
							exit_now = true;
						}
						
						if (source.equals(""))
						{
							sourceText.setBackground(getResources()
									.getDrawable(R.drawable.error_edittext));
							exit_now = true;
						}

						if(checked && reason.equals(""))
						{
							reasonText.setBackground(getResources().getDrawable(R.drawable.error_edittext));
							exit_now = true;
						}
						
						if(exit_now)
						{
							exit_now = false;
							return;
						}
						
						if (checked)
						{
							csr.put(DBHelper.TRANSACTION_CATEGORY, source);
							csr.put(DBHelper.TRANSACTION_WITHRAWAL_REASON,
									reason);
							balance = (-1) * balance;
						}
						else
							csr.put(DBHelper.TRANSACTION_DEPOSIT_SOURCE, source);

						csr.put(DBHelper.TRANSACTION_VALUE, balance);
						csr.put(DBHelper.TRANSACTION_USER, userID);

						Cursor caeser = sqldbase.query(
								DBHelper.ACCOUNT_TABLE,
								new String[] { DBHelper.ACCOUNT_NAME },
								DBHelper.ACCOUNT_ID + " = '"
										+ spinner.getSelectedItemId() + " '",
								null, null, null, null);

						if (caeser.getCount() != 0)
						{
							caeser.moveToFirst();
							accountName = caeser.getString(0);

						}

						csr.put(DBHelper.TRANSACTION_ACCOUNT_NAME, accountName);

						if (isHome)
							accountID = (int) spinner.getSelectedItemId();

						csr.put(DBHelper.TRANSACTION_ACCOUNT, accountID);

						fixBalance(userID, accountID, balance);

						sqldbase.insert(DBHelper.TRANSACTION_TABLE, null, csr);

						updateData();
						dialog.dismiss();

					}
				});

				// TODO Auto-generated method stub

			}
		});

		dialog.show();

	}

	public int getBalance(int userId, int accountID)
	{
		Cursor caeser = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_BALANCE },
				DBHelper.ACCOUNT_USER + " = '" + userID + "' AND "
						+ DBHelper.ACCOUNT_ID + " = '" + accountID + "'", null,
				null, null, null, null);

		int bal = 0;
		if (caeser.getCount() != 0)
		{
			caeser.moveToFirst();
			bal = caeser.getInt(0);

		}
		
		Log.i("Balance: ", ""+bal);
		return bal;

	}

	public int getBalanceSum(int userId)
	{
		Cursor caeser = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_ID }, DBHelper.ACCOUNT_USER
						+ " = '" + userID + "'", null, null, null, null, null);
		int sum = 0;
		Log(DatabaseUtils.dumpCursorToString(caeser));
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

		return sum;

	}

	public void fixBalance(int userID, int accountID, int change)
	{
		Cursor caeser = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_BALANCE },
				DBHelper.ACCOUNT_USER + " = '" + userID + "' AND "
						+ DBHelper.ACCOUNT_ID + " = '" + accountID + "'", null,
				null, null, null, null);

		int bal = getBalance(userID, accountID);
		bal += change;

		ContentValues cv = new ContentValues();
		cv.put(DBHelper.ACCOUNT_BALANCE, bal);

		sqldbase.update(DBHelper.ACCOUNT_TABLE, cv, DBHelper.ACCOUNT_USER
				+ " = '" + userID + "' AND " + DBHelper.ACCOUNT_ID + " = '"
				+ accountID + "'", null);

	}

}
