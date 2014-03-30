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


import com.parse.ParseObject;


public class Vars
{


	public static String DOLLAR;
	public static String POUND;
	public static String REAL;
	public static String YUAN;
	public static String EURO;
	public static String RUPEE;
	public static String RUBLE;
	public static String RAND;
	public static String FRANC;


	public static String CHECK;
	public static String CROSS;


	public static String DEF_CURRENT_ID;
	public static String DEF_CURRENCY_TICKER;
	public static String DEF_CURRENCY_SYMBOL;


	public static ParseObject intentExtraParseObj;


	public static String userObjectID;


	public static ParseObject currencyParseObj;


	public static ParseObject accountParseObj;


	public static List<ParseObject> accountsParseList = new ArrayList<ParseObject>();


	public static ParseObject userParseObj;


	public static List<ParseObject> transactionParseList = new ArrayList<ParseObject>();


	public static LinkedHashMap<String, Double> transactionParseMap = new LinkedHashMap<String, Double>();
	public static Double transactionTotalSum = 0.0;
	public static SimpleDateFormat dateFormat;
	public static SimpleDateFormat longDateFormat;
	public static List<ParseObject> currencyParseList;


	public static DecimalFormat decimalFormat;




	public static int ACCOUNT_SORT_TYPE = 0;
	public final static int SORT_BY_NAME = 0x0;
	public final static int SORT_BY_BANK = 0x1;
	public final static int SORT_BY_BALANCE = 0x2;
	public final static int SORT_BY_DATE_CREATED = 0x3;

	


	public static String sumTransactionsColumn = "Sum(" + ParseDriver.TRANSACTION_VALUE + ")";
	public static Double transactionWithrawSum = 0.0;
	

	
	public static String HASHED_DEVICE_ID;
	public static String TRANSACTIONS_AD_UNIT_IT;
	


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
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub


	}


}

