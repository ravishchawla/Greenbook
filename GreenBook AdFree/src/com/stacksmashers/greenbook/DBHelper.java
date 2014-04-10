package com.stacksmashers.greenbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class that initializes all SQLITE Database tables and columns. Some special
 * queries are also executed here.
 * 
 * @author Ravish Chawla
 */
public class DBHelper extends SQLiteOpenHelper
{

	/** The Constant Database_Name. */
	public static final String Database_Name = "main_database.db";

	/** The version. */
	public static int version = 1;

	/** The Constant _id. */
	public static final String _id = "_id";

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

	/** The Constant TRANSACTION_DEPOSIT_SOURCE. */
	public static final String TRANSACTION_DEPOSIT_SOURCE = "TRANSACTION_DEPOSIT_SOURCE";

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

	/** The create user table. */
	public static String createUserTable;

	/** The create accounts table. */
	public static String createAccountsTable;

	/** The create transactions table. */
	public static String createTransactionsTable;

	/** The create currency table. */
	public static String createCurrencyTable;

	/** The Constant TAG. */
	public static final String TAG = "DBHelper";

	/**
	 * Instantiates a new DB helper.
	 * 
	 * @param context
	 *            the context
	 */
	public DBHelper(Context context)
	{
		super(context, Database_Name, null, version);
	}

	/**
	 * This method is automatically executed when the database is first accessed
	 * in the app. It tells the Database to check for foriegn keys.
	 * 
	 */
	@Override
	public void onOpen(SQLiteDatabase db)
	{
		super.onOpen(db);
		if (!db.isReadOnly())
		{
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	/**
	 * called once when the database file is created .
	 * 
	 * @param dbse
	 *            - a reference to the created SQLite Database
	 * @return void
	 */

	@Override
	public void onCreate(SQLiteDatabase dbse)
	{

		createUserTable = "CREATE TABLE " + USER_TABLE + "( " + _id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_TYPE + " TEXT,"
				+ USER_NAME + " TEXT," + USER_EMAIL + " TEXT," + USER_PIC
				+ " TEXT," + USER_PASS + " TEXT," + USER_CURRENCY + " INTEGER"
				+ ");";

		createAccountsTable = "CREATE TABLE " + ACCOUNT_TABLE + "( " + _id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + ACCOUNT_USER
				+ " INTEGER," + ACCOUNT_NAME + " TEXT," + ACCOUNT_BALANCE
				+ " INTEGER," + ACCOUNT_BANK + " TEXT," + ACCOUNT_INTEREST
				+ " TEXT," + "FOREIGN KEY(" + ACCOUNT_USER + ") REFERENCES "
				+ USER_TABLE + "(" + _id + ") ON DELETE CASCADE" + ");";

		createTransactionsTable = "CREATE TABLE " + TRANSACTION_TABLE + "( "
				+ _id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TRANSACTION_USER + " INTEGER," + TRANSACTION_ACCOUNT
				+ " INTEGER," + TRANSACTION_ACCOUNT_NAME + " TEXT,"
				+ TRANSACTION_NAME + " TEXT," + TRANSACTION_VALUE + " INTEGER,"
				+ TRANSACTION_WITHRAWAL_REASON + " TEXT,"
				+ TRANSACTION_DEPOSIT_SOURCE + " TEXT," + TRANSACTION_CATEGORY
				+ " TEXT," + TRANSACTION_POSTED + " DATETIME,"
				+ TRANSACTION_AFFECTED + " DATETIME," + "FOREIGN KEY("
				+ TRANSACTION_USER + ") REFERENCES " + USER_TABLE + "(" + _id
				+ ") ON DELETE CASCADE," + "FOREIGN KEY(" + TRANSACTION_ACCOUNT
				+ ") REFERENCES " + ACCOUNT_TABLE + "(" + _id
				+ ") ON DELETE CASCADE" + ");";

		createCurrencyTable = "CREATE TABLE " + CURRENCY_TABLE + "( " + _id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + CURRENCY_NAME
				+ " TEXT," + CURRENCY_TICKER + " TEXT," + CURRENCY_SYMBOL
				+ " TEXT," + CURRENCY_VALUE + " INTEGER" + ");";

		Log.i(TAG, "SQL Statements created");
		dbse.execSQL(createUserTable);
		dbse.execSQL(createAccountsTable);
		dbse.execSQL(createTransactionsTable);
		dbse.execSQL(createCurrencyTable);
	}

	/**
	 * A special method used to handle inconsistencies when changes are not
	 * always up to date.
	 * 
	 * @return void
	 */
	public static void exec()
	{
		createCurrencyTable = "CREATE TABLE " + CURRENCY_TABLE + "( " + _id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + CURRENCY_NAME
				+ " TEXT," + CURRENCY_TICKER + " TEXT," + CURRENCY_SYMBOL
				+ " TEXT," + CURRENCY_VALUE + " INTEGER" + ");";
	}

	@Override
	/**
	 * Called once if database has been changed since last time 
	 * @param args1 - value 
	 * @param args2 - value 
	 * @return void 
	 */
	public void onUpgrade(SQLiteDatabase dbse, int oldV, int newV)
	{
	}

}
