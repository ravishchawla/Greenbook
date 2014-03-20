package com.stacksmashers.greenbook;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * this method make sure about settings activity
 */

public class SettingsActivity extends BaseActivity
{

	private Button verify_email;
	private int user_id;
	private ListView list;
	private static String[] setting_names = new String[2];
	private static int settingPos;

	private boolean loadFiles = true;
	private ArrayList<Double> exchangeValues = new ArrayList<Double>();
	private ArrayList<Double> backupExchangeValues = new ArrayList<Double>();
	private ArrayList<String> currencySymbols = new ArrayList<String>();
	private ArrayList<String> tickers = new ArrayList<String>();
	private ArrayList<String> exCnames = new ArrayList<String>();
	boolean gotData = false;
	private String[] exchangeNames;
	private ProgressBar progressBar;
	private static int selectedValue = 0;

	Setting verification = new Setting("Verify your Email",
			"Email not verified. You can also resend your code",
			R.drawable.ic_launcher);
	Setting region = new Setting("Regional Settings",
			"Select your default Currency", R.drawable.ic_launcher);

	Setting[] settings = { verification, region };

	private final static int SETTING_VERIFY_EMAIL = 0;
	private final static int SETTING_REGION_SETTING = 1;

	// settings activity
	public SettingsActivity()
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

	/**
	 * @param savedInstanceState
	 * @return void this method create savedinstancestate
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_settings); // setcontentview

		Bundle extras = getIntent().getExtras(); // getintent from bundle extras
		user_id = extras.getInt("User ID");

		settingPos = 0;
		list = (ListView) findViewById(R.id.settings_list); // find view id

		// ArrayList<String> items = (ArrayList<String>)
		// Arrays.asList(setting_names);

		// call new customerarrayadapater
		final CustomArrayAdapter adapter = new CustomArrayAdapter(this,
				setting_names);
		list.setAdapter(adapter);

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
				// TODO Auto-generated method stub

				Log.i("Setting", "pressed pos: "
						+ (position == SETTING_REGION_SETTING));

				if (position == SETTING_VERIFY_EMAIL)
				{
					verify(user_id); // varify user id
				}

				if (position == SETTING_REGION_SETTING)
				{
					Log.i("setting", "calling changeregion)(");
					changeRegion();
				}

			}

		});

		defineCurrencies();

	}

	private static void insertSetting(String name)
	{
		setting_names[settingPos++] = name;
	}

	private void defineCurrencies()
	{

	}

	/**
	 * this class make sure about array adapter
	 */
	private class CustomArrayAdapter extends ArrayAdapter<String>
	{

		private final Context context;
		private final String[] values;
		private LayoutInflater inflater;

		/**
		 * @param context
		 * @param values
		 */
		public CustomArrayAdapter(Context context, String[] values)
		{
			super(context, R.layout.nice_list_layout, values);

			this.context = context;
			this.values = values;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		/**
		 * @param position
		 * @param convertView
		 * @param parent
		 * @return views we can view different things from this methid
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{

			View row = inflater.inflate(R.layout.nice_list_layout, parent,
					false);
			TextView text = (TextView) row.findViewById(R.id.transaction_title);
			TextView description = (TextView) row
					.findViewById(R.id.transactions_total_balance);
			ImageView image = (ImageView) row
					.findViewById(R.id.transactions_total_icon);

			text.setText(settings[position].name);
			description.setText(settings[position].description);
			image.setImageResource(settings[position].icon);

			return row;

		}

	}

	/**
	 * @param user_id
	 * @return void
	 */

	public void verify(final int user_id)
	{

		Cursor caeser = DBDriver.GET_USER_TYPE(user_id);

		caeser.moveToFirst();
		if (caeser.getCount() != 0 && !caeser.getString(0).equals("auth")
				&& !caeser.getString(0).equals("admin"))
		{

			final String code = caeser.getString(0);

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
								 * @param dialog
								 * @param which
								 */
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									// TODO Auto-generated method stub

									Cursor caeser = DBDriver.GET_ALL_USERS();

									if (caeser.getCount() != 0)
									{
										caeser.moveToFirst();

										String name = caeser.getString(0); // get
																			// string
																			// name
										String email = caeser.getString(1); // get
																			// string
																			// email
										String code = codeEmail(email); // get
																		// string
																		// code

										// string messege
										String message = Mail
												.EMAIL_FOR_RESENDING_VERIFICATION_CODE(
														name, code);

										// get new messege

										Mail mail = new Mail(
												"no.reply.greenbook@gmail.com",
												"hello world");
										mail.setFrom("no.reply.greenbook");
										mail.setTo(email); // set to email
										mail.setSubject("GreenBook email verifcation"); // varify
										mail.setMessage(message); // set messege

										send(mail);
									}

								}
							}).create(); // create

			dialog.show();

			final EditText textView = (EditText) view
					.findViewById(R.id.verify_email);

			textView.addTextChangedListener(new TextWatcher()
			{

				/**
				 * @param s
				 * @return void
				 */
				@Override
				public void afterTextChanged(Editable s)
				{
					// TODO Auto-generated method stub

				}

				/**
				 * @param s
				 * @param start
				 * @param count
				 * @param after
				 * @reutrn void
				 * 
				 */
				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after)
				{
					// TODO Auto-generated method stub

				}

				/**
				 * @param s
				 * @param start
				 * @param before
				 * @param count
				 */
				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count)
				{
					// TODO Auto-generated method stub

					if (textView.getText().toString().equals(code))
					{
						dialog.dismiss();
						Toast.makeText(getApplicationContext(), "Verified!",
								Toast.LENGTH_LONG).show();

						verification.description = "Email Verified!";

						DBDriver.UPDATE_USER_TYPE("auth", user_id);

					}

				}

			});

		}
		else
			Toast.makeText(getApplicationContext(), "Email Alreaddy Verified",
					Toast.LENGTH_LONG).show();
		// show messege that email is already verified
	}

	public void changeRegion()
	{

		Log.i("settings", "changeregion()");

		Cursor csr = DBDriver.GET_ALL_CURRENCIES();
		View view = getLayoutInflater().inflate(R.layout.picker_exchangerates,
				null);
		final NumberPicker exchangePicker = (NumberPicker) view
				.findViewById(R.id.exchange_picker);
		exchangePicker.setMinValue(0);
		exchangePicker.setMaxValue(csr.getCount()-1);
		exchangePicker.setWrapSelectorWheel(false);
		exchangePicker
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		String URL = "http://download.finance.yahoo.com/d/quotes.csv?";

		String names[] = new String[csr.getCount()];

		csr.moveToFirst();
		currencySymbols = new ArrayList<String>();
		backupExchangeValues = new ArrayList<Double>();
		int i = 0;
		Log.i("settings: ", DatabaseUtils.dumpCursorToString(csr));
		while (!csr.isAfterLast())
		{
			Log.i("Setting", "ids: " + csr.getInt(4));

			currencySymbols.add(csr.getString(2));
			backupExchangeValues.add(csr.getDouble(3));
			tickers.add(csr.getString(1));
			URL += "s=";
			URL += Vars.DEF_CURRENCY_TICKER;
			URL += csr.getString(1);
			URL += "=X&";

			names[i++] = csr.getString(0) + " (" + csr.getString(1) + ")";
			csr.moveToNext();
		}

		URL += "f=l1&e=.cs";

		exchangePicker.setDisplayedValues(names);

		selectedValue = 0;

		final AlertDialog dialog = new AlertDialog.Builder(this).setView(view)
				.setPositiveButton("Select", new OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{

						
						int value = exchangePicker.getValue();
						DBDriver.UPDATE_TRANSACTION_VALUES(user_id,
								exchangeValues.get(value));

						DBDriver.UPDATE_DEFAULT_CURRENCY(user_id,
								value);
						
						Vars.DEF_CURRENCY_SYMBOL = currencySymbols.get(value);
						Vars.DEF_CURRENCY_TICKER = tickers.get(value);
						

					}
				}).create();
		updateExchangeRates(dialog, URL, csr.getCount());

		exchangePicker.setOnValueChangedListener(new OnValueChangeListener()
		{

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal)
			{
				// TODO Auto-generated method stub

				selectedValue = newVal;
				dialog.setTitle("1 " + Vars.DEF_CURRENCY_TICKER + " = "
						+ currencySymbols.get(newVal)
						+ exchangeValues.get(newVal));

			}
		});

	}

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

		// http://download.finance.yahoo.com/d/quotes.csv?s=USDILS=X&s=USDJPY=X&s=USDGBP=X&f=l1&e=.cs
		// http://download.finance.yahoo.com/d/quotes.csv?s=USDILS=X&s=USDJPY=X&s=USDGBP=X&f=11&e=.cs

		exchangeValues = new ArrayList<Double>();
		new ExchangeRate().execute(URL, length, dialog, secondDialog);

		/*
		 * 
		 * csr = DBDriver.GET_ALL_CURRENCIES(); exchangeValues = new
		 * ArrayList<Integer>(); csr.moveToFirst(); String base =
		 * defaultCurrency.ticker; while (!csr.isAfterLast()) { new
		 * ExchangeRate().execute("http://quote.yahoo.com/d/quotes.csv?s=" +
		 * base + csr.getString(1) + "=X&f=sl1&e=.csv");
		 * 
		 * if (temporaryReadData != null)
		 * exchangeValues.add(Integer.parseInt(temporaryReadData));
		 * 
		 * csr.moveToNext(); }
		 * 
		 * DBDriver.UPDATE_CURRENCIES(exchangeValues);
		 */

	}

	public class Setting
	{
		String name;
		String description;
		int icon;

		public Setting(String name, String description, int icon)
		{
			this.name = name;
			this.description = description;
			this.icon = icon;

			SettingsActivity.insertSetting(name);

		}

	}

	public class ExchangeRate extends AsyncTask
	{

		AlertDialog dialog;
		AlertDialog secondDialog;

		// this method runs in backgrond for object mail
		@Override
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
					BufferedInputStream stream = new BufferedInputStream(exchangeURL.openStream());
					int bytesRead = 0, total = 0;
					while ((bytesRead = stream.read(buffer)) > 0)
					{
						total += bytesRead;
						// Log.i("settings", "bytes read " + total);
						Log.i("settings", "txt " + buffer);
						publishProgress(null, total);
						out.write(buffer, 0, bytesRead);
						
					}
					Log.i("settings", "text " + text);
					Log.i("settings", "total" + total);

					text = out.toString("UTF-8");
					publishProgress(text, total);

					// progressBar.setMax((Integer) url[1] - 1);
					// int posit = 0;
					// while ((answer = reader.readLine()) != null)
					// publishProgress(answer, posit++);

					// reader.close();

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
		 * @param values
		 * @return void this method check on progress update for object values
		 */
		@Override
		protected void onProgressUpdate(Object... values)
		{
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			
			if(values[0] != null)
			{
				String text = (String)values[0];
				StringTokenizer tokenizer = new StringTokenizer(text);
				exchangeValues = new ArrayList<Double>();
				while(tokenizer.hasMoreTokens())
					exchangeValues.add(Double.parseDouble(tokenizer.nextToken()));

				//for(String token: tokens)
					//Log.i("settings", "text: " + token);
				
				//Log.i("settings", "text : " + values[0]);
			}
			
			if(values[0] == null && values[1] == null)
			{
				Log.i("settings", "both null");
				exchangeValues = backupExchangeValues;
				Toast.makeText(getApplicationContext(), "No Network Connection \n-Using latest offline data", Toast.LENGTH_LONG).show();
			}
			
		//	String answer = (String) values[0];
		//	String tokens[] = answer.split(",");

		//	Log.i("settings", answer);
			Log.i("settings", "prog " + values[1]);
			if(values[1] != null)
				progressBar.setProgress((Integer) values[1]);
			//exchangeValues.add(Double.parseDouble(answer));

			// temporaryReadData = tokens[1];

		}

		/**
		 * @param result
		 * @return void this method execute result for any object
		 */
		protected void onPostExecute(Object result)
		{

			DBDriver.UPDATE_CURRENCIES(exchangeValues);
			gotData = true;

			Log.i("settings", currencySymbols.toString());
			Log.i("settings", exchangeValues.toString());
			dialog.dismiss();
			 secondDialog.setTitle("1 " + Vars.DEF_CURRENCY_TICKER + " = "
			 + currencySymbols.get(0) + exchangeValues.get(0));
			 secondDialog.show();
		}

	}

}
