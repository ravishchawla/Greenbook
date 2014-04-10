package com.stacksmashers.greenbook;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.widget.ArrayAdapter;
import com.parse.ParseObject;


// TODO: Auto-generated Javadoc
/**
 * The Class Vars, holds important variables. All variables 
 * are static here, and instead of passing values as extas on intents, 
 * they are stored here, so that they can be used by more than one class
 * at the same time. 
 * 
 * @author Ravish Chawla
 */
public class Vars
{


	/** The dollar. */
	public static String DOLLAR;
	
	/** The pound. */
	public static String POUND;
	
	/** The real. */
	public static String REAL;
	
	/** The yuan. */
	public static String YUAN;
	
	/** The euro. */
	public static String EURO;
	
	/** The rupee. */
	public static String RUPEE;
	
	/** The ruble. */
	public static String RUBLE;
	
	/** The rand. */
	public static String RAND;
	
	/** The franc. */
	public static String FRANC;


	/** The check. */
	public static String CHECK;
	
	/** The cross. */
	public static String CROSS;


	/** The def current id. */
	public static String DEF_CURRENT_ID;
	
	/** The def currency ticker. */
	public static String DEF_CURRENCY_TICKER;
	
	/** The def currency symbol. */
	public static String DEF_CURRENCY_SYMBOL;


	/** The intent extra parse obj. */
	public static ParseObject intentExtraParseObj;


	/** The user object id. */
	public static String userObjectID;


	/** The currency parse obj. */
	public static ParseObject currencyParseObj;


	/** The account parse obj. */
	public static ParseObject accountParseObj;


	/** The accounts parse list. */
	public static List<ParseObject> accountsParseList = new ArrayList<ParseObject>();


	/** The user parse obj. */
	public static ParseObject userParseObj;


	/** The transaction parse list. */
	public static List<ParseObject> transactionParseList = new ArrayList<ParseObject>();


	/** The transaction withraw parse map. */
	public static LinkedHashMap<String, Double> transactionWithrawParseMap = new LinkedHashMap<String, Double>();
	
	/** The transaction total parse map. */
	public static LinkedHashMap<String, Double> transactionTotalParseMap = new LinkedHashMap<String, Double>();
	
	/** The transaction total sum. */
	public static Double transactionTotalSum = 0.0;
	
	/** The date format. */
	public static SimpleDateFormat dateFormat;
	
	/** The long date format. */
	public static SimpleDateFormat longDateFormat;
	
	/** The currency parse list. */
	public static List<ParseObject> currencyParseList;


	/** The decimal format. */
	public static DecimalFormat decimalFormat;




	/** The account sort type. */
	public static int ACCOUNT_SORT_TYPE = 0;
	
	/** The Constant SORT_BY_NAME. */
	public final static int SORT_BY_NAME = 0x0;
	
	/** The Constant SORT_BY_BANK. */
	public final static int SORT_BY_BANK = 0x1;
	
	/** The Constant SORT_BY_BALANCE. */
	public final static int SORT_BY_BALANCE = 0x2;
	
	/** The Constant SORT_BY_DATE_CREATED. */
	public final static int SORT_BY_DATE_CREATED = 0x3;

	


	/** The sum transactions column. */
	public static String sumTransactionsColumn = "Sum(" + ParseDriver.TRANSACTION_VALUE + ")";
	
	/** The transaction withraw sum. */
	public static Double transactionWithrawSum = 0.0;
	

	
	/** The hashed device id. */
	public static String HASHED_DEVICE_ID;
	
	/** The transactions ad unit it. */
	public static String TRANSACTIONS_AD_UNIT_IT;
	
	/** The transaction account parse list. */
	protected static List<ParseObject> transactionAccountParseList = new ArrayList<ParseObject>();
	
	/** The backup adapter. */
	public static ArrayAdapter<ParseObject> backupAdapter;
	
	/** The transaction difference sum. */
	public static Double transactionDifferenceSum;
	
	/** The transaction difference parse map. */
	public static LinkedHashMap<String, Double> transactionDifferenceParseMap;
	
	


	/**
	 * Instantiates all these variables to their values. 
	 *
	 * @param context the context
	 */
	public Vars(Context context)
	{
		// TODO Auto-generated constructor stub


		DOLLAR = Html.fromHtml("$").toString();
		POUND = Html.fromHtml("£").toString();
		REAL = Html.fromHtml("R$").toString();
		YUAN = Html.fromHtml("¥").toString();
		EURO = Html.fromHtml("€").toString();
		RUPEE = Html.fromHtml("₹").toString();
		RUBLE = Html.fromHtml("Rub.").toString();
		RAND = Html.fromHtml("R").toString();
		FRANC = Html.fromHtml("Fr.").toString();


		CHECK = Html.fromHtml("✔").toString();
		CROSS = Html.fromHtml("✘").toString();


		dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		longDateFormat = new SimpleDateFormat("EEE, MMMM d, yyyy",
				Locale.getDefault());

		

		decimalFormat = new DecimalFormat("#.##");

		TRANSACTIONS_AD_UNIT_IT = context.getResources().getString(R.string.transaction_ad_unit_id);
		HASHED_DEVICE_ID = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		
		

	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
	}
}