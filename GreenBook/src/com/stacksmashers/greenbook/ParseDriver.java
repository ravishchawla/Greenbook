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

public class ParseDriver
{
	public static final String _id = "_id";
	
	public static final String OBJECT_ID = "objectId";
	public static final String CREATED_AT = "createdAt";
	
	public static final String USER_TABLE = "USERS";
	public static final String USERS_ID = USER_TABLE + "._id";
	public static final String USER_TYPE = "USER_TYPE";
	public static final String USER_NAME = "USER_NAME";
	public static final String USER_DISPLAY_NAME = "USER_DISPLAY_NAME";
    public static final String USER_EMAIL = "USER_EMAIL";
	public static final String USER_PIC = "USER_PIC";
	public static final String USER_PASS = "USER_PASS";
	public static final String USER_CURRENCY = "USER_CURRENCY";
	public static final String USER_ACCOUNT = "USER_ACCOUNT";
	public static final String USER_TRANSACTION = "USER_TRANSACTION";
	
	public static final String ACCOUNT_TABLE = "ACCOUNTS";
	public static final String ACCOUNT_ID = ACCOUNT_TABLE + "._id";
	public static final String ACCOUNT_NAME = "ACCOUNT_NAME";
	public static final String ACCOUNT_USER = "ACCOUNT_USER";
	public static final String ACCOUNT_BALANCE= "ACCOUNT_BALANCE";
	public static final String ACCOUNT_INTEREST = "ACCOUNT_INTEREST";
	public static final String ACCOUNT_BANK = "ACCOUNT_BANK";
	
	
	
	public static final String TRANSACTION_TABLE = "TRANSACTIONS";
	public static final String ACCOUNT_TRANSACTION = "ACCOUNT_TRANSACTION";
	public static final String TRANSACTION_ID = TRANSACTION_TABLE + "._id";
	public static final String TRANSACTION_USER = "TRANSACTION_USER";
	public static final String TRANSACTION_ACCOUNT = "TRANSACTION_ACCOUNT";
	public static final String TRANSACTION_ACCOUNT_NAME = "TRANSACTION_ACCOUNT_NAME";
	public static final String TRANSACTION_NAME = "TRANSACTION_NAME";
	public static final String TRANSACTION_VALUE = "TRANSACTION_VALUE";
	public static final String TRANSACTION_WITHRAWAL_REASON = "TRANSACTION_WITHRAWAL_REASON";
	public static final String TRANSACTION_CATEGORY = "TRANSACTION_CATEGORY";
	public static final String TRANSACTION_POSTED = "TRANSACTION_POSTED";
	public static final String TRANSACTION_AFFECTED = "TRANSACTION_AFFECTED";

	
	public static final String CURRENCY_TABLE = "CURRENCIES";
	public static final String CURRENCY_ID = CURRENCY_TABLE + "._id";
	public static final String CURRENCY_NAME = "CURRENCY_NAME";
	public static final String CURRENCY_TICKER = "CURRENCY_TICKER";
	public static final String CURRENCY_SYMBOL = "CURRENCY_SYMBOL";
	public static final String CURRENCY_VALUE = "CURRENCY_VALUE";

	static List<ParseObject> list;
	
	
	public ParseDriver(Context context, Intent intent)
	{
		// TODO Auto-generated constructor stub
		
		Parse.initialize(context, "Eo7ASw57FOevkYiO6HnUKkJJauSWU64UdTwihpqP", "5pE1jsBnG5yjjCV97vVZwZpgEixnfMg4Fl5sYDwB");
		ParseAnalytics.trackAppOpened(intent);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}



/*	public static Cursor FORGET_USER_INFO(String USER_EMAIL)
	{
		Cursor cursor = sqldbase.query(
				// use dbuser table
				DBHelper.USER_TABLE, new String[] { DBHelper.USER_NAME,
						DBHelper.USER_TYPE, DBHelper.USER_PASS },
				DBHelper.USER_EMAIL + " = '" + USER_EMAIL + "'", null, null,
				null, null);

		return cursor;

	}

*/
	public static List<ParseObject> FORGET_USER_INFO(String _EMAIL)
	{
		
	
	
		return list;
		
	}
	
	
/*	public static Cursor LOGIN_USER_INFO(String NAME, String PASS)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE, new String[] {
				DBHelper.USERS_ID, DBHelper.USER_EMAIL, DBHelper.USER_PASS },
				DBHelper.USER_EMAIL + " = '" + NAME + "' AND "
						+ DBHelper.USER_PASS + " = '" + PASS + "'", null, null,
				null, null);

		return cursor;

	}
*/
	public static List<ParseObject> LOGIN_USER_INFO(String _NAME, String _PASS)
	{
		
		
	
		
			return null;
		
		
		
		
	}
	
/*	public static Cursor GET_USER_ID_FROM_EMAIL(String email)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE,
				new String[] { DBHelper.USERS_ID }, DBHelper.USER_EMAIL
						+ " = '" + email + "'", null, null, null, null);

		return cursor;
	}
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
						// TODO Auto-generated method stub
						
						if(exe == null)
							list = usersList;
						else
							Log.i("Parse", exe.getMessage());
							
						
						
						
					}
				});
			
				return list;
	
		
	
		
	}
	
	
/*	public static Cursor SPENDING_CATEGORY_REPORT(int USERID,
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
*/
	
	public static List<ParseObject> SPENDING_CATEGORY_REPORT(int _USERID, String _STARTDATE, String _ENDDATE)
	{
		return null;
	}
	
	
	
/*	public static Cursor ACCOUNT_NAME_FROM_ID(int ACCOUNTSID)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_NAME }, DBHelper.ACCOUNT_ID
						+ " = '" + ACCOUNTSID + " '", null, null, null, null);

		return cursor;

	}
*/
/*	public static Cursor GET_USER_TYPE(int USERID)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE,
				new String[] { DBHelper.USER_TYPE }, DBHelper.USERS_ID + " = '"
						+ USERID + "'", null, null, null, null);

		return cursor;
	}
*/
/*	public static Cursor GET_ALL_USERS()
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE, new String[] {
				DBHelper.USER_NAME, DBHelper.USER_EMAIL }, null, null, null,
				null, null);
		return cursor;
	}
*/
/*	public static Cursor CHECK_DUPLICATE_USERS(String USERNAME)
	{
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE, new String[] {
				DBHelper.USERS_ID, DBHelper.USER_EMAIL }, DBHelper.USER_EMAIL
				+ " = '" + USERNAME + "'", null, null, null, null);

		return cursor;
	}
*/
	public static boolean CHECK_DUPLICATE_USERS(String _USERNAME)
	{
		
		
			
				
	
	return false;	
	}
	
	
/*	public static void UPDATE_USER_TYPE(String type, int USERID)
	{
		ContentValues brutus = new ContentValues(); // get new contentvalyes
													// from brutus
		brutus.put(DBHelper.USER_TYPE, type);
		sqldbase.update(DBHelper.USER_TABLE, brutus, DBHelper.USERS_ID + " = '"
				+ USERID + "'", null);
	}
*/
/*	public static Cursor GET_ACCOUNT_TRANSACTIONS(int ACCOUNTSID, int USERID)
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
*/
/*	public static Cursor GET_ACCOUNT_INFO_FOR_USER(int USERID)
	{
		Cursor caeser = sqldbase.query(DBHelper.ACCOUNT_TABLE, new String[] {
				DBHelper.ACCOUNT_ID, DBHelper.ACCOUNT_NAME },
				DBHelper.ACCOUNT_USER + " = '" + USERID + "'", null, null,
				null, null);

		return caeser;

	}
*/
/*	public static Cursor GET_ACCOUNT_FROM_ID(long ACCOUNTSID)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_NAME }, DBHelper.ACCOUNT_ID
						+ " = '" + ACCOUNTSID + " '", null, null, null, null);

		return cursor;
	}
*/
/*	public static void INSERT_TRANSACTION(ContentValues cValues)
	{
		sqldbase.insert(DBHelper.TRANSACTION_TABLE, null, cValues);
	}
*/
/*	public static void INSERT_DEPOSIT(String SOURCE, Double balance, int USERID,
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
*/
/*	public static void INSERT_WITHRAWAL(String CATEGORY, String REASON,
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
*/
/*	public static Cursor GET_ACCOUNTS_IDS_FOR_USER(int USERID)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_ID }, DBHelper.ACCOUNT_USER
						+ " = '" + USERID + "'", null, null, null, null, null);

		return cursor;

	}
*/
/*	public static Cursor GET_ACCOUNT_BALANCE(int USERID, int ACCOUNTSID)
	{
		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_BALANCE },
				DBHelper.ACCOUNT_USER + " = '" + USERID + "' AND "
						+ DBHelper.ACCOUNT_ID + " = '" + ACCOUNTSID + "'",
				null, null, null, null, null);

		return cursor;

	}
*/
/*	public static void UPDATE_BALANCE_FOR_ACCOUNT(int BALANCE, int USERID,
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
*/
/*	public static void INSERT_USER(String USER_NAME, String USER_EMAIL,
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
*/
	public static List<ParseObject> INSERT_USER(String _USERNAME, String _USEREMAIL, String _USERPASS, String _USERTYPE, int _USERCURRENCY)
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
/*	public static Cursor CHECK_FOR_DUPLICATE_ACCOUNTS(int USERID,
			String ACCOUNTS_NAME)
	{

		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE,
				new String[] { DBHelper.ACCOUNT_NAME }, DBHelper.ACCOUNT_USER
						+ " = '" + USERID + "' AND " + DBHelper.ACCOUNT_NAME
						+ " = '" + ACCOUNTS_NAME + "'", null, null, null, null);

		return cursor;

	}
*/
/*	public static void INSERT_ACCOUNT(String ACCOUNT_NAME,
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
*/	
	
	public static void INSERT_ACCOUNT(ParseObject _USER, String _ACCOUNTNAME, String _ACCOUNTBALANCE, String _ACCOUNTBANK)
	{
		ParseObject acc = new ParseObject(ACCOUNT_TABLE);
		acc.put(USER_ACCOUNT, _USER);
		acc.put(ACCOUNT_NAME, _ACCOUNTNAME);
		acc.put(ACCOUNT_BALANCE, _ACCOUNTBALANCE);
		acc.put(ACCOUNT_BANK, _ACCOUNTBANK);
		
		acc.saveInBackground();
		
		
	}

	public static void INSERT_ACCOUNT(ParseObject _USER, String _ACCOUNTNAME, String _ACCOUNTBALANCE, String _ACCOUNTBANK, String _ACCOUNTINTEREST)
	{
		ParseObject acc = new ParseObject(ACCOUNT_TABLE);
		acc.put(USER_ACCOUNT, _USER);
		acc.put(ACCOUNT_NAME, _ACCOUNTNAME);
		acc.put(ACCOUNT_BALANCE, _ACCOUNTBALANCE);
		acc.put(ACCOUNT_BANK, _ACCOUNTBANK);
		acc.put(ACCOUNT_INTEREST, _ACCOUNTINTEREST);
		
		acc.saveInBackground();
		
		
	}
	
	/*	public static void INSERT_ACCOUNT(String ACCOUNT_NAME,
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
*/
/*	public static Cursor GET_ALL_ACCOUNT_INFO(int USERID, String SORT)
	{
		String query[] = { DBHelper.ACCOUNT_ID, DBHelper.ACCOUNT_NAME,
				DBHelper.ACCOUNT_BANK, DBHelper.ACCOUNT_BALANCE};

		Cursor cursor = sqldbase.query(DBHelper.ACCOUNT_TABLE, query,
				DBHelper.ACCOUNT_USER + " = '" + USERID + "'", null, null,
				null, SORT);

		return cursor;

	}
*/
/*	public static Cursor GET_DEFAULT_CURRENCY(int userid)
	{
		
		Cursor cursor = sqldbase.query(DBHelper.USER_TABLE, new String[] { DBHelper.USER_CURRENCY }, DBHelper.USERS_ID + " = '" + userid + "'", null,null,null,null);
		
		return cursor;

	}
*/
	
	public static void GET_DEFAULT_CURRENCY(String userid)
	{
		
	}
	
/*	public static void UPDATE_CURRENCIES(ArrayList<Double> exchangeValues)
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
*/
/*	public static void UPDATE_DEFAULT_CURRENCY(int user_id, int currency)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.USER_CURRENCY, currency);
		sqldbase.update(DBHelper.USER_TABLE, cValues, DBHelper.USERS_ID + " = '" + user_id + "'", null);
		
	}
*/	
	
/*	public static Cursor GET_ALL_CURRENCIES()
	{
		Cursor cursor = sqldbase.query(DBHelper.CURRENCY_TABLE, new String[] {
				DBHelper.CURRENCY_NAME, DBHelper.CURRENCY_TICKER,
				DBHelper.CURRENCY_SYMBOL, DBHelper.CURRENCY_VALUE , DBHelper.CURRENCY_ID},
				null, null, null, null, null);
		return cursor;
	}
*/	
/*	public static Cursor GET_CURRENCY(int currency_id)
	{
		Cursor cursor = sqldbase.query(DBHelper.CURRENCY_TABLE, new String[] {
				DBHelper.CURRENCY_NAME, DBHelper.CURRENCY_TICKER,
				DBHelper.CURRENCY_SYMBOL, DBHelper.CURRENCY_VALUE },
				DBHelper.CURRENCY_ID + " = '" + currency_id + "'", null, null, null, null);
		return cursor;
	}
*/	

/*	public static void INSERT_CURRENCY(String name, String ticker, String string)
	{
		ContentValues cValues = new ContentValues();
		cValues.put(DBHelper.CURRENCY_NAME, name);
		cValues.put(DBHelper.CURRENCY_TICKER, ticker);
		cValues.put(DBHelper.CURRENCY_SYMBOL, string);

		sqldbase.insert(DBHelper.CURRENCY_TABLE, null, cValues);

	}
*/
	public static void INSERT_CURRENCY(String name, String ticker, String symbol)
	{
		ParseObject parseObject = new ParseObject(CURRENCY_TABLE);
		
		parseObject.put(CURRENCY_NAME, name);
		parseObject.put(CURRENCY_TICKER, ticker);
		parseObject.put(CURRENCY_SYMBOL, symbol);
		
		parseObject.saveInBackground();
	}
	
	
/*	public static void UPDATE_TRANSACTION_VALUES(int user_id, Double double1)
	{

		// UPDATE TRANSACTION_TABLE Set TRANSACTION_VALUE = TRANSACTION_VALUE *
		// 3.2 WHERE TRANSACTION_USER = 0

		sqldbase.execSQL("UPDATE " + DBHelper.TRANSACTION_TABLE + " SET "
				+ DBHelper.TRANSACTION_VALUE + " = "
				+ DBHelper.TRANSACTION_VALUE + " * " + double1 + " WHERE "
				+ DBHelper.TRANSACTION_USER + " = " + user_id);

		sqldbase.execSQL("UPDATE " + DBHelper.ACCOUNT_TABLE + " SET "
				+ DBHelper.ACCOUNT_BALANCE + " = " + DBHelper.ACCOUNT_BALANCE
				+ " * " + double1 + " WHERE " + DBHelper.ACCOUNT_USER
				+ " = " + user_id);

	}
*/

	

	
}