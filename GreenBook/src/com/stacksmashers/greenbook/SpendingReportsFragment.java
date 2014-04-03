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

/**
 * The Class that displays to the user an Spending Report, and controls all
 * actions related to that report
 * 
 * @author Ravish Chawla
 */
public class SpendingReportsFragment extends BaseFragment
{
	/** The start spinner. */
	private Spinner startSpinner;

	/** The end spinner. */
	private Spinner endSpinner;

	/** The graph. */
	private GraphicalView graph;

	/** The start range. */
	private String startRange[] = new String[2];

	/** The end range. */
	private String endRange[] = new String[2];

	/** The end date. */
	private static Date startDate, endDate;

	/** The end adapter. */
	private ArrayAdapter<String> startAdapter, endAdapter;

	/** The spending adapter. */
	public ArrayAdapter<Entry<String, Double>> spendingAdapter;

	/** The view. */
	private View view;

	/** The m lin layout. */
	private LinearLayout mLinLayout;

	/** The view mode. */
	public int viewMode;

	/** The ad view. */
	private AdView adView;

	/**
	 * Instantiates a new spending reports fragment.
	 */
	public SpendingReportsFragment()
	{
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
	 * Creates a new instance of this fragment and returns it, used for external
	 * intents
	 * 
	 * @param text
	 *            the text
	 * @return the spending reports fragment
	 */
	public static SpendingReportsFragment newInstance(String text)
	{
		SpendingReportsFragment frag = new SpendingReportsFragment();
		Bundle bundle = new Bundle();
		bundle.putString("Strings", text);
		frag.setArguments(bundle);

		return frag;
	}

	/**
	 * Automatically called when the Fragment is first initialized. Inflates the
	 * xml layout, finds associated views, and adds listeners to views
	 * 
	 * @param inflater
	 *            - the LayoutInflater of parent activity
	 * @param container
	 *            - The group this view is associated with
	 * @param savedInstanceState
	 *            - saved state from a previous instance
	 * @return View - modified View with correct inflation
	 */
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_spending_report_table,
				container, false);
		startRange[1] = "Select Date";
		endRange[1] = "Select Date";
		startSpinner = (Spinner) view.findViewById(R.id.spending_spinner_start);
		endSpinner = (Spinner) view.findViewById(R.id.spending_spinner_end);
		startDate = new Date();
		endDate = new Date();
		startDate.setYear(startDate.getYear() - 1);
		endDate.setYear(endDate.getYear() + 1);
		String currentDate = Vars.longDateFormat.format(startDate);
		startRange[0] = currentDate;
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
		/**
		 * Automatically called when a spinner item is selected
		 * 
		 * @param arg0
		 *            - The adapter associated with the spinner
		 * @param view
		 *            - the view associated with the spinner
		 * @param pos
		 *            - the position currently selected
		 * @param arg3
		 *            - id of the item from the adapter
		 */

		startSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3)
			{

				if (pos == 1)
				{
					DatePicker dateFragment = new DatePicker(0);
					dateFragment.show(getActivity().getFragmentManager(),
							"DatePicker");
				}

			}

			/**
			 * called automatically if no item is selected (ie. back pressed)
			 */

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub

			}

		});

		/**
		 * Automatically called when a spinner item is selected
		 * 
		 * @param arg0
		 *            - The adapter associated with the spinner
		 * @param view
		 *            - the view associated with the spinner
		 * @param pos
		 *            - the position currently selected
		 * @param arg3
		 *            - id of the item from the adapter
		 */

		endSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3)
			{
				if (pos == 1)
				{
					DatePicker dateFragment = new DatePicker(1);
					dateFragment.show(getActivity().getFragmentManager(),
							"DatePicker");
				}
			}

			/**
			 * automatically called if no item is selected (ie. back pressed)
			 */

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub

			}

		});

		((TransactionsActivity) getActivity()).setSpendingReportTag(getTag());

		adView = (AdView) view.findViewById(R.id.adViewSpending);

		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
				Vars.HASHED_DEVICE_ID).build();

		adView.loadAd(adRequest);

		adView.setAdListener(new AdListener()
		{
			/**
			 * called when the ad is first initialized
			 * 
			 * @return void
			 */

			@Override
			public void onAdOpened()
			{
				Log.d("adopen", "ad opened");
			}

			/**
			 * called if the ad fails to load. diplays appropriate error log
			 * 
			 * @param error
			 *            - the error code
			 * @return void
			 */

			@Override
			public void onAdFailedToLoad(int error)
			{
				switch (error)
				{
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

			/**
			 * called when the ad is ifinished loading
			 * 
			 * @return void
			 */
			@Override
			public void onAdLoaded()
			{
				Log.d("adload", "ad loaded properly");
			}

		});

		updateData();

		return view;
	}

	/**
	 * Automatically called when the Activity enters into the resume state from
	 * a paused state
	 * 
	 * 
	 */
	@Override
	public void onResume()
	{

		if (adView != null)
			adView.resume();

		super.onResume();
		Log.i("Spending", "Actually called onREsume");
		
	}

	/**
	 * Automatically called when the activity gets destroyed
	 * 
	 * @param void
	 */
	@Override
	public void onPause()
	{
		if (adView != null)
			adView.pause();
		super.onPause();

	}

	/**
	 * Automatically called when the activity gets destroyed
	 * 
	 * @param void
	 */
	@Override
	public void onDestroy()
	{
		if (adView != null)
			adView.destroy();
		super.onDestroy();

	}

	/**
	 * Regroup the query elements into a collection of objects needed for this
	 * report. Mimics the Group by clause of SQL
	 * 
	 * @return List the collection of reformated objects
	 */
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

		return new ArrayList<Entry<String, Double>>(transactionGroups.entrySet());

	}

	/**
	 * Reload the graph with newer data.
	 * 
	 * @return void
	 */
	public void refreshGraph()
	{

		mLinLayout = (LinearLayout) view
				.findViewById(R.id.spending_graphicalview);

		graph = PieGraph.getNewInstance(getActivity(),
				Vars.transactionWithrawParseMap, Vars.transactionWithrawSum);
		graph.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		mLinLayout.removeAllViews();
		mLinLayout.addView(graph);
	}

	/**
	 * Update the list with refreshed data from Database
	 * 
	 * @return void
	 */
	public void updateData()
	{
		startAdapter.notifyDataSetChanged();
		endAdapter.notifyDataSetChanged();
		Log.i("Spend", "Moded");
		ListView mListView = (ListView) view
				.findViewById(R.id.spending_listview);

		spendingAdapter = new SpendingAdapter(getActivity(),
				new ArrayList<Entry<String, Double>>());
		mListView.setAdapter(spendingAdapter);
		displayView(1);
		Log.i("Spending", "Added Graph");

		return;
	}

	/**
	 * Replaces the current view (from graph to view and viceversa)
	 * 
	 * @param mode
	 *            - which mode to switch to
	 */
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

	/**
	 * A DatePicker Dialog class that asks user to enter a date
	 * 
	 * @author Ravish Chawla
	 */
	@SuppressLint("ValidFragment")
	private class DatePicker extends DialogFragment implements
			DatePickerDialog.OnDateSetListener
	{

		/** The which spinner. */
		private int whichSpinner = 0;

		/**
		 * Instantiates a new date picker.
		 * 
		 * @param whichSpinner
		 *            the which spinner
		 */
		public DatePicker(int whichSpinner)
		{

			this.whichSpinner = whichSpinner;
		}

		/**
		 * Automatically called when the dialog is firs created
		 * 
		 * @param savedInstanceState
		 *            - saved data from previous instance
		 */
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			final Calendar c = Calendar.getInstance();
			int day = c.get(Calendar.DAY_OF_MONTH);
			int month = c.get(Calendar.MONTH);
			int year = c.get(Calendar.YEAR);

			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		/**
		 * Automatically called when the cancel button is clicked in the dialog
		 * 
		 * @param dialog
		 *            - reference to the dialog currently in view
		 * @return void
		 */
		@Override
		public void onCancel(DialogInterface dialog)
		{
			if (whichSpinner == 0)
				startSpinner.setSelection(0);
			else
				endSpinner.setSelection(0);

			super.onCancel(dialog);
		}

		/**
		 * Automatically called when a date from the picker is selected
		 * 
		 * @param view
		 *            - reference to the dialog currently being viewed
		 * @param year
		 *            - the year selected
		 * @param monthOfYear
		 *            - the month selected
		 * @param dayOfMonth
		 *            - the day selected
		 */
		@Override
		public void onDateSet(android.widget.DatePicker view, int year,
				int monthOfYear, int dayOfMonth)
		{
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

	/**
	 * An arrayadapter used to fill the list of items with data from the parse
	 * database
	 */
	class SpendingAdapter extends ArrayAdapter<Entry<String, Double>>
	{

		/** The context. */
		private Context context;

		/** The entries. */
		private List<Entry<String, Double>> entries;

		/** The posit. */
		private int posit = 0;

		/**
		 * Instantiates a new income adapter.
		 * 
		 * @param _context
		 *            the _context
		 * @param _parseList
		 *            the _parse list
		 */
		public SpendingAdapter(Context _context,
				List<Entry<String, Double>> _parseList)
		{
			super(_context, R.layout.listblock_spending_report, _parseList);
			context = _context;
			entries = _parseList;

		}

		/**
		 * called to clear all elements in the current adapter
		 * 
		 * @return void
		 */
		@Override
		public void clear()
		{

			posit = 0;

		};

		/**
		 * used by the adapter to get view for each individual item. overriden
		 * to use parsed data
		 */
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

			((View) convertView.findViewById(R.id.spending_icon))
					.setBackgroundColor(Utility.getColor(posit++));

			return convertView;

		}

	}

}
