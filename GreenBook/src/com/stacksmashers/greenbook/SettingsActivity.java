package com.stacksmashers.greenbook;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.RefreshCallback;
import com.parse.SaveCallback;

/**
 * This class controls and coordinates all actions related to User settings.
 * Instead of seperating each individual setting into its own class, all
 * settings are handled here.
 * 
 * @author Ravish Chawla
 */

public class SettingsActivity extends BaseActivity
{
	/** The user_id. */
	private int user_id;

	/** The list. */
	private ListView list;

	/** The setting_names. */
	private static String[] setting_names = new String[4];

	/** The setting pos. */
	private static int settingPos;

	/** The exchange values. */
	private ArrayList<Double> exchangeValues = new ArrayList<Double>();

	/** The backup exchange values. */
	private ArrayList<Double> backupExchangeValues = new ArrayList<Double>();

	/** The currency symbols. */
	private ArrayList<String> currencySymbols = new ArrayList<String>();

	/** The tickers. */
	private ArrayList<String> tickers = new ArrayList<String>();

	/** The progress bar. */
	private ProgressBar progressBar;

	/** The verification. */
	private Setting verification = new Setting("Verify your Email",
			"Email not verified. You can also resend your code",
			R.drawable.ic_launcher);

	/** The region. */
	private Setting region = new Setting("Regional Settings",
			"Select your default Currency", R.drawable.ic_launcher);

	
	/** The tutorial */
	private Setting tutorial = new Setting("Tutorial", "View Tutorial", R.drawable.ic_launcher);
	
	/** The about. */
	private Setting about = new Setting("About the App",
			"Meet the developers and contact them for support",
			R.drawable.ic_launcher);

	/** The settings. */
	private Setting[] settings = { verification, region, tutorial, about };

	/** The settings adapter. */
	private CustomArrayAdapter settingsAdapter;

	/** The Constant SETTING_VERIFY_EMAIL. */
	private final static int SETTING_VERIFY_EMAIL = 0;

	/** The Constant SETTING_REGION_SETTING. */
	private final static int SETTING_REGION_SETTING = 1;
		
	/** The Constant SETTING_TUTORIAL. */
	private final static int SETTING_TUTORIAL = 2;
	
	/** The Constant SETTING_ABOUT. */
	private final static int SETTING_ABOUT = 3;

	/**
	 * Instantiates a new settings activity.
	 */
	public SettingsActivity()
	{
	}

	/**
	 * An auto generated main method
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{
	}

	/**
	 * automatically called when the activity is first created. Inflates the xml
	 * layout, finds views in this container, and adds listeners and adapters.
	 * 
	 * @param savedInstanceState
	 *            the saved instance state
	 * @return void this method create savedinstancestate
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		Bundle extras = getIntent().getExtras();
		user_id = extras.getInt("User ID");
		settingPos = 0;
		if (Vars.userParseObj.getString(ParseDriver.USER_TYPE).equals("auth"))
			verification.description = "Email alreaddy Verified.";

		list = (ListView) findViewById(R.id.settings_list);
		settingsAdapter = new CustomArrayAdapter(this, setting_names);
		list.setAdapter(settingsAdapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			/**
			 * @param parent
			 * @param view
			 * @param position
			 * @param id
			 * @return void
			 * 
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Log.i("Setting", "pressed pos: "
						+ (position == SETTING_REGION_SETTING));

				if (position == SETTING_VERIFY_EMAIL)
				{
					verify(user_id);
				}
				if (position == SETTING_REGION_SETTING)
				{
					Log.i("setting", "calling changeregion)(");
					changeRegion();
				}
				
				if (position == SETTING_TUTORIAL)
				{
					Log.i("setting", "calling tutorial");
					viewTutorial();
				}
				
				if(position == SETTING_ABOUT)
				{
					Log.i("setting", "starting about");
					Intent about = new Intent(getApplicationContext(), About.class);
					startActivity(about);
				}
			}
		});

		defineCurrencies();
	}

	/**
	 * A helper for the adapter to insert a new setting object
	 * 
	 * @param name
	 *            - the name of the setting being added
	 * @return void
	 */
	private static void insertSetting(String name)
	{
		setting_names[settingPos++] = name;
	}

	/**
	 * Left empty for possible future functionality
	 * 
	 * @return void
	 */
	private void defineCurrencies()
	{
	}

	/**
	 * A custom array adapter to inflate the individual list items in this
	 * activity.
	 * 
	 */
	private class CustomArrayAdapter extends ArrayAdapter<String>
	{
		/** The inflater. */
		private LayoutInflater inflater;

		/**
		 * Instantiates a new custom array adapter.
		 * 
		 * @param context
		 *            the context of the application
		 * @param values
		 *            the values to be added to the application
		 */
		public CustomArrayAdapter(Context context, String[] values)
		{
			super(context, R.layout.nice_list_layout, values);
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		/**
		 * used by the adapter to get view for each individual item. overriden
		 * to use parsed data
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View row = inflater.inflate(R.layout.nice_list_layout, parent,
					false);
			TextView text = (TextView) row.findViewById(R.id.settings_title);
			TextView description = (TextView) row
					.findViewById(R.id.settings_description);
			ImageView image = (ImageView) row.findViewById(R.id.settings_icon);
			text.setText(settings[position].name);
			description.setText(settings[position].description);
			image.setImageResource(settings[position].icon);

			return row;
		}
	}

	/**
	 * Handles a situation when the user wants to verify their email.
	 * 
	 * @param user_id
	 *            the curent user id
	 * @return void
	 */
	public void verify(final int user_id)
	{
		final ParseObject user = Vars.userParseObj;
		final String code = user.getString(ParseDriver.USER_TYPE);
		if (!code.equals("auth") && !code.equals("admin"))
		{
			View view = getLayoutInflater().inflate(R.layout.settings_verify,
					null);
			final AlertDialog dialog = new AlertDialog.Builder(this)
					.setTitle(
							"Please enter the code you received in your email")
					.setView(view)
					.setPositiveButton("Resend",
							new DialogInterface.OnClickListener()
							{
								/**
								 * automatically called when the positive button
								 * on the dialog is clicked
								 * 
								 * @param dialog
								 *            - a reference to the currently
								 *            visible dialog
								 * @param which
								 *            - the position of the button just
								 *            clicked
								 * 
								 */
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									String name = user
											.getString(ParseDriver.USER_NAME);
									String email = user
											.getString(ParseDriver.USER_EMAIL);
									String code = codeEmail(email);
									String message = Mail
											.EMAIL_FOR_RESENDING_VERIFICATION_CODE(
													name, code);
									Mail mail = new Mail(
											"no.reply.greenbook@gmail.com",
											"hello world");
									mail.setFrom("no.reply.greenbook");
									mail.setTo(email); // set to email
									mail.setSubject("GreenBook email verifcation"); // varify
									mail.setMessage(message); // set messege
									send(mail);
								}
							}).create();
			dialog.show();
			final EditText textView = (EditText) view
					.findViewById(R.id.verify_email);

			textView.addTextChangedListener(new TextWatcher()
			{
				/**
				 * Method called after text is changed
				 * 
				 * @param s
				 *            - text changed as an Editable
				 * @return void
				 */
				@Override
				public void afterTextChanged(Editable s)
				{
				}

				/**
				 * Method called before text is changed
				 * 
				 * @param s
				 *            the - text changed
				 * @param start
				 *            - starting position
				 * @param after
				 *            - count after
				 * @param count
				 *            - current count
				 * @return void
				 */
				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after)
				{
				}

				/**
				 * Method called when text is changed
				 * 
				 * @param s
				 *            the - text changed
				 * @param start
				 *            - starting position
				 * @param before
				 *            - count before
				 * @param count
				 *            - current count
				 * @return void
				 */

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count)
				{
					if (textView.getText().toString().equals(code))
					{
						user.put(ParseDriver.USER_TYPE, "auth");
						user.saveInBackground(new SaveCallback()
						{
							@Override
							public void done(ParseException arg0)
							{
								dialog.dismiss();
								Toast.makeText(getApplicationContext(),
										"Verified!", Toast.LENGTH_LONG).show();
								verification.description = "Email Verified!";
								settingsAdapter.notifyDataSetChanged();
							}
						});
					}
				}
			});
		}
		else
			Toast.makeText(getApplicationContext(), "Email Alreaddy Verified",
					Toast.LENGTH_LONG).show();
	}

	
	/**
	 * called when the user decides to change the currency of the app
	 */
	public void changeRegion()
	{

		Log.i("settings", "changeregion()");
		View view = getLayoutInflater().inflate(R.layout.picker_exchangerates,
				null);
		final NumberPicker exchangePicker = (NumberPicker) view
				.findViewById(R.id.exchange_picker);
		exchangePicker.setMinValue(0);
		exchangePicker.setMaxValue(Vars.currencyParseList.size() - 1);
		exchangePicker.setWrapSelectorWheel(false);
		exchangePicker
				.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

		String URL = "http://download.finance.yahoo.com/d/quotes.csv?";

		String names[] = new String[Vars.currencyParseList.size()];
		currencySymbols = new ArrayList<String>();
		backupExchangeValues = new ArrayList<Double>();
		int i = 0;
		for (ParseObject object : Vars.currencyParseList)
		{
			currencySymbols.add(object.getString(ParseDriver.CURRENCY_SYMBOL));
			backupExchangeValues.add(object
					.getDouble(ParseDriver.CURRENCY_VALUE));
			tickers.add(object.getString(ParseDriver.CURRENCY_TICKER));
			URL += "s=";
			URL += Vars.DEF_CURRENCY_TICKER;
			URL += tickers.get(i);
			URL += "=X&";
			names[i] = object.getString(ParseDriver.CURRENCY_NAME) + " ("
					+ tickers.get(i++) + ")";
		}
		URL += "f=l1&e=.cs";
		exchangePicker.setDisplayedValues(names);
		final AlertDialog dialog = new AlertDialog.Builder(this).setView(view)
				.setPositiveButton("Select", new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						final int value = exchangePicker.getValue();
						final double multiplier = exchangeValues.get(value);
						final NotificationManager man = NotifyUser(
								3,
								"Updating All Transactions",
								"This message will cancel automatically when all updates have completed",
								new Intent());
						ParseQuery<ParseObject> transactionsQuery = new ParseQuery<ParseObject>(
								ParseDriver.TRANSACTION_TABLE);
						transactionsQuery
								.whereEqualTo(ParseDriver.USER_TRANSACTION,
										Vars.userParseObj);
						transactionsQuery
								.findInBackground(new FindCallback<ParseObject>()
								{
									int done = 1;

									@Override
									public void done(List<ParseObject> list,
											ParseException arg1)
									{
										Vars.transactionParseList = list;
										final int size = list.size();
										for (ParseObject obj : list)
										{
											obj.put(ParseDriver.TRANSACTION_VALUE,
													Double.parseDouble(Vars.decimalFormat.format(obj
															.getDouble(ParseDriver.TRANSACTION_VALUE)
															* multiplier)));
											obj.saveInBackground(new SaveCallback()
											{
												@Override
												public void done(
														ParseException arg0)
												{
													done++;
													if (done >= size)
														man.cancel(3);
												}
											});
										}
									}
								});

						for (ParseObject obj : Vars.accountsParseList)
						{
							obj.put(ParseDriver.ACCOUNT_BALANCE,
									Double.parseDouble(Vars.decimalFormat.format(obj
											.getDouble(ParseDriver.ACCOUNT_BALANCE)
											* multiplier)));
							obj.saveInBackground();
						}

						for (ParseObject obj : Vars.transactionParseList)
							obj.refreshInBackground(new RefreshCallback()
							{

								@Override
								public void done(ParseObject arg0,
										ParseException arg1)
								{

								}
							});

						Vars.userParseObj.put(ParseDriver.USER_CURRENCY, Vars.currencyParseList.get(value));
						Log.d("userparseobj: ", Vars.currencyParseList.get(value).getObjectId());
						Vars.userParseObj.saveInBackground();
						Vars.DEF_CURRENCY_SYMBOL = currencySymbols.get(value);
						Vars.DEF_CURRENCY_TICKER = tickers.get(value);

					}
				}).create();
		updateExchangeRates(dialog, URL, Vars.currencyParseList.size());

		exchangePicker.setOnValueChangedListener(new OnValueChangeListener()
		{
			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal)
			{
				dialog.setTitle("1 " + Vars.DEF_CURRENCY_TICKER + " = "
						+ currencySymbols.get(newVal)
						+ exchangeValues.get(newVal));
			}
		});
	}

	
	public void viewTutorial()
	{
		Intent intent = new Intent(getApplicationContext(), TutorialActivity.class);
		startActivity(intent);
	}
	
	/**
	 * method called when the activit changes state from a paused to a resumed
	 * 
	 * @return void
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		Log.d("settingsac", "settingsactivity.onresume()");
	}

	/**
	 * method called when the activit changes state from a running to a paused
	 * 
	 * @return void
	 */
	@Override
	protected void onPause()
	{
		super.onPause();
		Log.d("transac", "settingsactivity.onpause()");
	}

	/**
	 * method called when the activity is destroyed
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Log.d("transac", "settingsactivity.ondestroy()");
	}

	/**
	 * method called to obtain all exchange rates from Yahoo! Finance and
	 * display to the user
	 * 
	 * @param secondDialog
	 *            - the dialog displayed after the values are obtained
	 * @param URL
	 *            - the URl from where the data will be reqeusted
	 * @param length
	 *            - the number of values being requested
	 */
	public void updateExchangeRates(AlertDialog secondDialog, String URL,
			int length)
	{
		View view = getLayoutInflater().inflate(
				R.layout.simple_progress_layout, null);
		final AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle("Downloading").setView(view).create();
		Log.i("settings", "alert dialog show");
		dialog.show();
		Log.i("settings", "alert dialog shown");
		progressBar = (ProgressBar) view.findViewById(R.id.simple_progress_bar);
		exchangeValues = new ArrayList<Double>();
		new ExchangeRate().execute(URL, length, dialog, secondDialog);
	}

	/**
	 * A Setting class that acts as a struct for storing setting values
	 */
	public class Setting
	{

		/** The name. */
		String name;

		/** The description. */
		String description;

		/** The icon. */
		int icon;

		public Setting(String name, String description, int icon)
		{
			this.name = name;
			this.description = description;
			this.icon = icon;

			SettingsActivity.insertSetting(name);
		}
	}

	/**
	 * A Background AsyncTask class that executes the URL connection on a
	 * background thread and after they are retreived, displays to the user the
	 * results. This Class does not handle the setting, only sets up the
	 * setting.
	 */
	public class ExchangeRate extends AsyncTask<Object, Object, Object>
	{

		/** The dialog. */
		public AlertDialog dialog;

		/** The second dialog. */
		public AlertDialog secondDialog;

		/**
		 * A method automatically executed when the thread starts. Thee
		 * expressions are all executed in a Background thread other than the UI
		 * thread.
		 * 
		 * @Override
		 */
		protected Object doInBackground(Object... url)
		{
			if (url[0] instanceof String)
			{
				try
				{
					URL exchangeURL = new URL((String) url[0]);
					dialog = (AlertDialog) url[2];
					secondDialog = (AlertDialog) url[3];
					byte[] buffer = new byte[32];
					progressBar.setMax(120);
					String text = "";
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					BufferedInputStream stream = new BufferedInputStream(
							exchangeURL.openStream());
					int bytesRead = 0, total = 0;
					while ((bytesRead = stream.read(buffer)) > 0)
					{
						total += bytesRead;
						Log.i("settings", "txt " + buffer);
						publishProgress(null, total);
						out.write(buffer, 0, bytesRead);
					}
					Log.i("settings", "text " + text);
					Log.i("settings", "total" + total);
					text = out.toString("UTF-8");
					publishProgress(text, total);
				}

				catch (MalformedURLException urexe)
				{
					Log.i("settings", "URL exceptions " + urexe.getMessage());
				}

				catch (IOException ioexe)
				{
					Log.i("settings", "IO excpection " + ioexe.getMessage());
					Log.i("settings", backupExchangeValues.toString());
					publishProgress(null, null);
				}
			}

			return null;
		}

		/**
		 * method called when part of the code has finished executing, so the
		 * user can be notified of its progress
		 * 
		 * @param values
		 *            - values used in the progress update
		 * @return void
		 */
		@Override
		protected void onProgressUpdate(Object... values)
		{
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			if (values[0] != null)
			{
				String text = (String) values[0];
				StringTokenizer tokenizer = new StringTokenizer(text);
				exchangeValues = new ArrayList<Double>();
				while (tokenizer.hasMoreTokens())
					exchangeValues
							.add(Double.parseDouble(tokenizer.nextToken()));

			}

			if (values[0] == null && values[1] == null)
			{
				Log.i("settings", "both null");
				exchangeValues = backupExchangeValues;
				Toast.makeText(getApplicationContext(),
						"No Network Connection \n-Using latest offline data",
						Toast.LENGTH_LONG).show();
			}
			Log.i("settings", "prog " + values[1]);
			if (values[1] != null)
				progressBar.setProgress((Integer) values[1]);
		}

		/**
		 * method called af the thread has finished executing
		 * 
		 * @param result
		 *            - the result of the task after it is done
		 * @return void
		 */
		@Override
		protected void onPostExecute(Object result)
		{
			DBDriver.UPDATE_CURRENCIES(exchangeValues);
			Log.i("settings", currencySymbols.toString());
			Log.i("settings", exchangeValues.toString());
			dialog.dismiss();
			secondDialog.setTitle("1 " + Vars.DEF_CURRENCY_TICKER + " = "
					+ currencySymbols.get(0) + exchangeValues.get(0));
			secondDialog.show();
		}
	}
}