package com.stacksmashers.greenbook;

import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * A fragment that controls and coordinates all activity for loging in a user.
 * 
 * @author Ravish Chawla
 */

public class LoginFragment extends BaseFragment
{

	/** The login_name. */
	private EditText login_name;

	/** The login_pass. */
	private EditText login_pass;

	/** The main. */
	private MainActivity main;

	/** The name_bool and pass_bool. */
	private boolean name_bool = false, pass_bool = false;

	/**
	 * Instantiates a new login fragment.
	 */
	public LoginFragment()
	{
	}

	/**
	 * called this method to start the activity.
	 * 
	 * @param inflater
	 *            the inflater
	 * @param container
	 *            the container
	 * @param savedInstanceState
	 *            the saved instance state
	 * @return void
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		main = (MainActivity) getActivity();
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		login_name = (EditText) view.findViewById(R.id.login_name);
		login_pass = (EditText) view.findViewById(R.id.login_password);

		login_name.addTextChangedListener(new TextWatcher()
		{
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
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				if (login_name.getEditableText().toString()
						.matches(".+@[a-z]+[.](com|edu|org|gov|batman)"))
					name_bool = true;
				else
					name_bool = false;
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
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
			}

			/**
			 * Method called after text is changed
			 * 
			 * @param s
			 *            - the text changed as an Editable
			 * @return void
			 */
			@Override
			public void afterTextChanged(Editable s)
			{
				if (main.check != null)
					if (name_bool && pass_bool)
						main.check.setEnabled(true);
					else
						main.check.setEnabled(false);
			}
		});

		login_pass.addTextChangedListener(new TextWatcher()
		{
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
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				if (login_pass.getEditableText().toString().matches(".+"))
					pass_bool = true;
				else
					pass_bool = false;

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
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				// TODO Auto-generated method stub

			}

			/**
			 * Method called after text is changed
			 * 
			 * @param s
			 *            - the text changed as an Editable
			 * @return void
			 */

			@Override
			public void afterTextChanged(Editable s)
			{

				if (main.check != null)
					if (name_bool && pass_bool)
						main.check.setEnabled(true);
					else
						main.check.setEnabled(false);

				Log.i("tag", " " + name_bool);

			}
		});

		return view;

	}

	/**
	 * Called automatically when the activity enters the resume state from a
	 * paused state
	 * 
	 * @return void this method resume the activity
	 */
	@Override
	public void onResume()
	{
		super.onResume();
	}

	/**
	 * A handler that gets input data from the user
	 * 
	 * @return void this method to click on login activity and see it
	 */

	public void logon()
	{
		final String email = login_name.getEditableText().toString();
		String pass = login_pass.getEditableText().toString();

		ParseQuery<ParseObject> query = ParseQuery
				.getQuery(ParseDriver.USER_TABLE);
		query.whereEqualTo(ParseDriver.USER_EMAIL, email);
		query.whereEqualTo(ParseDriver.USER_PASS, pass);

		query.findInBackground(new FindCallback<ParseObject>()
		{

			@Override
			public void done(List<ParseObject> userList, ParseException exe)
			{
				if (userList != null)
				{

					if (userList.size() != 0)
					{
						Intent homeIntent = new Intent(getActivity(),
								TransactionsActivity.class);
						if (email.equals("sudo@root.com"))
						{
							homeIntent.putExtra("Account Type", "Sudoer");

						}
						else
							homeIntent.putExtra("Account Type", "Non-Sudoer");

						ParseObject object = userList.get(0);
						Vars.userObjectID = object
								.getString(ParseDriver.OBJECT_ID);
						Vars.userParseObj = object;
						Vars.currencyParseObj = object
								.getParseObject(ParseDriver.USER_CURRENCY);

						for (ParseObject obj : Vars.currencyParseList)
						{
							if (obj.getObjectId().equals(
									Vars.currencyParseObj.getObjectId()))
							{
								Vars.currencyParseObj = obj;
								break;
							}
						}

						Vars.DEF_CURRENCY_SYMBOL = Vars.currencyParseObj
								.getString(ParseDriver.CURRENCY_SYMBOL);
						Vars.DEF_CURRENCY_TICKER = Vars.currencyParseObj
								.getString(ParseDriver.CURRENCY_TICKER);

						startActivity(homeIntent);

					}
					else
					{
						Toast.makeText(getActivity(), "User not found",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});
	}
}