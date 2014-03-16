package com.stacksmashers.greenbook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class SpendingReportsFragment extends BaseFragment implements
		DataFragmentInterface
{

	private Spinner startSpinner;
	private Spinner endSpinner;
	private LinearLayout graphLayout;
	private TransactionsActivity trans;
	// private ListView spendingList;
	GraphicalView graph;
	private int userId;
	private String startRange[] = new String[2];
	private String endRange[] = new String[2];
	static String startDateString, endDateString;
	private ArrayAdapter<String> startAdapter, endAdapter;
	private SimpleDateFormat dateFormat;
	private SimpleDateFormat longDateFormat;
	private SimpleCursorAdapter mListAdapter;
	boolean state = false;
	Cursor dataCursor;
	View view;
	LinearLayout mLinLayout;
	public int viewMode;
	public static int LIST_VIEW = 0;
	public static int GRAPH_VIEW = 1;

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

	public void refresh(int mode)
	{
		String currentDate = dateFormat.format(new Date());
		updateData();

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

		((TransactionsActivity) getActivity()).setSpendingReportTag(getTag());

		dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		longDateFormat = new SimpleDateFormat("EEE, MMMM d, yyyy",
				Locale.getDefault());
		startRange[1] = "Select Date";
		endRange[1] = "Select Date";

		startSpinner = (Spinner) view.findViewById(R.id.spending_spinner_start);
		endSpinner = (Spinner) view.findViewById(R.id.spending_spinner_end);

		graphLayout = (LinearLayout) view
				.findViewById(R.id.spending_report_graph);

		trans = (TransactionsActivity) getActivity();
		userId = trans.userID;

		String currentDate = longDateFormat.format(new Date());

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

		updateData();

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

	public void query()
	{
		String sumTransBalances = "Sum(" + DBHelper.TRANSACTION_VALUE + ")";

		dataCursor = sqldbase.query(DBHelper.TRANSACTION_TABLE, new String[] {
				DBHelper.TRANSACTION_ID, DBHelper.TRANSACTION_CATEGORY,
				sumTransBalances }, DBHelper.TRANSACTION_USER + " = '" + userId
				+ "' AND " + DBHelper.TRANSACTION_POSTED + " BETWEEN '"
				+ startDateString + "' AND '" + endDateString + "' AND "
				+ DBHelper.TRANSACTION_VALUE + " < '0'", null,
				DBHelper.TRANSACTION_CATEGORY, null,null);
		
		

		Log.i("Spend", DatabaseUtils.dumpCursorToString(dataCursor));
		
	}

	public void refreshGraph()
	{

		Map<String, Integer> items = new HashMap<String, Integer>();
		int totalSum = 0;
		dataCursor.moveToFirst();
		while (!dataCursor.isAfterLast())
		{
			Log.i("items: ",
					dataCursor.getString(1) + "'-'" + dataCursor.getInt(2));
			if (dataCursor.getInt(2) < 0)
			{
				items.put(dataCursor.getString(1), dataCursor.getInt(2));
				totalSum += dataCursor.getInt(2);

			}

			dataCursor.moveToNext();
		}

		items.put("-1", totalSum);
		graph = PieGraph.getNewInstance(getActivity(), items, items.size());
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

		String sumTransBalances = "Sum(" + DBHelper.TRANSACTION_VALUE + ")";
		query();

		Log.i("Spend", DatabaseUtils.dumpCursorToString(dataCursor));

		dataCursor.moveToFirst();

		String from[] = { DBHelper.TRANSACTION_CATEGORY, sumTransBalances };
		int to[] = { R.id.spending_category, R.id.spending_balance };

		dataCursor.moveToFirst();

		mListAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.listblock_spending_report, dataCursor, from, to);
		// Utility.setListViewHeightBasedOnChildren(spendingList);

		Log.i("Spend", "Moded");

		// rootLayout =
		// (LinearLayout)view.findViewById(R.id.child_linearlayout);

		mLinLayout = (LinearLayout) view
				.findViewById(R.id.spending_graphicalview);

		ListView mListView = (ListView) view
				.findViewById(R.id.spending_listview);
		mListView.setAdapter(mListAdapter);

		refreshGraph();

		displayView(0);
		Log.i("Spending", "Added Graph");

		return;

	}

	public void displayView(int mode)
	{
		LinearLayout mLinLayout = (LinearLayout) view
				.findViewById(R.id.spending_graphicalview);
		ListView mListView = (ListView) view
				.findViewById(R.id.spending_listview);
		if (LIST_VIEW == (viewMode = mode))
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

			try
			{

				longStartDate = longDateFormat.format(dateFormat.parse(date));

			}
			catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (whichSpinner == 0)
			{
				SpendingReportsFragment.startDateString = date;
				startRange[0] = longStartDate;
				startSpinner.setSelection(0);
			}
			else
			{
				SpendingReportsFragment.endDateString = date;
				endRange[0] = longStartDate;
				endSpinner.setSelection(0);
			}

			query();
			Log.i("Spend", "queried");


				Log.i("Spend", "listed");
				mListAdapter.changeCursor(dataCursor);
				mListAdapter.notifyDataSetChanged();




				Log.i("Spend", "refreshing");
				refreshGraph();
				Log.i("Spend", "called refresh");


		}

	}

}
