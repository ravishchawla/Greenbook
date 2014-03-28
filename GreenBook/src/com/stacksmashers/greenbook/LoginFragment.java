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
 * 
 * this class calls all account activity within the application . it make sure
 * all login activity
 */

public class LoginFragment extends BaseFragment
{
	private EditText login_name; // edit login name
	private EditText login_pass;
	// edit login button
	
	private MainActivity main;
	

	boolean name_bool = false, pass_bool = false;

	public LoginFragment() // loginactivity
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * called this method to start the activity.
	 * 
	 * @param savedInstanceState
	 * @param inflater
	 * @param container 
	 * @return void
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState); // create
																		// savedinstancestate
		main = (MainActivity) getActivity();
		
		
		// setContentView(R.layout.activity_login); // setcontentview
		View view = inflater.inflate(R.layout.fragment_login, container, false);

		login_name = (EditText) view.findViewById(R.id.login_name); // login
																	// name
		login_pass = (EditText) view.findViewById(R.id.login_password);
		// login_button = (Button)view.findViewById(R.id.login_button); // login
		// button

		/*
		 * Bundle extras = getIntent().getExtras(); if(extras != null) {
		 * login_name.setText(extras.getString("Login Email")); //login email
		 * name_bool = true; }
		 */
		login_name.addTextChangedListener(new TextWatcher() // text change
															// listener

				{
					/**
					 * @param s
					 * @param start
					 * @param before
					 * @param count
					 * @return void called this method to notify that, within s,
					 *         the count characters beginning at start have just
					 *         replaced old text that had length before.
					 */
					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count)
					{
						// TODO Auto-generated method stub

						if (login_name
								.getEditableText()
								.toString()
								.matches(".+@[a-z]+[.](com|edu|org|gov|batman)"))
							name_bool = true;
						else
							name_bool = false;

					}

					/**
					 * @param s
					 * @param start
					 * @param after
					 * @param count
					 * @return void called this method to notify that, within s,
					 *         the count characters beginning at start start are
					 *         about to be replaced by new text with length
					 *         after.
					 */
					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after)
					{
						// TODO Auto-generated method stub

					}

					/**
					 * @param s
					 * @return void This method is called to notify you that,
					 *         somewhere within s, the text has been changed.
					 */
					@Override
					public void afterTextChanged(Editable s)
					{

						if(main.check != null)
							if (name_bool && pass_bool) // if name and passwor
														// matches
								main.check.setEnabled(true);
							else
								main.check.setEnabled(false);

						
						// TODO Auto-generated method stub

					}
				});

		login_pass.addTextChangedListener(new TextWatcher()
		{
			/**
			 * @param s
			 * @param start
			 * @param before
			 * @param count
			 * @return void called this method to notify that, within s, the
			 *         count characters beginning at start have just replaced
			 *         old text that had length before.
			 */
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				// TODO Auto-generated method stub

				if (login_pass.getEditableText().toString().matches(".+"))
					pass_bool = true;
				else
					pass_bool = false;

			}

			/**
			 * @param s
			 * @param start
			 * @param after
			 * @param count
			 * @return void called this method to notify that, within s, the
			 *         count characters beginning at start start are about to be
			 *         replaced by new text with length after.
			 */
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				// TODO Auto-generated method stub

			}

			/**
			 * @param s
			 * @return void This method is called to notify you that, somewhere
			 *         within s, the text has been changed.
			 */
			@Override
			public void afterTextChanged(Editable s)
			{

				if(main.check != null)
					if (name_bool && pass_bool)
						main.check.setEnabled(true);
					else
						main.check.setEnabled(false);

					Log.i("tag", " " + name_bool);

				
				// TODO Auto-generated method stub

			}
		});

		

		return view;

	}

	/**
	 * @reutn void
	 * this method resume the activity 
	 */
	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();

		//login_name.setText("");
		//login_pass.setText("");
	}

	/**
	 * 
	 *
	 * @return void this method to click on login activity and see it
	 */

	public void logon()
	{
		final String email = login_name.getEditableText().toString();
		String pass = login_pass.getEditableText().toString();

		//Cursor caeser = DBDriver.LOGIN_USER_INFO(email, pass);
		List<ParseObject> parseList = ParseDriver.LOGIN_USER_INFO(email, pass);
		
		/*
		 * fields = sqldbase.query(DBHelper.COURSE_TABLE, new String[] {
		 * DBHelper.COURSE_ID, DBHelper.COURSE_NAME, DBHelper.COURSE_PROF,
		 * DBHelper.AVERAGE_GRADE, DBHelper.IS_WEIGHTED, DBHelper.COURSE_HOURS
		 * }, DBHelper.COURSE_ID + " = " + ID, null, null, null, null);
		 */
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseDriver.USER_TABLE);
		query.whereEqualTo(ParseDriver.USER_EMAIL, email);
		query.whereEqualTo(ParseDriver.USER_PASS, pass);
		
		query.findInBackground(new FindCallback<ParseObject>()
				{
					
					@Override
					public void done(List<ParseObject> userList, ParseException exe)
					{
						// TODO Auto-generated method stub
						
						if(userList != null)
						{
		
							if (userList.size() != 0)
							{
								//	caeser.moveToFirst();
								Intent homeIntent = new Intent(getActivity(),
										TransactionsActivity.class); // get application cotext from
												// accountsactivity class
									if (email.equals("sudo@root.com")) // if account type is specific
									{	
										homeIntent.putExtra("Account Type", "Sudoer"); // return
																	// sudoer

									}
									else
										homeIntent.putExtra("Account Type", "Non-Sudoer"); // reutrn
																		// nonsuder
			
			
									ParseObject object = userList.get(0);
									
									
									Vars.userObjectID = object.getString(ParseDriver.OBJECT_ID);
									Vars.userParseObj = object;
									Vars.currencyParseObj = object.getParseObject(ParseDriver.USER_CURRENCY);
									
									
			// getActivity().finish();
									startActivity(homeIntent); // start activity

							}
							else
							{
								Toast.makeText(getActivity(), "User not found", Toast.LENGTH_LONG)
								.show(); // user not found if id and password wrong
							}

						}

					}
				});
	}
}
