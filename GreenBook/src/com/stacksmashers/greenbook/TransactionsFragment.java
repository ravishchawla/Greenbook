package com.stacksmashers.greenbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.ads.AdRequest.ErrorCode;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class TransactionsFragment extends BaseFragment
{

	private ListView list;
	private TransactionsActivity trans;
	private TextView totalBalance;

	ArrayAdapter<ParseObject> transactionAdapter;

	private AdView adView;
	
	
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

		View view = inflater.inflate(R.layout.fragment_transactions, container,
				false);// calling activity register

		list = (ListView) view.findViewById(R.id.transactions_list);

		((TransactionsActivity) getActivity()).setTransactionTag(getTag());
		trans = (TransactionsActivity) getActivity();

		totalBalance = (TextView) view.findViewById(R.id.balance_total);
		
		adView = (AdView)view.findViewById(R.id.adViewTransactions);
		
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(Vars.HASHED_DEVICE_ID).build();
		
		adView.loadAd(adRequest);
		
		adView.setAdListener(new AdListener()
		{
			@Override
			public void onAdOpened()
			{
				Log.d("adopen", "ad opened");
			}
			
			@Override
			public void onAdFailedToLoad(int error)
			{
				switch(error){
					case AdRequest.ERROR_CODE_INTERNAL_ERROR:
						Log.d("adfailed", "error code internal error");
						break;
					case AdRequest.ERROR_CODE_INVALID_REQUEST:
						Log.d("adfailed", "error code invalid request");
						break;
					case AdRequest.ERROR_CODE_NETWORK_ERROR:
						Log.d("adfailed", "error code network error");
						break;
					case AdRequest.ERROR_CODE_NO_FILL:
						Log.d("adfailed", "error code no fill");
						break;
					
					
				}

				
			}
			
			@Override
			public void onAdLoaded()
			{
				Log.d("adload", "ad loaded properly");
			}
				
			
		});
		
		transactionAdapter = new TransactionAdapter(getActivity(),
				new ArrayList<ParseObject>());
		list.setAdapter(transactionAdapter);

		return view;
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

	//	((TextView)view.findViewById(R.id.transactions_currency)).setText(Vars.currencyParseObj.getString(ParseDriver.CURRENCY_SYMBOL));
		
		final TextView balanceText = (TextView) view
				.findViewById(R.id.transaction_add_value);
		final TextView sourceText = (TextView) view
				.findViewById(R.id.transaction_add_source);
		final TextView reasonText = (TextView) view
				.findViewById(R.id.transaction_add_reasno);
		final Spinner spinner = (Spinner) view
				.findViewById(R.id.transactions_account_select);

		spinner.setVisibility(View.GONE);

		String[] from = { DBHelper.ACCOUNT_NAME };
		int[] to = { android.R.id.text1 };

		ArrayAdapter<ParseObject> spinnerAdap = new SpinnerAdapter(
				getActivity(), new ArrayList<ParseObject>());
		spinnerAdap
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinnerAdap);

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

				Button plus = dialog.getButton(DialogInterface.BUTTON_POSITIVE); // get
																				// dialog
																				// button

				plus.setOnClickListener(new OnClickListener() // set on click
																// listener
				{

					private String accountName;

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

						if (exit_now)
						{
							exit_now = false;
							return;
						}

						Double balance = Double.parseDouble(balanceText
								.getText().toString());

						accountName = Vars.accountsParseList.get(
								(int) spinner.getSelectedItemId()).getString(
								ParseDriver.ACCOUNT_NAME);

						Date date = new Date();
						Log.i("date == null", "" + (date == null));

						String string = Vars.dateFormat.format(date).toString();
						Log.i("date string", string);

						ParseObject transaction = new ParseObject(
								ParseDriver.TRANSACTION_TABLE);

						transaction.put(ParseDriver.TRANSACTION_ACCOUNT_NAME,
								accountName);
						transaction.put(ParseDriver.ACCOUNT_TRANSACTION,
								Vars.accountParseObj);
						transaction.put(ParseDriver.USER_TRANSACTION, Vars.userParseObj);
						transaction.put(ParseDriver.TRANSACTION_CATEGORY,
								source);
						if (checked)
						{
							transaction.put(
									ParseDriver.TRANSACTION_WITHRAWAL_REASON,
									reason);

							

							transaction.put(ParseDriver.TRANSACTION_VALUE,
									((-1) * balance));
							
							Vars.accountParseObj.put(ParseDriver.ACCOUNT_BALANCE, Vars.accountParseObj.getDouble(ParseDriver.ACCOUNT_BALANCE) - balance);
							
						}
						else
						{

							transaction.put(ParseDriver.TRANSACTION_VALUE,
									balance);
							
							Vars.accountParseObj.put(ParseDriver.ACCOUNT_BALANCE, Vars.accountParseObj.getDouble(ParseDriver.ACCOUNT_BALANCE) + balance);
						}
						
						Vars.accountParseObj.saveInBackground();
						transaction.saveInBackground(new SaveCallback()
						{

							@Override
							public void done(ParseException arg0)
							{
								if (arg0 == null)
								{
									((TransactionsActivity) getActivity())
											.queryTransactions();
									dialog.dismiss(); // dismiss dialog
								}
							}
						});

					}
				});

				// TODO Auto-generated method stub

			}
		});

		dialog.show(); // show dialog

	}

	/**
	 * @param userId
	 * @return int we use this method to getblanace sum
	 */
	public double getBalanceSum()
	{

		double sum = 0;
		for (ParseObject obj : Vars.accountsParseList)
		{
			sum += obj.getDouble(ParseDriver.TRANSACTION_VALUE);
		}
		return sum; // return sum

	}

	public void updateTotal()
	{
		if(Vars.accountParseObj != null)
		{
			
			
			
			
			totalBalance.setText(""+Vars.decimalFormat.format(Vars.accountParseObj.getDouble(ParseDriver.ACCOUNT_BALANCE)));
		
	
	
		}
	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		
		if(adView != null)
			adView.resume();
	
	}

	@Override
	public void onPause()
	{
		super.onPause();

		if(adView != null)
			adView.pause();
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		if(adView != null)
			adView.destroy();
	}
	
	class TransactionAdapter extends ArrayAdapter<ParseObject>
	{
		private Context context;
		private List<ParseObject> parseList;

		public TransactionAdapter(Context _context, List<ParseObject> _parseList)
		{
			super(_context, R.layout.listblock_transactions, _parseList);
			context = _context;
			parseList = _parseList;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub

			if (convertView == null)
			{
				convertView = LayoutInflater.from(context).inflate(
						R.layout.listblock_transactions, null);
			}

			ParseObject object = parseList.get(position);
			double balances = object.getDouble(ParseDriver.TRANSACTION_VALUE);
			String date = Vars.longDateFormat.format(object.getCreatedAt());
			((TextView) convertView
					.findViewById(R.id.transaction_block_description))
					.setText(date);
			((TextView) convertView
					.findViewById(R.id.transaction_block_balance))
					.setText(Vars.DEF_CURRENCY_SYMBOL + balances);
			if (balances > 0) // balances less than 0
			{

				((TextView) convertView
						.findViewById(R.id.transaction_block_title))
						.setText(object
								.getString(ParseDriver.TRANSACTION_CATEGORY));

				((ImageView) convertView
						.findViewById(R.id.transaction_block_icon))
						.setImageDrawable(getResources().getDrawable(
								R.drawable.content_positive));

			}
			else
			{

				((TextView) convertView
						.findViewById(R.id.transaction_block_title))
						.setText(object
								.getString(ParseDriver.TRANSACTION_CATEGORY));

				((ImageView) convertView
						.findViewById(R.id.transaction_block_icon))
						.setImageDrawable(getResources().getDrawable(
								R.drawable.content_negative));

			}

			return convertView;

		}

	}

	class SpinnerAdapter extends ArrayAdapter
	{
		private Context context;
		private List<ParseObject> parseList;

		public SpinnerAdapter(Context _context, List<ParseObject> _parseList)
		{
			super(_context, android.R.layout.simple_spinner_item, _parseList);
			context = _context;
			parseList = _parseList;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub

			if (convertView == null)
			{
				convertView = LayoutInflater.from(context).inflate(
						R.layout.listblock_accounts, null);
			}

			ParseObject object = parseList.get(position);

			return convertView;

		}

	}

}
