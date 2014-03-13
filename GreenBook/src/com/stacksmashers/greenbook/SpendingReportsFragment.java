package com.stacksmashers.greenbook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.achartengine.GraphicalView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
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
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SpendingReportsFragment extends BaseFragment
{

	private Spinner startSpinner;
	private Spinner endSpinner;
	private LinearLayout graphLayout;
	private TransactionsActivity trans;
	private ListView spendingList;
	GraphicalView graph;
	private int userId;
	private String startRange[] = new String[2];
	private String endRange[] = new String[2];
	static String startDate, endDate;
	private ArrayAdapter<String> startAdapter, endAdapter;
	private SimpleDateFormat dateFormat;
	boolean state = false;
	private TextView totalText;

	View view;

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

		dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

		startRange[1] = "Select Date";
		endRange[1] = "Select Date";

		startSpinner = (Spinner) view
				.findViewById(R.id.spending_report_cycle_spinner_start);
		endSpinner = (Spinner) view
				.findViewById(R.id.spending_report_cycle_spinner_end);
		spendingList = (ListView) view
				.findViewById(R.id.spending_report_listview);
		graphLayout = (LinearLayout) view
				.findViewById(R.id.spending_report_graph);

		totalText = (TextView)view.findViewById(R.id.spending_total_sum);
		trans = (TransactionsActivity) getActivity();
		userId = trans.userID;

		String currentDate = dateFormat.format(new Date());

		// if(items != null)
		// drawGraph(items);

		// TODO Auto-generated method stub

		startRange[0] = currentDate;
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
			{
				// TODO Auto-generated method stub

				if (pos == 1)
				{
					DatePicker dateFragment = new DatePicker();
					dateFragment.show(getActivity().getFragmentManager(),
							"DatePicker", 0);
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
			{
				// TODO Auto-generated method stub

				if (pos == 1)
				{
					DatePicker dateFragment = new DatePicker();
					dateFragment.show(getActivity().getFragmentManager(),
							"DatePicker", 1);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub

			}

		});

		updateData(currentDate, currentDate, 1);
		return view;
	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub

		super.onResume();
		Log.i("Spending", "Actually called onREsume");
		state = true;

		// updateData(null, null, 0);
		// if(trans.spendingMode == 1 && items!= null)
		// repaintGraph(items);

	}

	public Map<String, Integer> updateData(String startDate2, String endDate2,
			int mode)
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

		String sumTransBalances = "Sum(" + DBHelper.TRANSACTION_VALUE + ")";

		Cursor cursor = sqldbase.query(DBHelper.TRANSACTION_TABLE,
				new String[] { DBHelper.TRANSACTION_ID,
						DBHelper.TRANSACTION_CATEGORY, sumTransBalances },
				DBHelper.TRANSACTION_USER + " = '" + userId + "' AND "
						+ DBHelper.TRANSACTION_POSTED + " BETWEEN '"
						+ startDate2 + "' AND '" + endDate2 + "' AND " + DBHelper.TRANSACTION_VALUE + " < '0'", null,
				DBHelper.TRANSACTION_CATEGORY, null, null);

		Log.i("Spend", DatabaseUtils.dumpCursorToString(cursor));

		cursor.moveToFirst();

		Map<String, Integer> items = new HashMap<String, Integer>();
		int totalSum = 0;
		while(!cursor.isAfterLast())
		{
			Log.i("items: ", cursor.getString(1) + "'-'" + cursor.getInt(2));
			if (cursor.getInt(2) < 0)
			{
				items.put(cursor.getString(1), cursor.getInt(2));
				totalSum += cursor.getInt(2);
				
			}
			
			cursor.moveToNext();
		}

		String from[] = { DBHelper.TRANSACTION_CATEGORY, sumTransBalances };
		int to[] = { R.id.spending_category, R.id.spending_balance };

		cursor.moveToFirst();

		// Utility.setListViewHeightBasedOnChildren(spendingList);

		items.put("-1", totalSum);
		
		totalText.setText("Total: " + totalSum);
		Log.i("Spend", "Moded");

		if (mode == 1)
			inflateList(cursor, from, to);
		else
			drawGraph(items);

		return items;

	}

	public void inflateList(Cursor cursor, String[] from, int[] to)
	{
		graphLayout.setVisibility(View.GONE);
		spendingList.setVisibility(View.VISIBLE);
		SimpleCursorAdapter adap = new SimpleCursorAdapter(getActivity(),
				R.layout.listblock_spending_report, cursor, from, to);
		spendingList.setAdapter(adap);
		Log.i("Spend", "Calling Inflate List");
	}

	public void drawGraph(Map<String, Integer> items)
	{
		spendingList.setVisibility(View.GONE);
		graphLayout.setVisibility(View.VISIBLE);

		graph = PieGraph.getNewInstance(getActivity(), items, items.size());
		graphLayout.addView(graph);
		Log.i("Spend", "Calling Draw Graph");

	}

	public void repaintGraph(Map<String, Integer> items)
	{
		if (false)
		{
			graphLayout.removeView(graph);
			graph = PieGraph.getNewInstance(getActivity(), items, items.size());
			// graphLayout.addView(graph);

		}
	}

	@SuppressLint("ValidFragment")
	private class DatePicker extends DialogFragment implements
			DatePickerDialog.OnDateSetListener
	{

		int which = 0;

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

		public void show(android.app.FragmentManager man, String tag, int which)
		{
			this.which = which;
			super.show(man, tag);
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

			String startDate = year + "-" + month + "-" + day;

			if (which == 0)
			{
				SpendingReportsFragment.startDate = startDate;
				startRange[0] = startDate;
				startSpinner.setSelection(0);
			}
			else
			{
				SpendingReportsFragment.endDate = startDate;
				endRange[0] = startDate;
				endSpinner.setSelection(0);
			}

			updateData(SpendingReportsFragment.startDate,
					SpendingReportsFragment.endDate, 1);

			Toast.makeText(getActivity(), startDate, Toast.LENGTH_LONG).show();

		}

	}

}
