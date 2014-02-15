package com.stacksmashers.greenbook;


import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity
{

	EditText register_name, register_username, register_password, register_checkpassword;
	TextView name_check, email_check, password_check, checkpassword_check;
	Button register_button;
	/**
	 * @param args
	 */
	
	DBHelper dbase;
	SQLiteDatabase sqldbase;
	String text;
	boolean name_bool = false, email_bool = false, password_bool = false, checkpassword_bool = false;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_register);
	
		dbase = new DBHelper(getBaseContext());
		sqldbase = dbase.getWritableDatabase();
	
		
		register_name = (EditText)findViewById(R.id.register_name);
		register_username = (EditText)findViewById(R.id.register_username);
		register_password = (EditText)findViewById(R.id.register_password);
		register_checkpassword = (EditText)findViewById(R.id.register_checkpassword);
		
		name_check = (TextView)findViewById(R.id.nameCheck);
		email_check = (TextView)findViewById(R.id.emailCheck);
		password_check = (TextView)findViewById(R.id.passwordCheck);
		checkpassword_check = (TextView)findViewById(R.id.checkpasswordCheck);
		
		
		
		register_button = (Button)findViewById(R.id.register_button);
		register_button.setEnabled(false);
		
		register_name.addTextChangedListener(new TextWatcher()
		{
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				
				
				text = register_name.getEditableText().toString();
				// TODO Auto-generated method stub
				if(text.matches("([A-Z][a-z]+[ ])([A-Z][a-z]+)"))
					name_bool = true;
				else
					name_bool = false;
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s)
			{
				// TODO Auto-generated method stub
				
				if(name_bool)
					name_check.setText("✔");
				else
					name_check.setText("✗");

				if(name_bool && email_bool && password_bool && checkpassword_bool)
					register_button.setEnabled(true);
				else
					register_button.setEnabled(false);
					
			
			}
		});
		
		
		register_username.addTextChangedListener(new TextWatcher()
		{
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				// TODO Auto-generated method stub
				
				text = register_username.getEditableText().toString();
				// TODO Auto-generated method stub
				if(text.matches(".+@[a-z]+[.]com"))
					email_bool = true;
				else
					email_bool = false;
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s)
			{
				// TODO Auto-generated method stub
				
				if(email_bool)
					email_check.setText("✔");
				else
					email_check.setText("✗");	
					
				if(name_bool && email_bool && password_bool && checkpassword_bool)
					register_button.setEnabled(true);
				else
					register_button.setEnabled(false);
				
				
			}
		});
		
		
		register_checkpassword.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0)
			{
				if(checkpassword_bool)
					checkpassword_check.setText("✔");
				else
					checkpassword_check.setText("✗");
		
				if(name_bool && email_bool && password_bool && checkpassword_bool)
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
				//text = register_password.getEditableText();
				// TODO Auto-generated method stub
				if(register_password.getEditableText().toString().equals(register_checkpassword.getEditableText().toString()))
					checkpassword_bool = true;
				else
					checkpassword_bool = false;
				
				
				
			}
			
			
			
		});

		register_password.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0)
			{

				if(password_bool)
					password_check.setText("✔");
				else
					password_check.setText("✗");
		
				if(checkpassword_bool)
					checkpassword_check.setText("✔");
				else
					checkpassword_check.setText("✗");
		
				
				if(name_bool && email_bool && password_bool && checkpassword_bool)
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
				
				//text = register_checkpassword.getEditableText().toString();
				// TODO Auto-generated method stub
				if(register_password.getEditableText().toString().matches(".+"))
					password_bool = true;
				else
					password_bool = false;
				
				if(register_password.getEditableText().toString().equals(register_checkpassword.getEditableText().toString()))
					checkpassword_bool = true;
				else
					checkpassword_bool = false;
				
				
			}
			
			
			
		});

		
		
	}

	public void onClickRegisterActivityButton(View view)
	{
		String name = register_name.getEditableText().toString();
		String username = register_username.getEditableText().toString();
		String password = register_password.getEditableText().toString();
		String checkPassword = register_checkpassword.getEditableText().toString();
		
		
		
		ContentValues brutus = new ContentValues();
		brutus.put(DBHelper.USER_NAME, username);
		brutus.put(DBHelper.USER_PASS, password);
		
		sqldbase.insert(DBHelper.USER_TABLE, null, brutus);
		Toast.makeText(getApplicationContext(), "User added to database", Toast.LENGTH_LONG).show();
		
		
		}

		
	
}
