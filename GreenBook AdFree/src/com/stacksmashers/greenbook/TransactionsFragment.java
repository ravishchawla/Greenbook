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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * A fragment class that handles all code for showing and deeling with
 * transactions
 * 
 * @author Ravish Chawla
 */
public class TransactionsFragment extends BaseFragment
{

	/** The list. */
	private ListView list;

	/** The total balance. */
	private TextView totalBalance;

	/** The transaction adapter. */
	ArrayAdapter<ParseObject> transactionAdapter;

	/**
	 * Instantiates a new transactions fragment.
	 */
	public TransactionsFragment()
	{
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{
	}

	/**
	 * called automatically when the fragment is first created. inflates the xml
	 * layout, finds all views by id, and sets listeners and adapters
	 * 
	 * @param inflater
	 *            - the inflater from base context
	 * @param container
	 *            - a group for all views in this layout
	 * @param savedInstanceState
	 *            - saved state from a previous instance
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_transactions, container,
				false);
		list = (ListView) view.findViewById(R.id.transactions_list);
		((TransactionsActivity) getActivity()).setTransactionTag(getTag());
		totalBalance = (TextView) view.findViewById(R.id.balance_total);

		transactionAdapter = new TransactionAdapter(getActivity(),
				new ArrayList<ParseObject>());
		list.setAdapter(transactionAdapter);
		updateTotal();

		return view;
	}

	/**
	 * called to handle adding a new transaction. It creates a new dialog that
	 * asks for user input, and then reads that input, and syncs it into the
	 * database.
	 * 
	 * @return void
	 */
	public void addTransaction()
	{

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_transactions, null);
		Log.i("class widget ", view.findViewById(R.id.transaction_switch)
				.getClass().getName());
		final Switch whichSwitch = (Switch) view
				.findViewById(R.id.transaction_switch);
		final TextView currency = (TextView) view
				.findViewById(R.id.transactions_currency);
		currency.setText(Vars.DEF_CURRENCY_SYMBOL);
		final TextView balanceText = (TextView) view
				.findViewById(R.id.transaction_add_value);
		final TextView sourceText = (TextView) view
				.findViewById(R.id.transaction_add_source);
		final TextView reasonText = (TextView) view
				.findViewById(R.id.transaction_add_reasno);
		final Spinner spinner = (Spinner) view
				.findViewById(R.id.transactions_account_select);
		spinner.setVisibility(View.GONE);

		
		ArrayAdapter<ParseObject> spinnerAdap = new SpinnerAdapter(
				getActivity(), new ArrayList<ParseObject>());
		spinnerAdap
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinnerAdap);
		whichSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			/**
			 * called automatically when the state of a checkbox changes from
			 * being clicked to being not
			 * 
			 * @param buttonView
			 *            - a reference to the checkbox
			 * @param isChecked
			 *            - the current state of the checkbox
			 * @return void
			 */
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked)
			{
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
				.setTitle("Add Details")
				.setView(view)
				.setPositiveButton("Enter",
						new DialogInterface.OnClickListener()
						{
							/**
							 * automatically called when the positive button on
							 * the dialog is clicked
							 * 
							 * @param dialog
							 *            - a reference to the currently visible
							 *            dialog
							 * @param which
							 *            - which button is clicked, (position)
							 * @return void
							 */
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener()
						// set navigate button
						{
							/**
							 * automatically called when the negative button on
							 * the dialog is clicked
							 * 
							 * @param dialog
							 *            - a reference to the currently visible
							 *            dialog
							 * @param which
							 *            - which button just got clickekd, by
							 *            position
							 * @return void
							 * 
							 */
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								dialog.dismiss();
							}
						}).create();
		dialog.setOnShowListener(new OnShowListener()
		{
			/**
			 * called automatically when the dialog is first shown
			 * 
			 * @param dyalog
			 *            - a reference to the dialog shown
			 * 
			 */
			@Override
			public void onShow(DialogInterface dyalog)
			{

				Button plus = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
				plus.setOnClickListener(new OnClickListener()
				{
					private String accountName;

					@SuppressLint("NewApi")
					@Override
					/**
					 * automatically called when the positive button on the dialog is clicked
					 * @param dialog - a reference to the currently visible dialog
					 * @return void
					 */
					public void onClick(View v)
					{
						String balanceStr = balanceText.getText().toString();
						String source = sourceText.getText().toString();
						String reason = reasonText.getText().toString();
						boolean checked = whichSwitch.isChecked();
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
						transaction.put(ParseDriver.USER_TRANSACTION,
								Vars.userParseObj);
						transaction.put(ParseDriver.TRANSACTION_CATEGORY,
								source);
						if (checked)
						{
							transaction.put(
									ParseDriver.TRANSACTION_WITHRAWAL_REASON,
									reason);

							transaction.put(ParseDriver.TRANSACTION_VALUE,
									((-1) * balance));

							Vars.accountParseObj
									.put(ParseDriver.ACCOUNT_BALANCE,
											Vars.accountParseObj
													.getDouble(ParseDriver.ACCOUNT_BALANCE)
													- balance);
						}
						else
						{
							transaction.put(ParseDriver.TRANSACTION_VALUE,
									balance);
							Vars.accountParseObj
									.put(ParseDriver.ACCOUNT_BALANCE,
											Vars.accountParseObj
													.getDouble(ParseDriver.ACCOUNT_BALANCE)
													+ balance);
						}
						Vars.accountParseObj.saveInBackground();
						transaction.saveInBackground(new SaveCallback()
						{
							@Override
							public void done(ParseException arg0)
							{
								if (arg0 == null)
								{

									TransactionsActivity activity = ((TransactionsActivity) getActivity());
									activity.queryTransactions(true);

									dialog.dismiss(); // dismiss dialog
								}
							}
						});

					}
				});
			}
		});
		dialog.show();
	}

	/**
	 * Gets the latest balance by adding up all sums
	 * 
	 * @return int - the balance
	 */
	public double getBalanceSum()
	{
		double sum = 0;
		for (ParseObject obj : Vars.accountsParseList)
		{
			sum += obj.getDouble(ParseDriver.TRANSACTION_VALUE);
		}
		return sum;
	}

	/**
	 * updates the totalBalance textView with the latest balance
	 * 
	 * @return void
	 */
	public void updateTotal()
	{
		if (Vars.accountParseObj != null)
		{
			totalBalance.setText(Vars.DEF_CURRENCY_SYMBOL
					+ Vars.decimalFormat.format(Vars.accountParseObj
							.getDouble(ParseDriver.ACCOUNT_BALANCE)));
		}

		else
			totalBalance.setText(Vars.DEF_CURRENCY_SYMBOL);
	}

	/**
	 * automatically called when the activity goes into a resumed state from a
	 * paused
	 * 
	 * @return void
	 */
	@Override
	public void onResume()
	{
		super.onResume();


		Log.d("transac", "transactionfragment.onresume()");
	}

	/**
	 * called automatcialy when the activity is paused
	 * 
	 * @return void
	 */
	@Override
	public void onPause()
	{
		super.onPause();


		Log.d("transac", "transactionfragment.onpause()");

	}

	/**
	 * called automatically when the activity is destroyed
	 */
	@Override
	public void onDestroy()
	{
		super.onDestroy();


		Log.d("transac", "transactionfragment.ondestroy()");
	}

	/**
	 * a custom array adapter, that is ued to update lists with parse objec
	 * data.
	 * 
	 * @author Ravish Chawla
	 */
	class TransactionAdapter extends ArrayAdapter<ParseObject>
	{

		/** The context. */
		private Context context;

		/** The parse list. */
		private List<ParseObject> parseList;

		/**
		 * Instantiates a new transaction adapter.
		 * 
		 * @param _context
		 *            the _context
		 * @param _parseList
		 *            the _parse list
		 */
		public TransactionAdapter(Context _context, List<ParseObject> _parseList)
		{
			super(_context, R.layout.listblock_transactions, _parseList);
			context = _context;
			parseList = _parseList;
		}

		/**
		 * method called internally to get a view by position. overridden here
		 * to update view with parse data before it it read.
		 * 
		 * @param convertView
		 *            - a rerence to the view being read
		 * @param parent
		 *            - a group in which the view belongs
		 * @return View - the modified view
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
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
			if (balances > 0)
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
	/**
	 * A custom array adapter for spinners, which 
	 * sets a view based on datetime objects. 
	 * 
	 * @author Ravish Chawla
	 */
	class SpinnerAdapter extends ArrayAdapter<ParseObject>
	{

		/** The context. */
		private Context context;

		/**
		 * Instantiates a new spinner adapter.
		 * 
		 * @param _context
		 *            the _context
		 * @param _parseList
		 *            the _parse list
		 */
		public SpinnerAdapter(Context _context, List<ParseObject> _parseList)
		{
			super(_context, android.R.layout.simple_spinner_item, _parseList);
			context = _context;
		}

		/**
		 * method called interanlly to return a view based on position. overridden here to modify the view before it is read. 
		 * @param position - the position of the view
		 * @param convertView - a reference to the view
		 * @param parent - the group the parent belongs to
		 * @return View - the modified view
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = LayoutInflater.from(context).inflate(
						R.layout.listblock_accounts, null);
			}

			

			return convertView;
		}
	}
}