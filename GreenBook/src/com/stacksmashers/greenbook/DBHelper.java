package com.stacksmashers.greenbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static final String Database_Name = "main_database.db";
	static  int version = 1;
	
	public static final String _id = "_id";
	public static final String USERS_TABLE = "USERS";
	public static final String USERS_ID = USERS_TABLE + "._id";
	public static final String USER_NAME = "USERS_NAME";
	public static final String USER_PASS = "USER_PASS";
	

	public static final String TAG = "DBHelper";
	
	//public static int LETTER_GRADING = 0x0;
	//public static int PASS_FAIL = 0x1;
	

	public DBHelper(Context context)
	{
		super(context, Database_Name, null, version);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
	    super.onOpen(db);
	    if (!db.isReadOnly()) {
	        // Enable foreign key constraints
	        db.execSQL("PRAGMA foreign_keys=ON;");
	    }
	}




	@Override
	public void onCreate(SQLiteDatabase dbse) {
		// TODO Auto-generated method stub
		
		//Queries for creating the four tables
		final String createUserTable = "CREATE TABLE " + USERS_TABLE + "( " + _id + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
																						  USER_NAME + " TEXT," +
																						  USER_PASS + " TEXT" + ");";
		
					
	
		Log.i(TAG,"SQL Statements created");
		dbse.execSQL(createUserTable);
		

	}


	@Override
	public void onUpgrade(SQLiteDatabase dbse, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	
	}

}
