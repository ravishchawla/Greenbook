package com.stacksmashers.greenbook;

import android.R.menu;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * called when the activity first created
 */
public class MainActivity extends BaseActivity
{
	protected Button loginButton, registerButton; // call loginbutton and
													// register button from res

	protected final String TAG = "activity_main";

	protected RegisterFragment registerFragment;
	protected LoginFragment loginFragment;
	protected ActionBar actionBar;
	protected MenuItem check;

	/**
	 * @param savedInstanceState
	 * @return void call this method to do initial setup
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState); // create saveinstancestate
		// setContentView(R.layout.activity_main);

		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		actionBar.setDisplayShowTitleEnabled(true);

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

		// loginButton = (Button)findViewById(R.id.loginButton); // login button
		// registerButton = (Button)findViewById(R.id.registerButton);
		// //register button
	}

	/**
	 * @param menu
	 * @return true or false call this method to show the cotextmenu for the
	 *         menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
				.getColor(android.R.color.holo_blue_light)));

		check = menu.findItem(R.id.action_check);

		check.setEnabled(false);

		return true;
	}

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

								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									// TODO Auto-generated method stub

									Cursor caeser = sqldbase.query(
											DBHelper.USER_TABLE, new String[] {
													DBHelper.USER_NAME,
													DBHelper.USER_TYPE,
													DBHelper.USER_PASS },
											DBHelper.USER_EMAIL
													+ " = '"
													+ textviewer.getText()
															.toString() + "'",
											null, null, null, null);

									if (caeser.getCount() != 0)
									{
										caeser.moveToFirst();

										if (!caeser.getString(1).equals("auth"))
										{
											String name = caeser.getString(0);
											String email = textviewer.getText()
													.toString();
											String pass = caeser.getString(2);

											String message = "Hello " + name
													+ ",\n" + "Hi " + name
													+ ",\n"
													+ "Here's your Password!"
													+ "\n" + "\n" + "\n" + pass
													+ "\n" + "\n" + "--"
													+ "The GreenBook Team";

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
	 * @param view
	 * @return void called when view has been clicked. (login)
	 * 
	 */

	
	public void onClickLoginMainButton(View view) // to show the contextmenu for
													// the view
	{

		Intent loginIntent = new Intent(getApplicationContext(),
				LoginFragment.class); // intent login
		startActivity(loginIntent); // login activity

	}

	/**
	 * @param view
	 * @return void called when view has been clicked. (register)
	 */
	public void onClickRegisterMainButton(View view)
	{

		Intent registerIntent = new Intent(getApplicationContext(),
				RegisterFragment.class); // intent register
		startActivity(registerIntent); // register activity

	}


}
