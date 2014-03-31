package com.stacksmashers.greenbook;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.achartengine.GraphicalView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.ParseObject;

public class SpendingReportsFragment extends BaseFragment
{

	
	
	private Spinner startSpinner;
	private Spinner endSpinner;
	private LinearLayout graphLayout;
	private TransactionsActivity trans;
	// private ListView spendingList;
	GraphicalView graph;
	private String startRange[] = new String[2];
	private String endRange[] = new String[2];
	static String startDateString, endDateString;
	static Date startDate, endDate;
	private ArrayAdapter<String> startAdapter, endAdapter;
	public ArrayAdapter<Entry<String, Double>> spendingAdapter;
	boolean state = false;
	View view;
	LinearLayout mLinLayout;
	public int viewMode;
	private AdView adView;

	public SpendingReportsFragment()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	public static SpendingReportsFragment newInstance(String text)
	{
		SpendingReportsFragment frag = new SpendingReportsFragment();
		Bundle bundle = new Bundle();
		bundle.putString("Strings", text);
		frag.setArguments(bundle);

		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		super.onCreateView(inflater, container, savedInstanceState); // create
		// savedinstancestate

		view = inflater.inflate(R.layout.fragment_spending_report_table,
				container, false);// calling activity register

		startRange[1] = "Select Date";
		endRange[1] = "Select Date";

		startSpinner = (Spinner) view.findViewById(R.id.spending_spinner_start);
		endSpinner = (Spinner) view.findViewById(R.id.spending_spinner_end);

		graphLayout = (LinearLayout) view
				.findViewById(R.id.spending_graphicalview);

		trans = (TransactionsActivity) getActivity();
		startDate = new Date();
		endDate = new Date();
		startDate.setYear(startDate.getYear() - 1);
		endDate.setYear(endDate.getYear() + 1);

		String currentDate = Vars.longDateFormat.format(startDate);
		startRange[0] = currentDate;
		// TODO Auto-generated method stub
		currentDate = Vars.longDateFormat.format(endDate);

		endRange[0] = currentDate;

		startAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, startRange);
		startAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		startSpinner.setAdapter(startAdapter);
		endAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, endRange);
		endAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		endSpinner.setAdapter(endAdapter);
		startSpinner.setSelection(0, false);
		endSpinner.setSelection(0, false);

		startSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3)
			{ // TODO Auto-generated method stub

				if (pos == 1)
				{
					DatePicker dateFragment = new DatePicker(0);
					dateFragment.show(getActivity().getFragmentManager(),
							"DatePicker");
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub

			}

		});

		endSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3)
			{ // TODO Auto-generated method stub

				if (pos == 1)
				{
					DatePicker dateFragment = new DatePicker(1);
					dateFragment.show(getActivity().getFragmentManager(),
							"DatePicker");
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub

			}

		});

		((TransactionsActivity) getActivity()).setSpendingReportTag(getTag());
		
adView = (AdView)view.findViewById(R.id.adViewSpending);
		
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(Vars.HASHED_DEVICE_ID).build();
		
		adView.loadAd(adRequest);
		
		adView.setAdListener(new AdListener()
		{
			@Override
			public void onAdOpened()
			{
				Log.d("adopen", "ad opened");
			}
			
			@Override
			public void onAdFailedToLoad(int error)
			{
				switch(error){
					case AdRequest.ERROR_CODE_INTERNAL_ERROR:
						Log.d("adfailed", "error code internal error");
						break;
					case AdRequest.ERROR_CODE_INVALID_REQUEST:
						Log.d("adfailed", "error code invalid request");
						break;
					case AdRequest.ERROR_CODE_NETWORK_ERROR:
						Log.d("adfailed", "error code network error");
						break;
					case AdRequest.ERROR_CODE_NO_FILL:
						Log.d("adfailed", "error code no fill");
						break;
					
					
				}

				
			}
			
			@Override
			public void onAdLoaded()
			{
				Log.d("adload", "ad loaded properly");
			}
				
			
		});

		
		
		updateData();

		return view;
	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub

		
		if(adView != null)
			adView.resume();
		
		super.onResume();
		Log.i("Spending", "Actually called onREsume");
		state = true;
		
		
		
		// updateData(null, null, 0);
		// if(trans.spendingMode == 1 && items!= null)
		// repaintGraph(items);

	}

	@Override
	public void onPause()
	{
		if(adView != null)
			adView.pause();
		super.onPause();
		
		
	}
	
	@Override
	public void onDestroy()
	{
		if(adView != null)
			adView.destroy();
		super.onDestroy();
		
	}
	
	public List<Entry<String, Double>> regroup()
	{
		LinkedHashMap<String, Double> transactionGroups = new LinkedHashMap<String, Double>();
		Log.i("trans entry ()", "" + Vars.transactionAccountParseList.size());
		Vars.transactionWithrawSum = 0.0;

		for (ParseObject obj : Vars.transactionAccountParseList)
		{
			Double value = obj.getDouble(ParseDriver.TRANSACTION_VALUE);
			Date createdAt = obj.getCreatedAt();
			Vars.transactionTotalSum += value;

			if ((value >= 0) || (createdAt.compareTo(startDate) < 0)
					|| (createdAt.compareTo(endDate) > 0))
				continue;

			Vars.transactionWithrawSum += value;
			String name = obj.getString(ParseDriver.TRANSACTION_CATEGORY);
			Double prev = transactionGroups.get(name);
			if (prev == null)
				transactionGroups.put(name, value);
			else
				transactionGroups.put(name, prev + value);
		}

		Log.i("map entry ()", "" + transactionGroups.size());
		for (Map.Entry<String, Double> entry : transactionGroups.entrySet())
		{
			Log.i("map entry", entry.getKey() + " , " + entry.getValue());
		}

		Vars.transactionWithrawParseMap = transactionGroups;

		return new ArrayList(transactionGroups.entrySet());

	}

	public void refreshGraph()
	{

		mLinLayout = (LinearLayout) view
				.findViewById(R.id.spending_graphicalview);

		graph = PieGraph.getNewInstance(getActivity(), Vars.transactionWithrawParseMap, Vars.transactionWithrawSum);
		graph.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		mLinLayout.removeAllViews();
		mLinLayout.addView(graph);
	}

	public void updateData()
	{

		/*
		 * final Cursor caeser = sqldbase.query(DBHelper.TRANSACTION_TABLE, new
		 * String[] { DBHelper.TRANSACTION_ID, DBHelper.TRANSACTION_USER,
		 * DBHelper.TRANSACTION_ACCOUNT, DBHelper.TRANSACTION_ACCOUNT_NAME,
		 * DBHelper.TRANSACTION_DEPOSIT_SOURCE,
		 * DBHelper.TRANSACTION_WITHRAWAL_REASON, DBHelper.TRANSACTION_CATEGORY,
		 * DBHelper.TRANSACTION_VALUE }, condition, null, null, null, null);
		 */

		startAdapter.notifyDataSetChanged();
		endAdapter.notifyDataSetChanged();

		// Utility.setListViewHeightBasedOnChildren(spendingList);

		Log.i("Spend", "Moded");

		// rootLayout =
		// (LinearLayout)view.findViewById(R.id.child_linearlayout);

		ListView mListView = (ListView) view
				.findViewById(R.id.spending_listview);

		spendingAdapter = new SpendingAdapter(getActivity(),
				new ArrayList<Entry<String, Double>>());
		mListView.setAdapter(spendingAdapter);

		displayView(1);
		Log.i("Spending", "Added Graph");

		return;

	}

	public void displayView(int mode)
	{
		LinearLayout mLinLayout = (LinearLayout) view
				.findViewById(R.id.spending_graphicalview);
		ListView mListView = (ListView) view
				.findViewById(R.id.spending_listview);
		if (0 == (viewMode = mode))
		{
			mLinLayout.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);

		}

		else
		{
			mListView.setVisibility(View.GONE);
			mLinLayout.setVisibility(View.VISIBLE);
		}
	}

	@SuppressLint("ValidFragment")
	private class DatePicker extends DialogFragment implements
			DatePickerDialog.OnDateSetListener
	{

		int whichSpinner = 0;

		public DatePicker(int whichSpinner)
		{

			this.whichSpinner = whichSpinner;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub

			final Calendar c = Calendar.getInstance();
			int day = c.get(Calendar.DAY_OF_MONTH);
			int month = c.get(Calendar.MONTH);
			int year = c.get(Calendar.YEAR);

			return new DatePickerDialog(getActivity(), this, year, month, day);

		}

		@Override
		public void onCancel(DialogInterface dialog)
		{
			// TODO Auto-generated method stub

			if (whichSpinner == 0)
				startSpinner.setSelection(0);
			else
				endSpinner.setSelection(0);

			super.onCancel(dialog);
		}

		@Override
		public void onDateSet(android.widget.DatePicker view, int year,
				int monthOfYear, int dayOfMonth)
		{
			// TODO Auto-generated method stub

			monthOfYear++;
			String month = "" + monthOfYear, day = "" + dayOfMonth;
			if (monthOfYear < 10)
				month = "0" + monthOfYear;

			if (dayOfMonth < 10)
				day = "0" + dayOfMonth;

			String date = year + "-" + month + "-" + day;
			String longStartDate = "";
			Date longDate = null;
			try
			{

				longDate = Vars.dateFormat.parse(date);
				longStartDate = Vars.longDateFormat.format(longDate);

			}
			catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (whichSpinner == 0)
			{
				SpendingReportsFragment.startDate = longDate;
				startRange[0] = longStartDate;
				startSpinner.setSelection(0);
			}
			else
			{
				SpendingReportsFragment.endDate = longDate;
				endRange[0] = longStartDate;
				endSpinner.setSelection(0);
			}

			spendingAdapter.clear();
			spendingAdapter.addAll(regroup());
			refreshGraph();



		}

	}

	class SpendingAdapter extends ArrayAdapter<Entry<String, Double>>
	{

		private Context context;
		private List<Entry<String, Double>> entries;
		private int posit = 0;
		
		public SpendingAdapter(Context _context,
				List<Entry<String, Double>> _parseList)
		{
			super(_context, R.layout.listblock_spending_report, _parseList);
			context = _context;
			entries = _parseList;


		}

		@Override
		public void clear() {
			
			posit = 0;
			
		};
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub

			if (convertView == null)
			{
				convertView = LayoutInflater.from(context).inflate(
						R.layout.listblock_spending_report, null);
			}

			Map.Entry<String, Double>

			object = entries.get(position);

			((TextView) convertView.findViewById(R.id.transactions_currency))
					.setText(object.getKey());
			((TextView) convertView.findViewById(R.id.spending_balance))
					.setText("" + object.getValue());

			
			
			((View)convertView.findViewById(R.id.spending_icon)).setBackgroundColor(Utility.getColor(posit++));
			
			return convertView;

		}

	}

}




