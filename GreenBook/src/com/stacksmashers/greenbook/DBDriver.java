package com.stacksmashers.greenbook;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBDriver
{

	static DBHelper dbase;
	static SQLiteDatabase sqldbase;

	public DBDriver(Context context)
	{
		// TODO Auto-generated constructor stub

		dbase = new DBHelper(context);
		sqldbase = dbase.getWritableDatabase();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	public static Cursor FORGET_USER_INFO(String USER_EMAIL)
	{
		Cursor cursor = sqldbase.query(
				// use dbuser table
				DBHelper.USER_TABLE, new String[] { DBHelper.USER_NAME,
						DBHelper.USER_TYPE, DBHelper.USER_PASS },
				DBHelper.USER_EMAIL + " = '" + USER_EMAIL + "'", null, null,
				null, null);

		return cursor;

	}

	public static Cursor LOGIN_USER_INFO(String NAME, String PASS)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE, new String[] {
				DBHelper.USERS_ID, DBHelper.USER_EMAIL, DBHelper.USER_PASS },
				DBHelper.USER_EMAIL + " = '" + NAME + "' AND "
						+ DBHelper.USER_PASS + " = '" + PASS + "'", null, null,
				null, null);

		return cursor;

	}

	public static Cursor GET_USER_FROM_ACCOUNt(String accountsUser)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE,
				new String[] { DBHelper.USERS_ID }, DBHelper.USER_EMAIL
						+ " = '" + accountsUser + "'", null, null, null, null);

		return cursor;
	}

	public static Cursor SPENDING_CATEGORY_REPORT(int USERID,
			String START_DATE, String END_DATE)
	{
		String sumTransBalances = "Sum(" + DBHelper.TRANSACTION_VALUE + ")";

		Cursor cursor = sqldbase.query(DBHelper.TRANSACTION_TABLE,
				new String[] { DBHelper.TRANSACTION_ID,
						DBHelper.TRANSACTION_CATEGORY, sumTransBalances },
				DBHelper.TRANSACTION_USER + " = '" + USERID + "' AND "
						+ DBHelper.TRANSACTION_POSTED + " BETWEEN '"
						+ START_DATE + "' AND '" + END_DATE + "' AND "
						+ DBHelper.TRANSACTION_VALUE + " < '0'", null,
				DBHelper.TRANSACTION_CATEGORY, null, null);

		return cursor;

	}

	public static Cursor ACCOUNT_NAME_FROM_ID(int ACCOUNTSID)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_NAME }, DBHelper.ACCOUNT_ID
						+ " = '" + ACCOUNTSID + " '", null, null, null, null);

		return cursor;

	}

	public static Cursor GET_USER_TYPE(int USERID)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE,
				new String[] { DBHelper.USER_TYPE }, DBHelper.USERS_ID + " = '"
						+ USERID + "'", null, null, null, null);

		return cursor;
	}

	public static Cursor GET_ALL_USERS()
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE, new String[] {
				DBHelper.USER_NAME, DBHelper.USER_EMAIL }, null, null, null,
				null, null);
		return cursor;
	}

	public static Cursor CHECK_DUPLICATE_USERS(String USERNAME)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE, new String[] {
				DBHelper.USERS_ID, DBHelper.USER_EMAIL }, DBHelper.USER_EMAIL
				+ " = '" + USERNAME + "'", null, null, null, null);

		return cursor;
	}

	public static void UPDATE_USER_TYPE(String type, int USERID)
	{
		ContentValues brutus = new ContentValues(); // get new contentvalyes
													// from brutus
		brutus.put(DBHelper.USER_TYPE, type);
		sqldbase.update(DBHelper.USER_TABLE, brutus, DBHelper.USERS_ID + " = '"
				+ USERID + "'", null);
	}

	public static Cursor GET_ACCOUNT_TRANSACTIONS(int ACCOUNTSID, int USERID)
	{
		String condition = DBHelper.TRANSACTION_USER + " = '" + USERID
				+ "' AND " + DBHelper.TRANSACTION_ACCOUNT + " ='" + ACCOUNTSID
				+ "'";

		Cursor cursor = sqldbase
				.query(DBHelper.TRANSACTION_TABLE,
						new String[] { DBHelper.TRANSACTION_ID,
								DBHelper.TRANSACTION_USER,
								DBHelper.TRANSACTION_ACCOUNT,
								DBHelper.TRANSACTION_ACCOUNT_NAME,
								DBHelper.TRANSACTION_DEPOSIT_SOURCE,
								DBHelper.TRANSACTION_WITHRAWAL_REASON,
								DBHelper.TRANSACTION_CATEGORY,
								DBHelper.TRANSACTION_POSTED,
								DBHelper.TRANSACTION_VALUE }, condition, null,
						null, null, null);

		return cursor;

	}

	public static Cursor GET_ACCOUNT_INFO_FOR_USER(int USERID)
	{
		Cursor caeser = sqldbase.query(DBHelper.ACCOUNT_TABLE, new String[] {
				DBHelper.ACCOUNT_ID, DBHelper.ACCOUNT_NAME },
				DBHelper.ACCOUNT_USER + " = '" + USERID + "'", null, null,
				null, null);

		return caeser;

	}

	public static Cursor GET_ACCOUNT_FROM_ID(long ACCOUNTSID)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_NAME }, DBHelper.ACCOUNT_ID
						+ " = '" + ACCOUNTSID + " '", null, null, null, null);

		return cursor;
	}

	public static void INSERT_TRANSACTION(ContentValues cValues)
	{
		sqldbase.insert(DBHelper.TRANSACTION_TABLE, null, cValues);
	}

	public static void INSERT_DEPOSIT(String SOURCE, int VALUE, int USERID,
			String ACCOUNT_NAME, int ACCOUNTSID)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.TRANSACTION_DEPOSIT_SOURCE, SOURCE);

		cValues.put(DBHelper.TRANSACTION_VALUE, VALUE);
		cValues.put(DBHelper.TRANSACTION_USER, USERID);
		cValues.put(DBHelper.TRANSACTION_ACCOUNT_NAME, ACCOUNT_NAME);
		cValues.put(DBHelper.TRANSACTION_ACCOUNT, ACCOUNTSID);
		cValues.put(DBHelper.TRANSACTION_POSTED,
				Utility.dateFormat.format(new Date()));

		sqldbase.insert(DBHelper.TRANSACTION_TABLE, null, cValues);

	}

	public static void INSERT_WITHRAWAL(String CATEGORY, String REASON,
			int VALUE, int USERID, String ACCOUNT_NAME, int ACCOUNTSID)
	{
		VALUE = (-1) * VALUE;
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.TRANSACTION_CATEGORY, CATEGORY);
		cValues.put(DBHelper.TRANSACTION_WITHRAWAL_REASON, REASON);
		cValues.put(DBHelper.TRANSACTION_VALUE, VALUE);
		cValues.put(DBHelper.TRANSACTION_USER, USERID);
		cValues.put(DBHelper.TRANSACTION_ACCOUNT_NAME, ACCOUNT_NAME);
		cValues.put(DBHelper.TRANSACTION_ACCOUNT, ACCOUNTSID);
		cValues.put(DBHelper.TRANSACTION_POSTED,
				Utility.dateFormat.format(new Date()));

		sqldbase.insert(DBHelper.TRANSACTION_TABLE, null, cValues);

	}

	public static Cursor GET_ACCOUNTS_IDS_FOR_USER(int USERID)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_ID }, DBHelper.ACCOUNT_USER
						+ " = '" + USERID + "'", null, null, null, null, null);

		return cursor;

	}

	public static Cursor GET_ACCOUNT_BALANCE(int USERID, int ACCOUNTSID)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_BALANCE },
				DBHelper.ACCOUNT_USER + " = '" + USERID + "' AND "
						+ DBHelper.ACCOUNT_ID + " = '" + ACCOUNTSID + "'",
				null, null, null, null, null);

		return cursor;

	}

	public static void UPDATE_BALANCE_FOR_ACCOUNT(int BALANCE, int USERID,
			int ACCOUNTID)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.ACCOUNT_BALANCE, BALANCE);

		sqldbase.update(DBHelper.ACCOUNT_TABLE, cValues, DBHelper.ACCOUNT_USER // update
				// sqldbase
				+ " = '" + USERID + "' AND " + DBHelper.ACCOUNT_ID
				+ " = '"
				+ ACCOUNTID + "'", null);

	}

	public static void INSERT_USER(String USER_NAME, String USER_EMAIL, String USER_PASS, String USER_TYPE)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.USER_NAME, USER_NAME);
		cValues.put(DBHelper.USER_EMAIL, USER_EMAIL);
		cValues.put(DBHelper.USER_PASS, USER_PASS);
		cValues.put(DBHelper.USER_TYPE, USER_TYPE);
		
		sqldbase.insert(DBHelper.USER_TABLE, null, cValues);
	}
	
	public static Cursor CHECK_FOR_DUPLICATE_ACCOUNTS(int USERID, String ACCOUNTS_NAME)
	{
	
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_NAME },
				DBHelper.ACCOUNT_USER + " = '" + USERID
						+ "' AND " + DBHelper.ACCOUNT_NAME
						+ " = '"
						+ ACCOUNTS_NAME + "'",
				null, null, null, null);

		return cursor;
		
	}
	
	public static void INSERT_ACCOUNT(String ACCOUNT_NAME, String ACCOUNT_BALANCE, String ACCOUNT_BANK,  String ACCOUNT_COLOR, int ACCOUNT_USER, String ACCOUNT_INTEREST)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.ACCOUNT_NAME, ACCOUNT_NAME);
		cValues.put(DBHelper.ACCOUNT_BALANCE, ACCOUNT_BALANCE);
		cValues.put(DBHelper.ACCOUNT_BANK, ACCOUNT_BANK);
		cValues.put(DBHelper.ACCOUNT_COLOR, ACCOUNT_COLOR);
				
		cValues.put(DBHelper.ACCOUNT_USER, ACCOUNT_USER);
		
		sqldbase.insert(DBHelper.ACCOUNT_TABLE, null, cValues);
		
		
	}
	
	public static Cursor GET_ALL_ACCOUNT_INFO(int USERID, String SORT)
	{
		String query[] = { DBHelper.ACCOUNT_ID, DBHelper.ACCOUNT_NAME,
				DBHelper.ACCOUNT_BANK, DBHelper.ACCOUNT_BALANCE,
				DBHelper.ACCOUNT_COLOR };
		
		 Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE, query,
				DBHelper.ACCOUNT_USER + " = '" + USERID + "'", null, null,
				null, SORT);
		
		return cursor;

	}

	
}
