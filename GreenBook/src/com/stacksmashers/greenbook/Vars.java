package com.stacksmashers.greenbook;

import android.text.Html;

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
	
	public static int DEF_CURRENT_ID;
	public static String DEF_CURRENCY_TICKER;
	public static String DEF_CURRENCY_SYMBOL;
	
	public Vars()
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
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
