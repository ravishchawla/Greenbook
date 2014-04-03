package com.stacksmashers.greenbook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionsActivity, surrounding activity for holding
 * the three swipe view tabs/fragments. Also utilizes the Navigation Drawer
 * and is the central hub of the entire app. 
 * 
 * @author Ravish Chawla
 */
public class TransactionsActivity extends BaseActivity implements
		ActionBar.TabListener
{

	/** The page adapter. */
	private FragmentPagerAdapter pageAdapter;

	/** The viewpager. */
	private ViewPager viewpager;

	/** The action bar. */
	private ActionBar actionBar;

	/** The user id. */
	protected String userID;

	/** The account id. */
	protected int accountID = -1;

	/** The nav list. */
	private ListView navList;

	/** The navigation drawer. */
	private DrawerLayout navigationDrawer;

	/** The action toggle. */
	private ActionBarDrawerToggle actionToggle;

	/** The on create being called. */
	private boolean onCreateBeingCalled = false;

	

	/** The currency query. */
	private ParseQuery<ParseObject> currencyQuery = ParseQuery
			.getQuery(ParseDriver.CURRENCY_TABLE);

	/** The navigation adapter. */
	private ArrayAdapter<ParseObject> navigationAdapter;

	/** The spending tag. */
	private String SPENDING_TAG;

	/** The income tag. */
	private String INCOME_TAG;

	/** The transactions tag. */
	private String TRANSACTIONS_TAG;

	/** The spending reports fragment. */
	protected SpendingReportsFragment spendingReportsFragment;

	/** The income source reports fragment. */
	protected IncomeSourceReportsFragment incomeSourceReportsFragment;

	/**
	 * this class make sure about transction activity.
	 */
	public TransactionsActivity()
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
	 * Called to requery the database to find updated values, and also refresh
	 * the list and graph on the activity with newere data.
	 * 
	 * @return void
	 */
	public void query()
	{
		ParseQuery<ParseObject> accountQuery = ParseQuery
				.getQuery(ParseDriver.ACCOUNT_TABLE);
		accountQuery.whereEqualTo(ParseDriver.USER_ACCOUNT, Vars.userParseObj);

		accountQuery.findInBackground(new FindCallback<ParseObject>()
		{

			/**
			 * called automatically as a callback when Parse finishes finding in
			 * the background
			 * 
			 * @param accountList
			 *            - the list of items it has found
			 * @param exe
			 *            - an exception, null, if worked properly
			 */
			@Override
			public void done(List<ParseObject> accountList, ParseException exe)
			{
				if (accountList != null)
				{
					Vars.accountsParseList = accountList;
					navigationAdapter.clear();
					navigationAdapter.addAll(accountList);
				}
			}
		});
	}

	/**
	 * Called to query all the transactions for this specific user, and update
	 * all views that use that data.
	 * 
	 * @param callUpdate
	 *            - boolean that checks whether to update all views as well,
	 *            useful when not all the views have properly ifnlatd before
	 *            this call.
	 */
	public void queryTransactions(final boolean callUpdate)
	{
		ParseQuery<ParseObject> transactionQuery = ParseQuery
				.getQuery(ParseDriver.TRANSACTION_TABLE);
		Log.i("transfrag ", "" + (Vars.accountParseObj == null));
		transactionQuery.findInBackground(new FindCallback<ParseObject>()
		{
			/**
			 * called automatically as a callback when Parse finishes finding in
			 * the background
			 * 
			 * @param accountList
			 *            - the list of items it has found
			 * @param exe
			 *            - an exception, null, if worked properly
			 */
			@Override
			public void done(List<ParseObject> transactionList,
					ParseException exe)
			{
				if (transactionList != null)
				{
					try
					{
						Vars.transactionParseList = transactionList;
						if (callUpdate)
							updateData();
					}

					catch (Exception aout)
					{

					}
				}
				else
					Log.i("transfragerr ", exe.getMessage());
			}
		});
	}

	/**
	 * Called automatically when the activity is first created. Inflates the xml
	 * layout, finds all views by their ids, and assigns liseners and adapters.
	 * 
	 * @param savedInstanceState
	 *            - saved data from a previous instance
	 * @return void
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		onCreateBeingCalled = true;
		setContentView(R.layout.activity_transaction);
		Bundle extras = getIntent().getExtras();
		userID = extras.getString("User ID");
		viewpager = (ViewPager) findViewById(R.id.transactoins_viewpager);
		currencyQuery.whereEqualTo(ParseDriver.OBJECT_ID,
				Vars.currencyParseObj.getObjectId());
		currencyQuery.findInBackground(new FindCallback<ParseObject>()
		{
			/**
			 * called automatically as a callback when Parse finishes finding in
			 * the background
			 * 
			 * @param currencyList
			 *            - the list of items it has found
			 * @param exe
			 *            - an exception, null, if worked properly
			 */
			@Override
			public void done(List<ParseObject> currencyList, ParseException exe)
			{
				if (currencyList != null)
				{
					ParseObject object = currencyList.get(0);
					Vars.DEF_CURRENCY_SYMBOL = object
							.getString(ParseDriver.CURRENCY_SYMBOL);
					Vars.DEF_CURRENCY_TICKER = object
							.getString(ParseDriver.CURRENCY_TICKER);
					Vars.DEF_CURRENT_ID = object
							.getString(ParseDriver.OBJECT_ID);
				}
			}
		});
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		navList = (ListView) findViewById(R.id.navigation_drawer);
		navigationDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		actionToggle = new ActionBarDrawerToggle(this, navigationDrawer,
				R.drawable.ic_drawer, R.string.open_drawer,
				R.string.close_drawer)
		{
			@Override
			public void onDrawerClosed(View view)
			{
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View view)
			{
				invalidateOptionsMenu();
			}
		};

		navigationDrawer.setDrawerListener(actionToggle);
		navigationAdapter = new NavigationAdapter(getApplicationContext(),
				new ArrayList<ParseObject>());
		navList.setAdapter(navigationAdapter);
		query();
		queryTransactions(false);
		navList.setOnItemClickListener(new OnItemClickListener()
		{

			/**
			 * called automatically when an item in the Navigation Drawer has
			 * been clicked.
			 * 
			 * @param parent
			 *            - a reference to the Adapter that filled this list
			 * @param view
			 *            - a reference to the view that was just clicked
			 * @param pos
			 *            - the position of the item clicked
			 * @param id
			 *            - the id value assigned to the view by its adapter
			 * @return void
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id)
			{
				
				Vars.accountParseObj = Vars.accountsParseList.get(pos);
				
				updateData();
				accountID = pos;
				navigationDrawer.closeDrawer(navList);
			}
		});

		Utility.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		
		pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{
			@Override
			public int getCount()
			{
				return 3;
			}

			/**
			 * Called when a Tab is selected for the first time. It creates a
			 * new instance of the fragment being created, and returns a
			 * reference to that, based on which tab has been clicked.
			 * 
			 * @param - the index based on the position of tab clicked
			 */
			@Override
			public Fragment getItem(int index)
			{
				switch (index)
				{
					case 0:
						return new TransactionsFragment();

					case 1:
						spendingReportsFragment = new SpendingReportsFragment();
						spendingReportsFragment.setRetainInstance(true);
						return spendingReportsFragment;

					case 2:
						incomeSourceReportsFragment = new IncomeSourceReportsFragment();
						incomeSourceReportsFragment.setRetainInstance(true);
						return incomeSourceReportsFragment;
				}

				return null;
			}
		};

		viewpager.setOffscreenPageLimit(2);

		viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			/**
			 * method called automatically when a page has been shifted to.
			 * 
			 * @param position
			 *            - which page has been shiftied to
			 */
			@Override
			public void onPageSelected(int position)
			{
				actionBar.setSelectedNavigationItem(position);
				Log.i("Transct", "calling onREsume()");
				pageAdapter.getItem(position).onResume();
				invalidateOptionsMenu();
			}

			/**
			 * method called automatically when a page is being scrolled, and
			 * has not completly finished scrolling.
			 * 
			 * @param arg0
			 *            - the position of the previous page
			 * @param arg1
			 *            - scrolling factor
			 * @param arg2
			 *            - the posiition of the next page
			 */
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
			}

			/**
			 * method called automatically when the scrolling state of a page
			 * changes
			 * 
			 * @param arg0
			 *            - the position of the page whose values just changed
			 */
			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}
		});

		viewpager.setAdapter(pageAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText("Transactions")
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Spending Report")
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Income Report")
				.setTabListener(this));

		new Handler().postDelayed(new Runnable()
		{
			@Override
			/**
			 * method called after 2000 milliseconds have passed
			 * @return  void
			 */
			public void run()
			{
				navigationDrawer.openDrawer(navList);
			}

		}, 2000);
	}

	/**
	 * method called to obtain new data from the database, and to set adapters
	 * in this view with the newer data
	 * 
	 * @return voiod
	 */
	protected void updateData()
	{
		final TransactionsFragment transactionsFragment = (TransactionsFragment) getSupportFragmentManager()
				.findFragmentByTag(TRANSACTIONS_TAG);
		final SpendingReportsFragment spendingFragment = (SpendingReportsFragment) getSupportFragmentManager()
				.findFragmentByTag(SPENDING_TAG);
		final IncomeSourceReportsFragment incomeFragment = (IncomeSourceReportsFragment) getSupportFragmentManager()
				.findFragmentByTag(INCOME_TAG);
		transactionsFragment.transactionAdapter.clear();
		String objId = Vars.accountParseObj.getObjectId();
		Vars.transactionAccountParseList.clear();
		Log.d("transacacc", Vars.transactionParseList.toString());
		Vars.transactionTotalSum = 0.0;
		for (ParseObject obj : Vars.transactionParseList)
		{
			if (obj.getParseObject(ParseDriver.ACCOUNT_TRANSACTION)
					.getObjectId().equals(objId))
			{
				Vars.transactionAccountParseList.add(obj);
				Log.d("transagcc",
						"adding "
								+ obj.getDouble(ParseDriver.TRANSACTION_VALUE));
			}
		}
		Log.d("transac", Vars.transactionAccountParseList.toString() + "");
		Log.d("transac", Vars.transactionTotalSum + "");
		if (transactionsFragment != null)
		{
			transactionsFragment.transactionAdapter
					.addAll(Vars.transactionAccountParseList);
			transactionsFragment.updateTotal();
		}

		if (spendingFragment != null)
		{
			spendingFragment.spendingAdapter.clear();

			spendingFragment.spendingAdapter.addAll(spendingFragment.regroup());
			spendingFragment.refreshGraph();
		}

		if (incomeFragment != null)
		{
			incomeFragment.incomeAdapter.clear();
			incomeFragment.incomeAdapter.addAll(incomeFragment.regroup());
			incomeFragment.refreshGraph();
		}
	}

	/**
	 * method called automatically when the activity goes into the resumed state
	 * from a paused state
	 * 
	 * @return void
	 */
	@Override
	protected void onResume()
	{
		Log.d("transac", "transactionactivity.onresume()");
		super.onResume();
		navigationAdapter.clear();
		navigationAdapter.addAll(Vars.accountsParseList);

		if (onCreateBeingCalled || Vars.accountParseObj == null)
		{
			onCreateBeingCalled = false;
			return;
		}
		updateData();
	}

	/**
	 * method called automatically when the activity goes into the paused state
	 * 
	 * @return void
	 */
	@Override
	protected void onPause()
	{
		super.onPause();
		Log.d("transac", "transactionactivity.onpause()");
		onCreateBeingCalled = false;
	}

	/**
	 * method called automatically when the activity gets destroyed
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Log.d("transac", "transactionactivity.ondestroy()");
	}

	/**
	 * method called automatically when the activity starts. it finds a menu by
	 * id, and inflates it on top of the screen. This activity uses this
	 * extensiviely. When a page switches, the current menu is invalidated, so
	 * this method is recalled, and it changes the current menu based on which
	 * page is open right now.
	 * 
	 * @return menu - the menu being inflated
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		if (navigationDrawer.isDrawerOpen(navList))
			getMenuInflater().inflate(R.menu.menu_drawer, menu);

		else
		{
			Log.i("Debug", "Create");
			int sel = getActionBar().getSelectedNavigationIndex();
			if (sel == 0)
				getMenuInflater().inflate(R.menu.menu_transactions, menu);

			else if (sel == 1)
				getMenuInflater().inflate(R.menu.menu_spending, menu);

			else if (sel == 2)
				getMenuInflater().inflate(R.menu.menu_income, menu);
		}

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Called automatically when an item in the options menu is selected. method
	 * also takes in account which page is currently open.
	 * 
	 * @param item
	 *            - a reference to the men item just selected
	 * @return boolean
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		TransactionsFragment transactionsFragment = (TransactionsFragment) getSupportFragmentManager()
				.findFragmentByTag(TRANSACTIONS_TAG);

		if (actionToggle.onOptionsItemSelected(item))
		{
			return true;
		}

		if (item.getItemId() == R.id.transactions_refresh)
		{
			queryTransactions(true);
		}

		if (item.getItemId() == R.id.accounts_refresh)
			query();

		if (item.getItemId() == R.id.drawer_edit)
		{
			Intent accounts = new Intent(getApplicationContext(),
					AccountsFragment.class);
			accounts.putExtra("User ID", userID);
			startActivity(accounts);
		}

		if (item.getItemId() == R.id.accounts_settings)
		{
			Intent settings = new Intent(getApplicationContext(),
					SettingsActivity.class);
			settings.putExtra("User ID", userID);
			startActivity(settings);

			return super.onOptionsItemSelected(item);
		}
		if (item.getItemId() == R.id.transactions_add)
		{

			if (accountID == -1)
				Toast.makeText(getApplicationContext(),
						"Please Select an account from the sidebar",
						Toast.LENGTH_LONG).show();

			else
				transactionsFragment.addTransaction();

			return super.onOptionsItemSelected(item);
		}

		SpendingReportsFragment spendingFragment = (SpendingReportsFragment) getSupportFragmentManager()
				.findFragmentByTag(SPENDING_TAG);

		IncomeSourceReportsFragment incomeFragment = (IncomeSourceReportsFragment) getSupportFragmentManager()
				.findFragmentByTag(INCOME_TAG);

		if (item.getItemId() == R.id.spending_view_type)
		{
			if (item.isChecked())
			{
				item.setIcon(R.drawable.content_list);
				spendingFragment.displayView(0);
				item.setChecked(false);
			}
			else
			{
				item.setIcon(R.drawable.content_graph);
				spendingFragment.displayView(1);
				item.setChecked(true);
			}

			return super.onOptionsItemSelected(item);
		}

		if (item.getItemId() == R.id.income_view_type)
		{
			if (item.isChecked())
			{
				item.setIcon(R.drawable.content_graph);
				incomeFragment.displayView(1);
				item.setChecked(false);
			}
			else
			{
				item.setIcon(R.drawable.content_list);
				incomeFragment.displayView(0);
				item.setChecked(true);
			}

			return super.onOptionsItemSelected(item);
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * method called after onCreate has been called.
	 * 
	 * @param savedInstanceState
	 *            - saved state from a previous instance
	 * @return void
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{

		super.onPostCreate(savedInstanceState);
		actionToggle.syncState();

	};

	/**
	 * method called when the state of the page changes (ie., navigation drawer
	 * closed, opened, viewpager on, off, etc..)
	 * 
	 * @param newConfig
	 *            - the newer configuration of the activity
	 * @return void
	 */

	@Override
	public void onConfigurationChanged(
			android.content.res.Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		actionToggle.onConfigurationChanged(newConfig);
	};

	/**
	 * Sets the spending report tag
	 * 
	 * @param tag
	 *            the new spending report tag
	 */
	public void setSpendingReportTag(String tag)
	{
		SPENDING_TAG = tag;
	}

	/**
	 * Sets the income report tag.
	 * 
	 * @param tag
	 *            the new income report tag
	 */
	public void setIncomeReportTag(String tag)
	{
		INCOME_TAG = tag;
	}

	/**
	 * Gets the spending report tag.
	 * 
	 * @return the spending report tag
	 */
	public String getSpendingReportTag()
	{
		return SPENDING_TAG;
	}

	/**
	 * Gets the income report tag.
	 * 
	 * @return the income report tag
	 */
	public String getIncomeReportTag()
	{
		return INCOME_TAG;
	}

	/**
	 * Sets the transaction tag.
	 * 
	 * @param tag
	 *            the new transaction tag
	 */
	public void setTransactionTag(String tag)
	{
		TRANSACTIONS_TAG = tag;
	}

	/**
	 * Gets the transaction tag.
	 * 
	 * @return the transaction tag
	 */
	public String getTransactionTag()
	{
		return TRANSACTIONS_TAG;
	}

	/**
	 * method automatically called when a tab from the actionbar is reselected
	 * 
	 * @param tab
	 *            - the tab reselected
	 * @ft - FragmentTransaction from base context
	 * @return void
	 */
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{
	}

	/**
	 * method called automatically when a tab from the action bar is selected
	 * 
	 * @param tab
	 *            - the tab selected
	 * @param ft
	 *            - fragmentTransaction from base context
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		viewpager.setCurrentItem(tab.getPosition());
	}

	/**
	 * called automatically when a tab is deselected from the actionbar
	 * 
	 * @param tab
	 *            - the tab deselected
	 * @param ft
	 *            - fragmentTransaction from base context
	 * @return void
	 */
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{
	}

	/**
	 * A custom array adatper that sets the values of the 
	 * list based on data from the database
	 */
	class NavigationAdapter extends ArrayAdapter<ParseObject>
	{

		/** The context. */
		private Context context;

		/** The parse list. */
		private List<ParseObject> parseList;

		/**
		 * Instantiates a new navigation adapter.
		 * 
		 * @param _context
		 *            the _context
		 * @param _parseList
		 *            the _parse list
		 */
		public NavigationAdapter(Context _context, List<ParseObject> _parseList)
		{
			super(_context, R.layout.drawer_item, _parseList);
			context = _context;
			parseList = _parseList;

		}

		/**
		 * called internallly by the adapter when setting views. ovveridden here to 
		 * modify the view before it is put in the adapter. 
		 * @param position - the position of the view
		 * @param convertView - the view being modified
		 * @param parent - a container for all views
		 * @return View - the modified view 
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = LayoutInflater.from(context).inflate(
						R.layout.drawer_item, null);
			}

			ParseObject object = parseList.get(position);

			((TextView) convertView.findViewById(R.id.drawer_textView))
					.setText(object.getString(ParseDriver.ACCOUNT_NAME));

			return convertView;
		}
	}
}