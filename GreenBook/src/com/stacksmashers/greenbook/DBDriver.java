package com.stacksmashers.greenbook;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * The Class DBDriver.
 * 
 * @author Ravish Chawla
 */
public class DBDriver
{

	/** The dbase. */
	public static DBHelper dbase;
	
	/** The sqldbase. */
	public static SQLiteDatabase sqldbase;

	/**
	 * Instantiates a new DB driver.
	 *
	 * @param context the context
	 */
	public DBDriver(Context context)
	{
		dbase = new DBHelper(context);
		sqldbase = dbase.getWritableDatabase();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
	}

	/**
	 * Forget user info.
	 *
	 * @param USER_EMAIL the user email
	 * @return the cursor
	 */
	public static Cursor FORGET_USER_INFO(String USER_EMAIL)
	{
		Cursor cursor = sqldbase.query(
				DBHelper.USER_TABLE, new String[] { DBHelper.USER_NAME,
						DBHelper.USER_TYPE, DBHelper.USER_PASS },
				DBHelper.USER_EMAIL + " = '" + USER_EMAIL + "'", null, null,
				null, null);

		return cursor;
	}

	/**
	 * Login user info.
	 *
	 * @param NAME the name
	 * @param PASS the pass
	 * @return the cursor
	 */
	public static Cursor LOGIN_USER_INFO(String NAME, String PASS)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE, new String[] {
				DBHelper.USERS_ID, DBHelper.USER_EMAIL, DBHelper.USER_PASS },
				DBHelper.USER_EMAIL + " = '" + NAME + "' AND "
						+ DBHelper.USER_PASS + " = '" + PASS + "'", null, null,
				null, null);

		return cursor;
	}

	/**
	 * Gets the user id from email.
	 *
	 * @param email the email
	 * @return the cursor
	 */
	public static Cursor GET_USER_ID_FROM_EMAIL(String email)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE,
				new String[] { DBHelper.USERS_ID }, DBHelper.USER_EMAIL
						+ " = '" + email + "'", null, null, null, null);

		return cursor;
	}

	/**
	 * Spending category report.
	 *
	 * @param USERID the userid
	 * @param START_DATE the start date
	 * @param END_DATE the end date
	 * @return the cursor
	 */
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

	/**
	 * Account name from id.
	 *
	 * @param ACCOUNTSID the accountsid
	 * @return the cursor
	 */
	public static Cursor ACCOUNT_NAME_FROM_ID(int ACCOUNTSID)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_NAME }, DBHelper.ACCOUNT_ID
						+ " = '" + ACCOUNTSID + " '", null, null, null, null);

		return cursor;

	}

	/**
	 * Gets the user type.
	 *
	 * @param USERID the userid
	 * @return the cursor
	 */
	public static Cursor GET_USER_TYPE(int USERID)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE,
				new String[] { DBHelper.USER_TYPE }, DBHelper.USERS_ID + " = '"
						+ USERID + "'", null, null, null, null);

		return cursor;
	}

	/**
	 * Gets the all users.
	 *
	 * @return the cursor
	 */
	public static Cursor GET_ALL_USERS()
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE, new String[] {
				DBHelper.USER_NAME, DBHelper.USER_EMAIL }, null, null, null,
				null, null);
		return cursor;
	}

	/**
	 * Check duplicate users.
	 *
	 * @param USERNAME the username
	 * @return the cursor
	 */
	public static Cursor CHECK_DUPLICATE_USERS(String USERNAME)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE, new String[] {
				DBHelper.USERS_ID, DBHelper.USER_EMAIL }, DBHelper.USER_EMAIL
				+ " = '" + USERNAME + "'", null, null, null, null);

		return cursor;
	}

	/**
	 * Update user type.
	 *
	 * @param type the type
	 * @param USERID the userid
	 */
	public static void UPDATE_USER_TYPE(String type, int USERID)
	{
		ContentValues brutus = new ContentValues(); // get new contentvalyes
													// from brutus
		brutus.put(DBHelper.USER_TYPE, type);
		sqldbase.update(DBHelper.USER_TABLE, brutus, DBHelper.USERS_ID + " = '"
				+ USERID + "'", null);
	}

	/**
	 * Gets the account transactions.
	 *
	 * @param ACCOUNTSID the accountsid
	 * @param USERID the userid
	 * @return the cursor
	 */
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

	/**
	 * Gets the account info for user.
	 *
	 * @param USERID the userid
	 * @return the cursor
	 */
	public static Cursor GET_ACCOUNT_INFO_FOR_USER(int USERID)
	{
		Cursor caeser = sqldbase.query(DBHelper.ACCOUNT_TABLE, new String[] {
				DBHelper.ACCOUNT_ID, DBHelper.ACCOUNT_NAME },
				DBHelper.ACCOUNT_USER + " = '" + USERID + "'", null, null,
				null, null);

		return caeser;

	}

	/**
	 * Gets the account from id.
	 *
	 * @param ACCOUNTSID the accountsid
	 * @return the cursor
	 */
	public static Cursor GET_ACCOUNT_FROM_ID(long ACCOUNTSID)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_NAME }, DBHelper.ACCOUNT_ID
						+ " = '" + ACCOUNTSID + " '", null, null, null, null);

		return cursor;
	}

	/**
	 * Insert transaction.
	 *
	 * @param cValues the c values
	 */
	public static void INSERT_TRANSACTION(ContentValues cValues)
	{
		sqldbase.insert(DBHelper.TRANSACTION_TABLE, null, cValues);
	}

	/**
	 * Insert deposit.
	 *
	 * @param SOURCE the source
	 * @param balance the balance
	 * @param USERID the userid
	 * @param ACCOUNT_NAME the account name
	 * @param ACCOUNTSID the accountsid
	 */
	public static void INSERT_DEPOSIT(String SOURCE, Double balance, int USERID,
			String ACCOUNT_NAME, int ACCOUNTSID)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.TRANSACTION_DEPOSIT_SOURCE, SOURCE);

		cValues.put(DBHelper.TRANSACTION_VALUE, balance);
		cValues.put(DBHelper.TRANSACTION_USER, USERID);
		cValues.put(DBHelper.TRANSACTION_ACCOUNT_NAME, ACCOUNT_NAME);
		cValues.put(DBHelper.TRANSACTION_ACCOUNT, ACCOUNTSID);
		cValues.put(DBHelper.TRANSACTION_POSTED,
				Utility.dateFormat.format(new Date()));
		sqldbase.insert(DBHelper.TRANSACTION_TABLE, null, cValues);
	}

	/**
	 * Insert withrawal.
	 *
	 * @param CATEGORY the category
	 * @param REASON the reason
	 * @param balance the balance
	 * @param USERID the userid
	 * @param ACCOUNT_NAME the account name
	 * @param ACCOUNTSID the accountsid
	 */
	public static void INSERT_WITHRAWAL(String CATEGORY, String REASON,
			Double balance, int USERID, String ACCOUNT_NAME, int ACCOUNTSID)
	{
		balance = (-1) * balance;
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.TRANSACTION_CATEGORY, CATEGORY);
		cValues.put(DBHelper.TRANSACTION_WITHRAWAL_REASON, REASON);
		cValues.put(DBHelper.TRANSACTION_VALUE, balance);
		cValues.put(DBHelper.TRANSACTION_USER, USERID);
		cValues.put(DBHelper.TRANSACTION_ACCOUNT_NAME, ACCOUNT_NAME);
		cValues.put(DBHelper.TRANSACTION_ACCOUNT, ACCOUNTSID);
		cValues.put(DBHelper.TRANSACTION_POSTED,
				Utility.dateFormat.format(new Date()));
		sqldbase.insert(DBHelper.TRANSACTION_TABLE, null, cValues);
	}

	/**
	 * Gets the accounts ids for user.
	 *
	 * @param USERID the userid
	 * @return the cursor
	 */
	public static Cursor GET_ACCOUNTS_IDS_FOR_USER(int USERID)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_ID }, DBHelper.ACCOUNT_USER
						+ " = '" + USERID + "'", null, null, null, null, null);

		return cursor;

	}

	/**
	 * Gets the account balance.
	 *
	 * @param USERID the userid
	 * @param ACCOUNTSID the accountsid
	 * @return the cursor
	 */
	public static Cursor GET_ACCOUNT_BALANCE(int USERID, int ACCOUNTSID)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_BALANCE },
				DBHelper.ACCOUNT_USER + " = '" + USERID + "' AND "
						+ DBHelper.ACCOUNT_ID + " = '" + ACCOUNTSID + "'",
				null, null, null, null, null);

		return cursor;

	}

	/**
	 * Update balance for account.
	 *
	 * @param BALANCE the balance
	 * @param USERID the userid
	 * @param ACCOUNTID the accountid
	 */
	public static void UPDATE_BALANCE_FOR_ACCOUNT(int BALANCE, int USERID,
			int ACCOUNTID)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.ACCOUNT_BALANCE, BALANCE);
		sqldbase.update(DBHelper.ACCOUNT_TABLE, cValues, DBHelper.ACCOUNT_USER
				+ " = '" + USERID + "' AND " + DBHelper.ACCOUNT_ID
				+ " = '"
				+ ACCOUNTID + "'", null);
	}

	/**
	 * Insert user.
	 *
	 * @param USER_NAME the user name
	 * @param USER_EMAIL the user email
	 * @param USER_PASS the user pass
	 * @param USER_TYPE the user type
	 * @param USER_CURRENCY the user currency
	 */
	public static void INSERT_USER(String USER_NAME, String USER_EMAIL,
			String USER_PASS, String USER_TYPE, int USER_CURRENCY)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.USER_NAME, USER_NAME);
		cValues.put(DBHelper.USER_EMAIL, USER_EMAIL);
		cValues.put(DBHelper.USER_PASS, USER_PASS);
		cValues.put(DBHelper.USER_TYPE, USER_TYPE);
		cValues.put(DBHelper.USER_CURRENCY, USER_CURRENCY);
		sqldbase.insert(DBHelper.USER_TABLE, null, cValues);
	}

	/**
	 * Check for duplicate accounts.
	 *
	 * @param USERID the userid
	 * @param ACCOUNTS_NAME the accounts name
	 * @return the cursor
	 */
	public static Cursor CHECK_FOR_DUPLICATE_ACCOUNTS(int USERID,
			String ACCOUNTS_NAME)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_NAME }, DBHelper.ACCOUNT_USER
						+ " = '" + USERID + "' AND " + DBHelper.ACCOUNT_NAME
						+ " = '" + ACCOUNTS_NAME + "'", null, null, null, null);

		return cursor;
	}

	/**
	 * Insert account.
	 *
	 * @param ACCOUNT_NAME the account name
	 * @param ACCOUNT_BALANCE the account balance
	 * @param ACCOUNT_BANK the account bank
	 * @param ACCOUNT_USER the account user
	 */
	public static void INSERT_ACCOUNT(String ACCOUNT_NAME,
			String ACCOUNT_BALANCE, String ACCOUNT_BANK,
			int ACCOUNT_USER)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.ACCOUNT_NAME, ACCOUNT_NAME);
		cValues.put(DBHelper.ACCOUNT_BALANCE, ACCOUNT_BALANCE);
		cValues.put(DBHelper.ACCOUNT_BANK, ACCOUNT_BANK);
		cValues.put(DBHelper.ACCOUNT_USER, ACCOUNT_USER);
		sqldbase.insert(DBHelper.ACCOUNT_TABLE, null, cValues);
	}
	
	/**
	 * Insert account.
	 *
	 * @param ACCOUNT_NAME the account name
	 * @param ACCOUNT_BALANCE the account balance
	 * @param ACCOUNT_BANK the account bank
	 * @param ACCOUNT_USER the account user
	 * @param ACCOUNT_INTEREST the account interest
	 */
	public static void INSERT_ACCOUNT(String ACCOUNT_NAME,
			String ACCOUNT_BALANCE, String ACCOUNT_BANK,
			int ACCOUNT_USER, String ACCOUNT_INTEREST)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.ACCOUNT_NAME, ACCOUNT_NAME);
		cValues.put(DBHelper.ACCOUNT_BALANCE, ACCOUNT_BALANCE);
		cValues.put(DBHelper.ACCOUNT_BANK, ACCOUNT_BANK);
		cValues.put(DBHelper.ACCOUNT_USER, ACCOUNT_USER);
		cValues.put(DBHelper.ACCOUNT_INTEREST, ACCOUNT_INTEREST);
		sqldbase.insert(DBHelper.ACCOUNT_TABLE, null, cValues);
	}

	/**
	 * Gets the all account info.
	 *
	 * @param USERID the userid
	 * @param SORT the sort
	 * @return the cursor
	 */
	public static Cursor GET_ALL_ACCOUNT_INFO(int USERID, String SORT)
	{
		String query[] = { DBHelper.ACCOUNT_ID, DBHelper.ACCOUNT_NAME,
				DBHelper.ACCOUNT_BANK, DBHelper.ACCOUNT_BALANCE};

		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE, query,
				DBHelper.ACCOUNT_USER + " = '" + USERID + "'", null, null,
				null, SORT);

		return cursor;

	}

	/**
	 * Gets the default currency.
	 *
	 * @param userid the userid
	 * @return the cursor
	 */
	public static Cursor GET_DEFAULT_CURRENCY(int userid)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE, new String[] { DBHelper.USER_CURRENCY }, DBHelper.USERS_ID + " = '" + userid + "'", null,null,null,null);
		
		return cursor;
	}

	/**
	 * Update currencies.
	 *
	 * @param exchangeValues the exchange values
	 */
	public static void UPDATE_CURRENCIES(ArrayList<Double> exchangeValues)
	{
		ContentValues cValues = new ContentValues();
		int key = 1;
		for (Double d : exchangeValues)
		{
			cValues.clear();
			cValues.put(DBHelper.CURRENCY_VALUE, d);
			sqldbase.update(DBHelper.CURRENCY_TABLE, cValues,
					DBHelper.CURRENCY_ID + " = '" + key + "'", null);
			key++;
		}
	}

	/**
	 * Update default currency.
	 *
	 * @param user_id the user_id
	 * @param currency the currency
	 */
	public static void UPDATE_DEFAULT_CURRENCY(int user_id, int currency)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.USER_CURRENCY, currency);
		sqldbase.update(DBHelper.USER_TABLE, cValues, DBHelper.USERS_ID + " = '" + user_id + "'", null);
	}
	
	/**
	 * Gets the all currencies.
	 *
	 * @return the cursor
	 */
	public static Cursor GET_ALL_CURRENCIES()
	{
		Cursor cursor = sqldbase.query(DBHelper.CURRENCY_TABLE, new String[] {
				DBHelper.CURRENCY_NAME, DBHelper.CURRENCY_TICKER,
				DBHelper.CURRENCY_SYMBOL, DBHelper.CURRENCY_VALUE , DBHelper.CURRENCY_ID},
				null, null, null, null, null);
		return cursor;
	}
	
	/**
	 * Gets the currency.
	 *
	 * @param currency_id the currency_id
	 * @return the cursor
	 */
	public static Cursor GET_CURRENCY(int currency_id)
	{
		Cursor cursor = sqldbase.query(DBHelper.CURRENCY_TABLE, new String[] {
				DBHelper.CURRENCY_NAME, DBHelper.CURRENCY_TICKER,
				DBHelper.CURRENCY_SYMBOL, DBHelper.CURRENCY_VALUE },
				DBHelper.CURRENCY_ID + " = '" + currency_id + "'", null, null, null, null);
		return cursor;
	}
	
	/**
	 * Insert currency.
	 *
	 * @param name the name
	 * @param ticker the ticker
	 * @param string the string
	 */
	public static void INSERT_CURRENCY(String name, String ticker, String string)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.CURRENCY_NAME, name);
		cValues.put(DBHelper.CURRENCY_TICKER, ticker);
		cValues.put(DBHelper.CURRENCY_SYMBOL, string);
		sqldbase.insert(DBHelper.CURRENCY_TABLE, null, cValues);
	}

	/**
	 * Update transaction values.
	 *
	 * @param user_id the user_id
	 * @param double1 the double1
	 */
	public static void UPDATE_TRANSACTION_VALUES(int user_id, Double double1)
	{
		sqldbase.execSQL("UPDATE " + DBHelper.TRANSACTION_TABLE + " SET "
				+ DBHelper.TRANSACTION_VALUE + " = "
				+ DBHelper.TRANSACTION_VALUE + " * " + double1 + " WHERE "
				+ DBHelper.TRANSACTION_USER + " = " + user_id);

		sqldbase.execSQL("UPDATE " + DBHelper.ACCOUNT_TABLE + " SET "
				+ DBHelper.ACCOUNT_BALANCE + " = " + DBHelper.ACCOUNT_BALANCE
				+ " * " + double1 + " WHERE " + DBHelper.ACCOUNT_USER
				+ " = " + user_id);
	}
}
