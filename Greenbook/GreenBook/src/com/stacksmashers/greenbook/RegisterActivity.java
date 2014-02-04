package com.stacksmashers.greenbook;


import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity
{

	EditText register_name, register_username, register_password, register_checkpassword;
	/**
	 * @param args
	 */
	
	DBHelper dbase;
	SQLiteDatabase sqldbase;

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
		
	}

	public void onClickRegisterActivityButton(View view)
	{
		String name = register_name.getEditableText().toString();
		String username = register_username.getEditableText().toString();
		String password = register_password.getEditableText().toString();
		String checkPassword = register_checkpassword.getEditableText().toString();
		
		if(password.equals(checkPassword)){
		
		
		
		ContentValues brutus = new ContentValues();
		brutus.put(DBHelper.USER_NAME, username);
		brutus.put(DBHelper.USER_PASS, password);
		
		sqldbase.insert(DBHelper.USERS_TABLE, null, brutus);
		Toast.makeText(getApplicationContext(), "User added to database", Toast.LENGTH_LONG).show();
		
		
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG).show();
		}
		
		
	}
	
}
