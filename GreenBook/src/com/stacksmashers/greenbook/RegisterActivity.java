package com.stacksmashers.greenbook;

import java.io.File;
import java.io.FileOutputStream;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
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
	TextView name_check, email_check, password_check, checkpassword_check;
	// check  name, email, password and check password 		
																			
															
	// calling register button
	ImageView badge;

	static boolean dismiss = false;
	static int PICTURE_REQUEST = 1;
	static int CROP_REQUEST = 2;
	/**
	 * @param args
	 */

	private String text;
	private Cursor caeser;    // cursor 
	private boolean name_bool = false, email_bool = false,
			password_bool = false, checkpassword_bool = false;

	private MainActivity main;

	private Bitmap photo;

	/**
	 * called this method to start the activity. Maintain the activity and
	 * application.
	 * 
	 * @param savedInstanceState
	 * @param inflater
	 * @param container
	 * 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState); // create
																		// savedinstancestate

		View view = inflater.inflate(R.layout.activity_register, container,
				false);// calling activity register
		// from layour

		register_name = (EditText) view.findViewById(R.id.register_name); 
		//calling name from register 
																			
		register_username = (EditText) view
				.findViewById(R.id.register_username); // calling
														// username
														// button
		register_password = (EditText) view
				.findViewById(R.id.register_password); // calling
														// password
														// button
		register_checkpassword = (EditText) view
				.findViewById(R.id.register_checkpassword);   // find Id 

		name_check = (TextView) view.findViewById(R.id.nameCheck); // check name
		email_check = (TextView) view.findViewById(R.id.emailCheck); // check email 
																		
		password_check = (TextView) view.findViewById(R.id.passwordCheck); // check password
																		 p
		checkpassword_check = (TextView) view     // check check password 
				.findViewById(R.id.checkpasswordCheck);

		register_name.addTextChangedListener(new TextWatcher()    // new text watcher 
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

				if (main.check != null)
					if (name_bool && email_bool && password_bool
							&& checkpassword_bool) // if name and password true
						main.check.setEnabled(true);
					else
						main.check.setEnabled(false);

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

						if (main.check != null)
							if (name_bool && email_bool && password_bool
									&& checkpassword_bool)
								main.check.setEnabled(true);
							else
								main.check.setEnabled(false);

					}
				});

		
		register_checkpassword.addTextChangedListener(new TextWatcher()
		{
           /**
            * @param arg0
            * @return void 
            */
			@Override
			public void afterTextChanged(Editable arg0)
			{
				if (checkpassword_bool)
					checkpassword_check.setText("✔");
				else
					checkpassword_check.setText("✗");

				if (main.check != null)
					if (name_bool && email_bool && password_bool
							&& checkpassword_bool)
						main.check.setEnabled(true);    // set enable true 
					else
						main.check.setEnabled(false);   //set unable false 

			}

			@Override
			/**
			 * @param arg0
			 * @param arg1
			 * @param arg2
			 * @param arg3
			 * @return void 
			 */
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}
			
			/**
			 * @param arg0
			 * @param arg1
			 * @param arg2
			 * @param arg3
			 * @return void 
			 */

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
	// if text.length >0 and text = register check password 
					checkpassword_bool = true;     // true 
				else
					checkpassword_bool = false;    // false 

			}

		});
  
		// addtextchangedlistened for register password 
		register_password.addTextChangedListener(new TextWatcher()
		{

			/**
			 * @param arg0
			 * @return void 
			 */
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

				if (main.check != null)
					if (name_bool && email_bool && password_bool
							&& checkpassword_bool)
						main.check.setEnabled(true);
					else
						main.check.setEnabled(false);

			}

			/**
			 * @param arg0
			 * @param arg1
			 * @param arg2
			 * @param arg3
			 * @return void 
			 */
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			/**
			 * @param arg0
			 * @param arg1
			 * @param arg2
			 * @param arg3
			 * @return void 
			 */
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
		// findview by ID
		badge.setOnClickListener(new OnClickListener()
		// set Onclick listener for new onclicklistener 
		{
           
			/**
			 * @param v
			 * @return void 
			 */
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				Intent intent = new Intent();   // calling new intent 
				intent.setType("image/*");      // set image 
				intent.setAction(Intent.ACTION_GET_CONTENT);  // setaction 
				startActivityForResult(                       // start activity 
						Intent.createChooser(intent, "Select Picture"),
						PICTURE_REQUEST);

			}
		});

		main = (MainActivity) getActivity();    // get main activity 

		return view;        // return view 

	}

	
	/**
	 * @param request
	 * @param result
	 * @param data
	 * @return void
	 * we can use this method to get activity result 
	 */
	
	@Override
	public void onActivityResult(int request, int result, Intent data)
	{

		if (result == getActivity().RESULT_OK)     // result is good 
		{
			if (request == PICTURE_REQUEST)       // request picture 

			{
				Toast.makeText(getActivity(), "Selected", Toast.LENGTH_LONG)
						.show();               // get the picture that we requested 

				Uri selectedImage = data.getData();        // get data from selected image 
				String path = selectedImage.getPath();    
				//get string path from selected string path 
				Log.d("path: ", path);

				// badge.setImageURI(selectedImage);

				// call for new intent cop 
				final Intent crop = new Intent("com.android.camera.action.CROP");
				crop.setData(selectedImage);
				crop.putExtra("outputX", 75);
				crop.putExtra("outputY", 75);
				crop.putExtra("aspectX", 1);
				crop.putExtra("aspectY", 1);
				crop.putExtra("scale", true);
				crop.putExtra("noFaceDetection", true);
				crop.putExtra("return-data", true);
				startActivityForResult(crop, CROP_REQUEST);
			}

			else if (request == CROP_REQUEST)     // if we crop request 
			{
				Bundle extras = data.getExtras();
				if (extras != null)           // extras not null 
				{
					photo = extras.getParcelable("data");   // get data 
					badge.setImageBitmap(photo);        // set picture 
					dismiss = false;                 // if not, than dismiss 

				}
			}

		}

	}

	/**
	 * this method calls to resume the activity 
	 */
	@Override
	public void onResume() // resume work
	{
		super.onResume();

		if (dismiss)
		{
			register_name.setText(""); // set text name
			register_username.setText(""); // set text username
			register_password.setText(""); // set text password
			register_checkpassword.setText("");// set text check password
			dismiss = true;
		}

	}

	/**
	 * called this method to start the regristration activity Maintain the
	 * regristration activity and application.
	 * 
	 * 
	 * @return void
	 */

	public void register()
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
		brutus.put(DBHelper.USER_NAME, name);
		brutus.put(DBHelper.USER_EMAIL, username);
		brutus.put(DBHelper.USER_PASS, password);
		brutus.put(DBHelper.USER_TYPE, main.codeEmail(username));

		caeser = sqldbase.query(DBHelper.USER_TABLE, new String[] {
				DBHelper.USERS_ID, DBHelper.USER_EMAIL }, DBHelper.USER_EMAIL
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

									// Intent LoginIntent = new Intent(
									// getActivity(), LoginActivity.class); //
									// get
									// application
									// context
									// from
									// loginactivity
									// class
									// LoginIntent.putExtra("Login Email",
									// caeser.getString(1)); // get string
									// call
									// login
									// email
									// getActivity().finish(); // finish

									if (main != null)
										main.actionBar
												.setSelectedNavigationItem(0);

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

			ContextWrapper wrapper = new ContextWrapper(getActivity());
			// get new contextwrapper from  wrapper
			File imagesPath = wrapper.getDir("images", Context.MODE_PRIVATE);
			File filePath = new File(imagesPath, main.normalizeEmail(username));
			Toast.makeText(getActivity(),        // get activity 
					"Normalized: " + main.normalizeEmail(username),
					Toast.LENGTH_LONG).show();

			FileOutputStream output = null;   // output== null 

			try
			{

				output = new FileOutputStream(filePath);
				Log("File Path: " + filePath);
				photo.compress(Bitmap.CompressFormat.PNG, 100, output);
				Log("Photo compressed");

				output.close();
			}

			catch (Exception e)
			{

				Toast.makeText(getActivity(), "Couldn't save photo",   // we cant save photo 
						Toast.LENGTH_SHORT);
				e.printStackTrace();       // print stack trace 
			}

			sqldbase.insert(DBHelper.USER_TABLE, null, brutus); // use dbhelper
			main.NotifyUser("Please verify your Email Address",
					"Click to open Default Email client",
					getActivity().getPackageManager()
							.getLaunchIntentForPackage("com.android.email"));
			sendMessage(name, username, password, main.codeEmail(username));
			// user table

			new AlertDialog.Builder(getActivity())
					.setTitle("Login Now?")                // set title 
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

									/*
									 * Intent LoginIntent = new Intent(
									 * getActivity(), LoginActivity.class);
									 * getActivity().finish();
									 * startActivity(LoginIntent); // start
									 */// activity

									dismiss = true;
									dialog.dismiss();
									// getActivity().getActionBar().setSelectedNavigationItem(0);

									MainActivity main = (MainActivity) getActivity();

									if (main != null)
										main.actionBar
												.setSelectedNavigationItem(0);

								}
							})
					.setNegativeButton("Exit",                     // set exit button 
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

	/**
	 * @param name
	 * @param user
	 * @param pass
	 * @param code
	 * @return void
	 * this method send messege to the app 
	 */
	public void sendMessage(String name, String user, String pass, String code)
	{
		// sendEmail();

		String message = "Hello "
				+ name
				+ ",\n"
				+ "Thankyou for creating an account with GreenBook!"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "Please Verify your email Address by using the following code:  "
				+ code + "\n" + "\n" + "Other Account Information"
				+ "\nUsername: " + user + "\nPassword: " + pass;

		Mail mail = new Mail("no.reply.greenbook@gmail.com", "hello world");
		mail.setFrom("no.reply.greenbook@gmail.com");
		mail.setTo(user);
		mail.setSubject("GreenBook email verifcation");
		mail.setMessage(message);

		main.send(mail);

		// new SendMail().execute(mail);

	}

}
