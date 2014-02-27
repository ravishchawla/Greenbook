package com.stacksmashers.greenbook;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * this class make sure everything about regtistration activity
 */

public class RegisterActivity extends BaseFragment
{

	EditText register_name, register_username, register_password,
			register_checkpassword; // name, username, password, checkpassword
	TextView name_check, email_check, password_check, checkpassword_check; // we
																			// can
																			// see
																			// name,email,password
																			// and
																			// checkpassword
																			// check
	Button register_button; // calling register button
	ImageView badge;
	
	static int PICTURE_REQUEST = 1;
	static int CROP_REQUEST = 2;
	/**
	 * @param args
	 */

	String text;
	public Cursor caeser;
	boolean name_bool = false, email_bool = false, password_bool = false,
			checkpassword_bool = false;

	/**
	 * called this method to start the activity. Maintain the activity and
	 * application.
	 * 
	 * @param savedInstanceState
	 * @return void
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState); // create savedinstancestate

		View view = inflater.inflate(R.layout.activity_register, container, false);// calling activity register
													// from layour

		register_name = (EditText) view.findViewById(R.id.register_name); // calling
																		// name
																		// from
																		// res
		register_username = (EditText) view.findViewById(R.id.register_username); // calling
																				// username
																				// button
		register_password = (EditText) view.findViewById(R.id.register_password); // calling
																				// password
																				// button
		register_checkpassword = (EditText) view.findViewById(R.id.register_checkpassword);

		name_check = (TextView) view.findViewById(R.id.nameCheck); // check name
		email_check = (TextView) view.findViewById(R.id.emailCheck); // check email
		password_check = (TextView) view.findViewById(R.id.passwordCheck); // check
																		// password
		checkpassword_check = (TextView) view.findViewById(R.id.checkpasswordCheck);

		register_button = (Button) view.findViewById(R.id.register_button);
		register_button.setEnabled(false);

	
		
		register_name.addTextChangedListener(new TextWatcher()
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
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub

				text = register_name.getEditableText().toString(); // put text
				// TODO Auto-generated method stub
				if (text.matches("([A-Z][a-z]+[ ])([A-Z][a-z]+)")) // if text
																	// are
																	// registered
					name_bool = true; // its true
				else
					name_bool = false; // its false

			}

			/**
			 * @param s
			 * @param start
			 * @param before
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
				// TODO Auto-generated method stub

				if (name_bool) // if its name
					name_check.setText("✔"); // use the right check mark
				else
					name_check.setText("✗"); // use wrong check mark

				if (name_bool && email_bool && password_bool
						&& checkpassword_bool) // if name and password true
					register_button.setEnabled(true); // register
				else
					register_button.setEnabled(false); // regtistration failed

			}
		});

		register_username.addTextChangedListener(new TextWatcher() // register
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

						text = register_username.getEditableText().toString();
						// TODO Auto-generated method stub
						if (text.matches(".+@[a-z]+[.](com|edu|org|gov|batman)"))
							email_bool = true;
						else
							email_bool = false;

					}

					/**
					 * @param s
					 * @param start
					 * @param before
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
						// TODO Auto-generated method stub

						if (email_bool)
							email_check.setText("✔");
						else
							email_check.setText("✗");

						if (name_bool && email_bool && password_bool
								&& checkpassword_bool)
							register_button.setEnabled(true);
						else
							register_button.setEnabled(false);

					}
				});

		register_checkpassword.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void afterTextChanged(Editable arg0)
			{
				if (checkpassword_bool)
					checkpassword_check.setText("✔");
				else
					checkpassword_check.setText("✗");

				if (name_bool && email_bool && password_bool
						&& checkpassword_bool)
					register_button.setEnabled(true);
				else
					register_button.setEnabled(false);

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				text = register_password.getEditableText().toString();
				// TODO Auto-generated method stub
				if (text.length() > 0
						&& text.equals(register_checkpassword.getEditableText()
								.toString()))
					checkpassword_bool = true;
				else
					checkpassword_bool = false;

			}

		});

		register_password.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void afterTextChanged(Editable arg0)
			{

				if (password_bool)
					password_check.setText("✔");
				else
					password_check.setText("✗");

				if (checkpassword_bool)
					checkpassword_check.setText("✔");
				else
					checkpassword_check.setText("✗");

				if (name_bool && email_bool && password_bool
						&& checkpassword_bool)
					register_button.setEnabled(true);
				else
					register_button.setEnabled(false);

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{

				// text = register_checkpassword.getEditableText().toString();
				// TODO Auto-generated method stub
				if (register_password.getEditableText().toString()
						.matches(".+"))
					password_bool = true;
				else
					password_bool = false;

				if (register_password
						.getEditableText()
						.toString()
						.equals(register_checkpassword.getEditableText()
								.toString()))
					checkpassword_bool = true;
				else
					checkpassword_bool = false;

			}

		});

		badge = (ImageView) view.findViewById(R.id.register_pic);
		badge.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				
				
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						PICTURE_REQUEST);

			}
		});
		return view;

	}

	@Override
	public void onActivityResult(int request, int result, Intent data)
	{

		if (result == getActivity().RESULT_OK)
		{
			if (request == PICTURE_REQUEST)

			{
				Toast.makeText(getActivity(), "Selected",
						Toast.LENGTH_LONG).show();

				Uri selectedImage = data.getData();
				String path = selectedImage.getPath();
				Log.d("path: ", path);

				// badge.setImageURI(selectedImage);

				final Intent crop = new Intent("com.android.camera.action.CROP");
				crop.setData(selectedImage);
				crop.putExtra("outputX", 50);
				crop.putExtra("outputY", 50);
				crop.putExtra("aspectX", 1);
				crop.putExtra("aspectY", 1);
				crop.putExtra("scale", true);
				crop.putExtra("noFaceDetection", true);
				crop.putExtra("return-data", true);
				startActivityForResult(crop, CROP_REQUEST);
			}
	
			else if (request == CROP_REQUEST)
			{
				Bundle extras = data.getExtras();
				if(extras != null)
				{
					Bitmap photo = extras.getParcelable("data");
					badge.setImageBitmap(photo);
				}
			}
		
		}
		
		
	}

	@Override
	public void onResume() // resume work
	{
		super.onResume();

		register_name.setText(""); // set text name
		register_username.setText(""); // set text username
		register_password.setText(""); // set text password
		register_checkpassword.setText("");// set text check password
	}

	/**
	 * called this method to start the regristration activity Maintain the
	 * regristration activity and application.
	 * 
	 * @param view
	 * @return void
	 */
	public void onClickRegisterActivityButton(View view)
	{
		String name = register_name.getEditableText().toString(); // hold string
																	// called
																	// name
		String username = register_username.getEditableText().toString(); // hold
																			// string
																			// called
																			// username
		String password = register_password.getEditableText().toString(); // hold
																			// string
																			// called
																			// password
		String checkPassword = register_checkpassword.getEditableText()
				.toString(); // hold string called check password

		ContentValues brutus = new ContentValues(); // get new content value
		brutus.put(DBHelper.USER_NAME, username);
		brutus.put(DBHelper.USER_PASS, password);

		caeser = sqldbase.query(DBHelper.USER_TABLE, new String[] {
				DBHelper.USERS_ID, DBHelper.USER_NAME }, DBHelper.USER_NAME
				+ " = '" + username + "'", null, null, null, null);

		if (caeser.getCount() != 0)
		{
			caeser.moveToFirst();

			new AlertDialog.Builder(getActivity())
					.setMessage(
							"Login Now with Email: " + caeser.getString(1)
									+ "?")
					.setTitle("Email Alreaddy Registered")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener()
							{

								@Override
								/**
								 * @param which
								 * @return number
								 * call this method when user touches the item 
								 */
								public void onClick(DialogInterface dialog,
										int which)
								{
									// TODO Auto-generated method stub

									Intent LoginIntent = new Intent(
											getActivity(),
											LoginActivity.class); // get
																	// application
																	// context
																	// from
																	// loginactivity
																	// class
									LoginIntent.putExtra("Login Email",
											caeser.getString(1)); // get string
																	// call
																	// login
																	// email
									getActivity().finish(); // finish
									startActivity(LoginIntent); // start
																// activity

								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener()
							{

								/**
								 * @param which
								 * @return number call this method when user
								 *         touches the item
								 */
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									// TODO Auto-generated method stub

									register_username.setText(""); // set
																	// username
																	// text

								}
							}).show(); // show username register

		}
		else
		{

			sqldbase.insert(DBHelper.USER_TABLE, null, brutus); // use dbhelper
																// user table
			Toast.makeText(getActivity(), "User added to database",
					Toast.LENGTH_LONG).show();

			new AlertDialog.Builder(getActivity())
					.setTitle("Login Now?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener()
							{

								/**
								 * @param which
								 * @return number call this method when user
								 *         touches the item
								 */
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									// TODO Auto-generated method stub

									/*Intent LoginIntent = new Intent(
											getActivity(),
											LoginActivity.class);
									getActivity().finish();
									startActivity(LoginIntent); // start
										*/						// activity
									
									dialog.dismiss();
									//getActivity().getActionBar().setSelectedNavigationItem(0);
									Log.i("mua", "haha");
									
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener()
							{

								/**
								 * @param which
								 * @return number call this method when user
								 *         touches the item
								 */
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									// TODO Auto-generated method stub

									getActivity().finish(); // finish

								}
							}).show(); // show the activity

		}
	}

}
