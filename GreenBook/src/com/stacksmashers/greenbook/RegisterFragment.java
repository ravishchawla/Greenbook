package com.stacksmashers.greenbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * This class maintains code and methods for User Registration. It is bound to
 * the layout activity_registration.xml
 * 
 * @author Ravish Chawla
 */
public class RegisterFragment extends BaseFragment
{

	/** The register_checkpassword. */
	public EditText register_name, register_username, register_password,
			register_checkpassword;

	/** The checkpassword_check. */
	public TextView name_check, email_check, password_check,
			checkpassword_check;

	/** The query. */
	public ParseQuery<ParseObject> query = ParseQuery
			.getQuery(ParseDriver.USER_TABLE);

	/** The badge. */
	public ImageView badge;

	/** The dismiss. */
	public static boolean dismiss = false;

	/** The picture request. */
	public static int PICTURE_REQUEST = 1;

	/** The crop request. */
	public static int CROP_REQUEST = 2;

	/** The text. */
	private String text;

	/** The checkpassword_bool. */
	private boolean name_bool = false, email_bool = false,
			password_bool = false, checkpassword_bool = false;

	/** The main. */
	public static MainActivity main;

	/** The man. */
	public static NotificationManager man;

	/** The false dialog. */
	public AlertDialog falseDialog;

	/** The true dialog. */
	public AlertDialog trueDialog;

	/** The photo. */
	private Bitmap photo;

	/**
	 * Inflating the main layout for this activity, finding views by id,
	 * initializing important variables, and adding listeners.
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
		View view = inflater.inflate(R.layout.activity_register, container,
				false);
		main = (MainActivity) getActivity();
		register_name = (EditText) view.findViewById(R.id.register_name);
		register_username = (EditText) view
				.findViewById(R.id.register_username);
		register_password = (EditText) view
				.findViewById(R.id.register_password);
		register_checkpassword = (EditText) view
				.findViewById(R.id.register_checkpassword);
		name_check = (TextView) view.findViewById(R.id.nameCheck);
		email_check = (TextView) view.findViewById(R.id.emailCheck);
		password_check = (TextView) view.findViewById(R.id.passwordCheck);
		checkpassword_check = (TextView) view
				.findViewById(R.id.checkpasswordCheck);
		register_name.addTextChangedListener(new TextWatcher()
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
				text = register_name.getEditableText().toString();
				if (text.matches("([A-Z][a-z]+[ ])([A-Z][a-z]+)"))
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
			 *            - text changed as an Editable
			 * @return void
			 */
			@Override
			public void afterTextChanged(Editable s)
			{
				if (name_bool)
					name_check.setText(Vars.CHECK);
				else
					name_check.setText(Vars.CROSS);

				if (main.check != null)
					if (name_bool && email_bool && password_bool
							&& checkpassword_bool)
						main.check.setEnabled(true);
					else
						main.check.setEnabled(false);
			}
		});

		register_username.addTextChangedListener(new TextWatcher() // register
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
					public void onTextChanged(CharSequence s, int start,
							int before, int count)
					{
						text = register_username.getEditableText().toString();

						if (text.matches(".+@[a-z]+[.](com|edu|org|gov|batman)"))
							email_bool = true;
						else
							email_bool = false;
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
					 * Method called after text is changed
					 * 
					 * @param s
					 *            - text changed as an editable
					 * @return void
					 */
					@Override
					public void afterTextChanged(Editable s)
					{
						if (email_bool)
							email_check.setText(Vars.CHECK);
						else
							email_check.setText(Vars.CROSS);

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
			 * Method called after text is changed
			 * 
			 * @param s
			 *            - the text changed as an editable
			 * @return void
			 */
			@Override
			public void afterTextChanged(Editable arg0)
			{
				if (checkpassword_bool)
					checkpassword_check.setText(Vars.CHECK);
				else
					checkpassword_check.setText(Vars.CROSS);

				if (main.check != null)
					if (name_bool && email_bool && password_bool
							&& checkpassword_bool)
						main.check.setEnabled(true);
					else
						main.check.setEnabled(false);
			}

			/**
			 * Method called before text is changed
			 * 
			 * @param s
			 *            arg0 - text changed
			 * @param arg1
			 *            - starting position
			 * @param arg2
			 *            - count before
			 * @param arg3
			 *            - current count
			 * @return void
			 */
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
			}

			/**
			 * Method called when text is changed
			 * 
			 * @param s
			 *            arg0 - text changed
			 * @param arg1
			 *            - starting position
			 * @param arg2
			 *            - count after
			 * @param arg3
			 *            - current count
			 * @return void
			 */
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				text = register_password.getEditableText().toString();
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
			/**
			 * Method called when text is changed
			 * 
			 * @param arg0
			 *            - the text changed as an editable
			 * @return void
			 */
			@Override
			public void afterTextChanged(Editable arg0)
			{
				if (password_bool)
					password_check.setText(Vars.CHECK);
				else
					password_check.setText(Vars.CROSS);

				if (checkpassword_bool)
					checkpassword_check.setText(Vars.CHECK);
				else
					checkpassword_check.setText(Vars.CROSS);

				if (main.check != null)
					if (name_bool && email_bool && password_bool
							&& checkpassword_bool)
						main.check.setEnabled(true);
					else
						main.check.setEnabled(false);
			}

			/**
			 * Method called before text is changed
			 * 
			 * @param s
			 *            arg0 - text changed
			 * @param arg1
			 *            - starting position
			 * @param arg2
			 *            - count before
			 * @param arg3
			 *            - current count
			 * @return void
			 */
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
			}

			/**
			 * Method called when text is changed
			 * 
			 * @param s
			 *            arg0 - text changed
			 * @param arg1
			 *            - starting position
			 * @param arg2
			 *            - count before
			 * @param arg3
			 *            - current count
			 * @return void
			 */
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
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
			/**
			 * 
			 * called when a click operation is performed on the imageview
			 * 
			 * @param v
			 *            - reference to imageview pressed
			 * @return void
			 * 
			 */
			@Override
			public void onClick(View v)
			{
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

	/**
	 * called automatically when the intent returns to current activity.
	 * 
	 * @param request
	 *            - id of the requested intent
	 * @param result
	 *            - status of
	 * @param data
	 *            - data returned from intent
	 * @return void
	 */

	@Override
	public void onActivityResult(int request, int result, Intent data)
	{
		;
		getActivity();
		if (result == Activity.RESULT_OK)
		{
			if (request == PICTURE_REQUEST)
			{
				Toast.makeText(getActivity(), "Selected", Toast.LENGTH_LONG)
						.show();
				Uri selectedImage = data.getData();
				String path = selectedImage.getPath();
				Log.d("path: ", path);
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

			else if (request == CROP_REQUEST)
			{
				Bundle extras = data.getExtras();
				if (extras != null)
				{
					photo = extras.getParcelable("data");
					badge.setImageBitmap(photo);
					dismiss = false;
				}
			}
		}
	}

	/**
	 * automatically called activity resumes from a pause state.
	 * 
	 * @return void
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		if (dismiss)
		{
			register_name.setText("");
			register_username.setText("");
			register_password.setText("");
			register_checkpassword.setText("");
			dismiss = true;
		}
	}

	/**
	 * called to get user entered input data and query against the database for
	 * duplicate users.
	 * 
	 * @return void
	 */

	public void register()
	{
		final String name = register_name.getEditableText().toString();
		final String username = register_username.getEditableText().toString();
		final String password = register_password.getEditableText().toString();
		query = new ParseQuery<ParseObject>(ParseDriver.USER_TABLE);
		query.whereEqualTo(ParseDriver.USER_EMAIL, username);
		query.findInBackground(new FindCallback<ParseObject>()
		{
			@Override
			public void done(List<ParseObject> usersList, ParseException exe)
			{
				if (usersList != null)
				{
					if (usersList.size() > 0)
					{
						handleRegistration(true, name, username, password);
					}
					else
						handleRegistration(false, name, username, password);
				}
				else
					Log.i("Parse", exe.getMessage());

			}
		});

	}

	/**
	 * called as a handler for entering registration data into the database, and
	 * storing user selected picture onto a file.
	 * 
	 * @param condition
	 *            - whether the username is a duplicate
	 * @param name
	 *            - the name entered by the user
	 * @param username
	 *            - username selected by the user
	 * @param password
	 *            - the password entered by the user
	 * @return int - debugging output for JUnit testing
	 */

	public void handleRegistration(boolean condition, String name,
			String username, String password)
	{
		if (condition)
		{
			falseDialog = new AlertDialog.Builder(getActivity())
					.setMessage("Login Now with Email: " + username + "?")
					.setTitle("Email Alreaddy Registered")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener()
							{
								@Override
								/**
								 * called automatically when the positive button is clicked 
								 * by the user
								 * @param dialog - reference to dialog currently in view
								 * @param which - position of the button clicked in the view
								 * @return void 
								 * call this method when user touches the item 
								 */
								public void onClick(DialogInterface dialog,
										int which)
								{
									if (main != null)
										main.actionBar
												.setSelectedNavigationItem(0);
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener()
							{
								/**
								 * called automatically when the negative button
								 * is clicked by the user
								 * 
								 * @param dialog
								 *            - reference to dialog currently in
								 *            view
								 * @param which
								 *            - position of the button clicked
								 *            in the view
								 * @return void call this method when user
								 *         touches the item
								 */
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									register_username.setText("");
								}
							}).show();
			
		}

		else
		{

			ParseObject user = new ParseObject(ParseDriver.USER_TABLE);
			user.put(ParseDriver.USER_NAME, name);
			user.put(ParseDriver.USER_EMAIL, username);
			user.put(ParseDriver.USER_PASS, password);
			user.put(ParseDriver.USER_TYPE, main.codeEmail(username));
			user.put(ParseDriver.USER_CURRENCY, Vars.currencyParseList.get(13));
			user.saveInBackground();
			ContextWrapper wrapper = new ContextWrapper(getActivity());
			File imagesPath = wrapper.getDir("images", Context.MODE_PRIVATE);
			File filePath = new File(imagesPath, main.normalizeEmail(username));
			FileOutputStream output = null;
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
				Toast.makeText(getActivity(), "Couldn't save photo",
						Toast.LENGTH_SHORT);
				e.printStackTrace();
			
			}
			man = main.NotifyUser(1, "Please verify your Email Address",
					"Click to open Default Email client",
					getActivity().getPackageManager()
							.getLaunchIntentForPackage("com.android.email"));
			sendMessage(name, username, password, main.codeEmail(username));
			trueDialog = new AlertDialog.Builder(getActivity())
					.setTitle("Login Now?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener()
							{
								/**
								 * called automatically when the positive button
								 * is clicked by the user
								 * 
								 * @param dialog
								 *            - reference to dialog currently in
								 *            view
								 * @param which
								 *            - position of the button clicked
								 *            in the view
								 * @return void call this method when user
								 *         touches the item
								 */
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									dismiss = true;
									dialog.dismiss();
									MainActivity main = (MainActivity) getActivity();
									if (main != null)
										main.actionBar
												.setSelectedNavigationItem(0);
								}
							})
					.setNegativeButton("Exit",
							new DialogInterface.OnClickListener()
							{
								/**
								 * called automatically when the positive button
								 * is clicked by the user
								 * 
								 * @param dialog
								 *            - reference to dialog currently in
								 *            view
								 * @param which
								 *            - position of the button clicked
								 *            in the view
								 * @return void call this method when user
								 *         touches the item
								 */
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									getActivity().finish();
								}
							}).show();
		}
	
	}

	/**
	 * called to send a message to the user as verification.
	 * 
	 * @param name
	 *            - the name of the user
	 * @param user
	 *            - the username, also email at which to send to
	 * @param pass
	 *            - the password - sent to user as insurance
	 * @param code
	 *            - a verification code
	 */
	public void sendMessage(String name, String user, String pass, String code)
	{
		String message = Mail
				.EMAIL_FOR_NEW_REGISTRATION(name, user, code, pass);
		Mail mail = new Mail("no.reply.greenbook@gmail.com", "hello world");
		mail.setFrom("no.reply.greenbook@gmail.com");
		mail.setTo(user);
		mail.setSubject("GreenBook email verifcation");
		mail.setMessage(message);
		main.send(mail);
	}
}
