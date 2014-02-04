package com.stacksmashers.greenbook;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{
	EditText login_name;
	EditText login_pass;
	public Cursor caeser;

	DBHelper dbase;
	SQLiteDatabase sqldbase;
	
	public LoginActivity()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
	
		dbase = new DBHelper(getBaseContext());
		sqldbase = dbase.getWritableDatabase();
		
		
		login_name = (EditText)findViewById(R.id.login_name);
		login_pass = (EditText)findViewById(R.id.login_password);
		
		
		
	}

	public void onClickLoginActivityButton(View view)
	{
		String name = login_name.getEditableText().toString();
		String pass = login_pass.getEditableText().toString();
		
		caeser = sqldbase.query(DBHelper.USERS_TABLE, new String[]{DBHelper.USERS_ID, DBHelper.USER_NAME, DBHelper.USER_PASS}, DBHelper.USER_NAME + " = '" + name + "' AND " + DBHelper.USER_PASS + " = '" + pass + "'", null, null, null, null);
				
				/*
				 * fields = sqldbase.query(DBHelper.COURSE_TABLE, new String[]
					{ DBHelper.COURSE_ID, DBHelper.COURSE_NAME,
							DBHelper.COURSE_PROF, DBHelper.AVERAGE_GRADE,
							DBHelper.IS_WEIGHTED, DBHelper.COURSE_HOURS }, DBHelper.COURSE_ID + " = "
						+ ID, null, null, null, null);

				 */
		
		if(caeser.getCount() != 0)
		{
			caeser.moveToFirst();
		
			if(name.equals("mmayer"))
				Toast.makeText(getApplicationContext(), "Admin Found\n", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "User Found\n", Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(),"User not found", Toast.LENGTH_LONG).show();
		}
		
	}


}
