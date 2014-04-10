package com.stacksmashers.greenbook;

import java.util.List;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * The First Activity shown to the user (apart from the splash screen). 
 * Holds the Login and Registration fragments and coordinates them 
 * 
 * @author Ravish Chawla
 */
public class MainActivity extends BaseActivity
{
	/** The register button. */
	protected Button loginButton, registerButton;

	/** The tag. */
	protected final String TAG = "activity_main";

	/** The register fragment. */
	protected RegisterFragment registerFragment;
	
	/** The login fragment. */
	protected LoginFragment loginFragment;
	
	/** The action bar. */
	protected ActionBar actionBar;
	
	/** The check. */
	protected MenuItem check;

	/**
	 * called automatically when the activity is first created. 
	 * inflates the xml layout, finds views within this container, and 
	 * adds listeners and adapters. 
	 *
	 * @param savedInstanceState - saved state from a previous instance
	 * @return void
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		actionBar = getActionBar(); 
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);  // set display show title  

		Tab tab = actionBar
				.newTab()
				.setText("Login")
				.setTabListener(
						new SimpleTabListener<LoginFragment>(this, "Login",
								LoginFragment.class, 0, null));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText("Register")
				.setTabListener(
						new SimpleTabListener<RegisterFragment>(this, "Register",
								RegisterFragment.class, 1, null));
		actionBar.addTab(tab);
	}

	/**
	 * automatically called when the activity is first created to inflate
	 * the options menu. overriden here to inflate a custom menu. 
	 *
	 * @param menu - reference to the menu being inflated
	 * @return boolean - returns true in all cases. there should be no case where the options menu would fail to load. 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);

		ActionBar actionBar = getActionBar(); 
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources() 
				.getColor(android.R.color.holo_blue_light))); 
		check = menu.findItem(R.id.action_check);
		check.setEnabled(false);   

		return true;
	}
	
	/**
	 * called automatically when an option is selected in the options menu. 
	 *
	 * @param item - reference to the item selected 
	 * @return boolean - returns true if the selection was handled succesfully. 
	 */

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item)
	{
		if (item.getItemId() == R.id.action_forgot_password) 
		{
			View view = getLayoutInflater().inflate(R.layout.settings_verify,
					null);
			final EditText textviewer = (EditText) view
					.findViewById(R.id.verify_email);
			textviewer.setHint("Email");
			AlertDialog dialog = new AlertDialog.Builder(this)
					.setView(view)
					.setMessage("Enter your Email") 
					.setPositiveButton("Send verification Email", 
							new OnClickListener() 
							{
						/**
						 * automatically called when the positive button on the
						 * dialog is clicked
						 * 
						 * @param dialog
						 *            - a reference to the currently visible dialog
						 * @param which
						 *            - the position of the button just clicked
						 * 
						 */
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseDriver.USER_TABLE);
									query.whereEqualTo(ParseDriver.USER_EMAIL, textviewer.getText().toString());
									query.findInBackground(new FindCallback<ParseObject>()
									{
							
										/**
										 * Parse method, a callback that is automatically initiated when 
										 * the query is finishe dloading
										 * 
										 * @param usersList - the return value of the query
										 * @param exe - error if failed to query results, null otherwise
										 */
										@Override
										public void done(List<ParseObject> usersList, ParseException exe)
										{
											if(usersList != null)
											{
												if (usersList.size() != 0) 
												{	
													ParseObject object = usersList.get(0);	
														String name = object.getString(ParseDriver.USER_EMAIL);
														String email = textviewer.getText()
																.toString();
														String pass = object.getString(ParseDriver.USER_PASS); 
														String message = Mail.EMAIL_FOR_FORGOTTEN_PASSWORD(name, pass);
														Mail mail = new Mail(
																"no.reply.greenbook@gmail.com",
																"hello world");
														mail.setFrom("no.reply.greenbook");
														mail.setTo(email);
														mail.setSubject("GreenBook email verifcation");
														mail.setMessage(message);
														send(mail);
												}
											}
											else
												Log.i("Parse", exe.getMessage());
										}
									});
								}
							}).create();      
			dialog.show(); 

		}
		
		else if (item.getItemId() == R.id.action_check) 
		{
			if (actionBar.getSelectedNavigationIndex() == 0)
			{
				loginFragment = (LoginFragment) getSupportFragmentManager() 
						.findFragmentByTag("Login");
				loginFragment.logon();
			}
			
			else
			{
				registerFragment = (RegisterFragment)getSupportFragmentManager().findFragmentByTag("Register");
				registerFragment.register(); 
			}
		}

		return false; 

	};

	/**
	 * OnClick method associated (in xml) with the Login button 
	 *
	 * @param view - reference to the view initiating the call
	 * @return void
	 */

	
	public void onClickLoginMainButton(View view)
	{
		Intent loginIntent = new Intent(getApplicationContext(),
				LoginFragment.class);
		startActivity(loginIntent);
	}

	/**
	 * On click method associated (in xml) with the Register button
	 *
	 * @param view - reference to the view initiating the call
	 * @return void 
	 */
	public void onClickRegisterMainButton(View view)
	{
		Intent registerIntent = new Intent(getApplicationContext(),
				RegisterFragment.class);
		startActivity(registerIntent);
	}
}
