package com.stacksmashers.greenbook;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * The Class ParseDriver.
 * 
 * @author Ravish Chawla
 */
public class ParseDriver
{

	/** The Constant _id. */
	public static final String _id = "_id";

	/** The Constant OBJECT_ID. */
	public static final String OBJECT_ID = "objectId";

	/** The Constant CREATED_AT. */
	public static final String CREATED_AT = "createdAt";

	/** The Constant USER_TABLE. */
	public static final String USER_TABLE = "USERS";

	/** The Constant USERS_ID. */
	public static final String USERS_ID = USER_TABLE + "._id";

	/** The Constant USER_TYPE. */
	public static final String USER_TYPE = "USER_TYPE";

	/** The Constant USER_NAME. */
	public static final String USER_NAME = "USER_NAME";

	/** The Constant USER_DISPLAY_NAME. */
	public static final String USER_DISPLAY_NAME = "USER_DISPLAY_NAME";

	/** The Constant USER_EMAIL. */
	public static final String USER_EMAIL = "USER_EMAIL";

	/** The Constant USER_PIC. */
	public static final String USER_PIC = "USER_PIC";

	/** The Constant USER_PASS. */
	public static final String USER_PASS = "USER_PASS";

	/** The Constant USER_CURRENCY. */
	public static final String USER_CURRENCY = "USER_CURRENCY";

	/** The Constant USER_ACCOUNT. */
	public static final String USER_ACCOUNT = "USER_ACCOUNT";

	/** The Constant USER_TRANSACTION. */
	public static final String USER_TRANSACTION = "USER_TRANSACTION";

	/** The Constant ACCOUNT_TABLE. */
	public static final String ACCOUNT_TABLE = "ACCOUNTS";

	/** The Constant ACCOUNT_ID. */
	public static final String ACCOUNT_ID = ACCOUNT_TABLE + "._id";

	/** The Constant ACCOUNT_NAME. */
	public static final String ACCOUNT_NAME = "ACCOUNT_NAME";

	/** The Constant ACCOUNT_USER. */
	public static final String ACCOUNT_USER = "ACCOUNT_USER";

	/** The Constant ACCOUNT_BALANCE. */
	public static final String ACCOUNT_BALANCE = "ACCOUNT_BALANCE";

	/** The Constant ACCOUNT_INTEREST. */
	public static final String ACCOUNT_INTEREST = "ACCOUNT_INTEREST";

	/** The Constant ACCOUNT_BANK. */
	public static final String ACCOUNT_BANK = "ACCOUNT_BANK";

	/** The Constant TRANSACTION_TABLE. */
	public static final String TRANSACTION_TABLE = "TRANSACTIONS";

	/** The Constant ACCOUNT_TRANSACTION. */
	public static final String ACCOUNT_TRANSACTION = "ACCOUNT_TRANSACTION";

	/** The Constant TRANSACTION_ID. */
	public static final String TRANSACTION_ID = TRANSACTION_TABLE + "._id";

	/** The Constant TRANSACTION_USER. */
	public static final String TRANSACTION_USER = "TRANSACTION_USER";

	/** The Constant TRANSACTION_ACCOUNT. */
	public static final String TRANSACTION_ACCOUNT = "TRANSACTION_ACCOUNT";

	/** The Constant TRANSACTION_ACCOUNT_NAME. */
	public static final String TRANSACTION_ACCOUNT_NAME = "TRANSACTION_ACCOUNT_NAME";

	/** The Constant TRANSACTION_NAME. */
	public static final String TRANSACTION_NAME = "TRANSACTION_NAME";

	/** The Constant TRANSACTION_VALUE. */
	public static final String TRANSACTION_VALUE = "TRANSACTION_VALUE";

	/** The Constant TRANSACTION_WITHRAWAL_REASON. */
	public static final String TRANSACTION_WITHRAWAL_REASON = "TRANSACTION_WITHRAWAL_REASON";

	/** The Constant TRANSACTION_CATEGORY. */
	public static final String TRANSACTION_CATEGORY = "TRANSACTION_CATEGORY";

	/** The Constant TRANSACTION_POSTED. */
	public static final String TRANSACTION_POSTED = "TRANSACTION_POSTED";

	/** The Constant TRANSACTION_AFFECTED. */
	public static final String TRANSACTION_AFFECTED = "TRANSACTION_AFFECTED";

	/** The Constant CURRENCY_TABLE. */
	public static final String CURRENCY_TABLE = "CURRENCIES";

	/** The Constant CURRENCY_ID. */
	public static final String CURRENCY_ID = CURRENCY_TABLE + "._id";

	/** The Constant CURRENCY_NAME. */
	public static final String CURRENCY_NAME = "CURRENCY_NAME";

	/** The Constant CURRENCY_TICKER. */
	public static final String CURRENCY_TICKER = "CURRENCY_TICKER";

	/** The Constant CURRENCY_SYMBOL. */
	public static final String CURRENCY_SYMBOL = "CURRENCY_SYMBOL";

	/** The Constant CURRENCY_VALUE. */
	public static final String CURRENCY_VALUE = "CURRENCY_VALUE";

	/** The list. */
	public static List<ParseObject> list;

	/**
	 * Instantiates a new parses the driver.
	 * 
	 * @param context
	 *            the context
	 * @param intent
	 *            the intent
	 */
	public ParseDriver(Context context, Intent intent)
	{
		Parse.initialize(context, "Eo7ASw57FOevkYiO6HnUKkJJauSWU64UdTwihpqP",
				"5pE1jsBnG5yjjCV97vVZwZpgEixnfMg4Fl5sYDwB");
		ParseAnalytics.trackAppOpened(intent);
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{
	}

	
	/**
	 * A method left blank for future use
	 * 
	 * @param _NAME
	 *            the name
	 * @param _PASS
	 *            the pass
	 * @return the list
	 */
	public static List<ParseObject> LOGIN_USER_INFO(String _NAME, String _PASS)
	{

		return null;

	}

	/**
	 * Queries the table for a User Id given a User's email
	 * 
	 * @param _EMAIL
	 *            the email to check against
	 * @return list = the list of users returned for the email
	 */
	public static List<ParseObject> GET_USER_ID_FROM_EMAIL(String _EMAIL)
	{
		ParseQuery<ParseObject> query = ParseQuery.getQuery(USER_TABLE);
		query.whereEqualTo(USER_EMAIL, _EMAIL);

		query.findInBackground(new FindCallback<ParseObject>()
		{

			@Override
			public void done(List<ParseObject> usersList, ParseException exe)
			{
				if (exe == null)
					list = usersList;
				else
					Log.i("Parse", exe.getMessage());
			}
		});

		return list;
	}

	/**
	 * Method left blank for future use where a more simplified version of 
	 * query for Spending Reports may be needed (right now its very complicated and costly)
	 * 
	 * @param _USERID
	 *            the userid
	 * @param _STARTDATE
	 *            the startdate
	 * @param _ENDDATE
	 *            the enddate
	 * @return the list
	 */
	public static List<ParseObject> SPENDING_CATEGORY_REPORT(int _USERID,
			String _STARTDATE, String _ENDDATE)
	{
		return null;
	}

	/**
	 * Method left blank for futre use where duplicate users may be checked 
	 * against cached data instead of running several queries to the network 
	 * 
	 * @param _USERNAME
	 *            the username
	 * @return true, if successful
	 */
	public static boolean CHECK_DUPLICATE_USERS(String _USERNAME)
	{

		return false;
	}

	/**
	 * Method called to insert a user into the database
	 * 
	 * @param _USERNAME
	 *            the name of the user being crated
	 * @param _USEREMAIL
	 *            the email of the user being created
	 * @param _USERPASS
	 *            the password of the user being created 
	 * @param _USERTYPE
	 *            the type of the user being crated
	 * @param _USERCURRENCY
	 *            the currency of the user
	 * @return the list
	 */
	public static List<ParseObject> INSERT_USER(String _USERNAME,
			String _USEREMAIL, String _USERPASS, String _USERTYPE,
			int _USERCURRENCY)
	{
		ParseObject user = new ParseObject(USER_TABLE);
		user.put(USER_NAME, _USERNAME);
		user.put(USER_EMAIL, _USEREMAIL);
		user.put(USER_PASS, _USERPASS);
		user.put(USER_TYPE, _USERTYPE);
		user.put(USER_CURRENCY, _USERCURRENCY);
		user.saveInBackground();
		
		return null;

	}

	/**
	 * Method called to insert a new account into the database
	 * 
	 * @param _USER
	 *            the user the new account belongs to
	 * @param _ACCOUNTNAME
	 *            the name of the account
	 * @param _ACCOUNTBALANCE
	 *            the balance of the account
	 * @param _ACCOUNTBANK
	 *            the bank of the account
	 */
	public static void INSERT_ACCOUNT(ParseObject _USER, String _ACCOUNTNAME,
			String _ACCOUNTBALANCE, String _ACCOUNTBANK)
	{
		ParseObject acc = new ParseObject(ACCOUNT_TABLE);
		acc.put(USER_ACCOUNT, _USER);
		acc.put(ACCOUNT_NAME, _ACCOUNTNAME);
		acc.put(ACCOUNT_BALANCE, _ACCOUNTBALANCE);
		acc.put(ACCOUNT_BANK, _ACCOUNTBANK);

		acc.saveInBackground();
	}

	/**
	 * Method called to insert a new account into the database, with interst rate
	 * 
	 * @param _USER
	 *            the user the account belongs to
	 * @param _ACCOUNTNAME
	 *            the name of the account
	 * @param _ACCOUNTBALANCE
	 *            the balance of the account
	 * @param _ACCOUNTBANK
	 *            the bank of the account
	 * @param _ACCOUNTINTEREST
	 *            the interst rate of the account
	 */
	public static void INSERT_ACCOUNT(ParseObject _USER, String _ACCOUNTNAME,
			String _ACCOUNTBALANCE, String _ACCOUNTBANK, String _ACCOUNTINTEREST)
	{
		ParseObject acc = new ParseObject(ACCOUNT_TABLE);
		acc.put(USER_ACCOUNT, _USER);
		acc.put(ACCOUNT_NAME, _ACCOUNTNAME);
		acc.put(ACCOUNT_BALANCE, _ACCOUNTBALANCE);
		acc.put(ACCOUNT_BANK, _ACCOUNTBANK);
		acc.put(ACCOUNT_INTEREST, _ACCOUNTINTEREST);

		acc.saveInBackground();
	}

	/**
	 * Gets the default currency.
	 * 
	 * @param userid
	 *            the userid
	 */
	public static void GET_DEFAULT_CURRENCY(String userid)
	{
	}

	/**
	 * Inserts a currency into the database
	 * 
	 * @param name
	 *            the name of the currency
	 * @param ticker
	 *            the ticker symbol of the currency
	 * @param symbol
	 *            the symbol of the currency (in HTML)
	 */
	public static void INSERT_CURRENCY(String name, String ticker, String symbol)
	{
		ParseObject parseObject = new ParseObject(CURRENCY_TABLE);

		parseObject.put(CURRENCY_NAME, name);
		parseObject.put(CURRENCY_TICKER, ticker);
		parseObject.put(CURRENCY_SYMBOL, symbol);

		parseObject.saveInBackground();
	}
}