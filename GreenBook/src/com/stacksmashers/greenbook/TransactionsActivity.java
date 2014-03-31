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

public class TransactionsActivity extends BaseActivity implements
		ActionBar.TabListener
{

	private FragmentPagerAdapter pageAdapter;
	private ViewPager viewpager;
	private TransactionsFragment transactionFragment;
	private ActionBar actionBar;
	protected String userID;
	protected int accountID = -1;
	private ListView navList;
	private DrawerLayout navigationDrawer;
	private ActionBarDrawerToggle actionToggle;

	private boolean onCreateBeingCalled = false;
	ParseQuery<ParseObject> userQuery = ParseQuery
			.getQuery(ParseDriver.USER_TABLE);
	ParseQuery<ParseObject> currencyQuery = ParseQuery
			.getQuery(ParseDriver.CURRENCY_TABLE);

	ArrayAdapter navigationAdapter;

	// private TabsPageAdapter tabsPageAdapter;

	int spendingMode;
	int incomeMode;
	String SPENDING_TAG;
	String INCOME_TAG;
	String TRANSACTIONS_TAG;

	protected SpendingReportsFragment spendingReportsFragment;

	protected IncomeSourceReportsFragment incomeSourceReportsFragment;



	/**
	 * this class make sure about transction activity
	 */
	public TransactionsActivity()
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

	public void query()
	{
		ParseQuery<ParseObject> accountQuery = ParseQuery
				.getQuery(ParseDriver.ACCOUNT_TABLE);
		accountQuery.whereEqualTo(ParseDriver.USER_ACCOUNT, Vars.userParseObj);

		accountQuery.findInBackground(new FindCallback<ParseObject>()
		{

			@Override
			public void done(List<ParseObject> accountList, ParseException exe)
			{
				// TODO Auto-generated method stub

				if (accountList != null)
				{
					Vars.accountsParseList = accountList;
					navigationAdapter.clear();
					navigationAdapter.addAll(accountList);
				}

			}
		});

	}

	public void queryTransactions(final boolean callUpdate)
	{
		ParseQuery<ParseObject> transactionQuery = ParseQuery
				.getQuery(ParseDriver.TRANSACTION_TABLE);
		// transactionQuery.whereEqualTo(ParseDriver.ACCOUNT_TRANSACTION,
		// Vars.accountParseObj);

		final TransactionsFragment transactionsFragment = (TransactionsFragment) getSupportFragmentManager()
				.findFragmentByTag(TRANSACTIONS_TAG);

		final SpendingReportsFragment spendingFragment = (SpendingReportsFragment) getSupportFragmentManager()
				.findFragmentByTag(SPENDING_TAG);

		final IncomeSourceReportsFragment incomeFragment = (IncomeSourceReportsFragment) getSupportFragmentManager()
				.findFragmentByTag(INCOME_TAG);


		Log.i("transfrag ", "" + (Vars.accountParseObj == null));
		transactionQuery.findInBackground(new FindCallback<ParseObject>()
		{

			@Override
			public void done(List<ParseObject> transactionList,
					ParseException exe)
			{
				// TODO Auto-generated method stub

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
	 * @param savedInstanceState
	 * @return void we call this method to initalize this activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState); // initalize savedinstancestate

		onCreateBeingCalled = true;
		setContentView(R.layout.activity_transaction); // call setcontentview
		Bundle extras = getIntent().getExtras(); // get intent bundle extras

		userID = extras.getString("User ID");
		viewpager = (ViewPager) findViewById(R.id.transactoins_viewpager);

		currencyQuery.whereEqualTo(ParseDriver.OBJECT_ID,
				Vars.currencyParseObj.getObjectId());

		currencyQuery.findInBackground(new FindCallback<ParseObject>()
		{

			@Override
			public void done(List<ParseObject> currencyList, ParseException exe)
			{
				// TODO Auto-generated method stub

				if (currencyList != null)
				{
					// Vars.DEF_CURRENT_ID =
					// usersList.get(0).getString(ParseDriver.USER_CURRENCY);

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
		actionBar = getActionBar(); // get action bar
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// actionBar.setDisplayShowTitleEnabled(true); // enabled display show

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

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id)
			{
				// TODO Auto-generated method stub
				TransactionsFragment transactionsFragment = (TransactionsFragment) getSupportFragmentManager()
						.findFragmentByTag(TRANSACTIONS_TAG);

				SpendingReportsFragment spendingFragment = (SpendingReportsFragment) getSupportFragmentManager()
						.findFragmentByTag(SPENDING_TAG);

				IncomeSourceReportsFragment incomeFragment = (IncomeSourceReportsFragment) getSupportFragmentManager()
						.findFragmentByTag(INCOME_TAG);


				Vars.accountParseObj = Vars.accountsParseList.get(pos);

				String titleText = "Transactions";

				updateData();
				accountID = pos;
				navigationDrawer.closeDrawer(navList);

			}

		});

		// navList.setAdapter(mAdapter);

		Utility.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());

		String titleText = "Transactions";

		pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				// TODO Auto-generated method stub
				return 3;
			}

			@Override
			public Fragment getItem(int index)
			{
				switch (index)
				{
					case 0:
						return transactionFragment = new TransactionsFragment();

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

				// TODO Auto-generated method stub

			}
		};
		viewpager.setOffscreenPageLimit(2);
		viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int position)
			{
				// TODO Auto-generated method stub

				actionBar.setSelectedNavigationItem(position);

				Log.i("Transct", "calling onREsume()");
				pageAdapter.getItem(position).onResume();

				invalidateOptionsMenu();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		viewpager.setAdapter(pageAdapter);
		// actionBar.setHomeButtonEnabled(false);
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
			 * @return void 
			 */
			public void run()
			{

				navigationDrawer.openDrawer(navList);

			}

		}, 2000);

	}

	protected void updateData()
	{
		// TODO Auto-generated method stub
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

	@Override
	protected void onPause()
	{
		super.onPause();
		Log.d("transac", "transactionactivity.onpause()");

		onCreateBeingCalled = false;
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Log.d("transac", "transactionactivity.ondestroy()");
	}

	/**
	 * 
	 * @param menu
	 * @return boolean we use this method to create option menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		if (navigationDrawer.isDrawerOpen(navList))
			getMenuInflater().inflate(R.menu.menu_drawer, menu);

		else
		{

			// TODO Auto-generated method stub
			Log.i("Debug", "Create");
			int sel = getActionBar().getSelectedNavigationIndex();
			if (sel == 0)
				getMenuInflater().inflate(R.menu.menu_transactions, menu);

			else if (sel == 1)
				getMenuInflater().inflate(R.menu.menu_spending, menu);

			else if (sel == 2)
				getMenuInflater().inflate(R.menu.menu_income, menu);
			
			
			
			// submenu.add("Stranger").setIcon(R.drawable.content_forgot);
		}
		// }

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * @return boolean
	 * @param item
	 *            we use this method to select items
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub

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

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{

		super.onPostCreate(savedInstanceState);
		actionToggle.syncState();

	};

	@Override
	public void onConfigurationChanged(
			android.content.res.Configuration newConfig)
	{

		super.onConfigurationChanged(newConfig);
		actionToggle.onConfigurationChanged(newConfig);

	};

	public void setSpendingReportTag(String tag)
	{
		SPENDING_TAG = tag;
	}

	public void setIncomeReportTag(String tag)
	{
		INCOME_TAG = tag;
	}

	
	public String getSpendingReportTag()
	{
		return SPENDING_TAG;
	}

	public String getIncomeReportTag()
	{
		return INCOME_TAG;
	}

	
	public void setTransactionTag(String tag)
	{
		TRANSACTIONS_TAG = tag;
	}

	public String getTransactionTag()
	{
		return TRANSACTIONS_TAG;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub

		viewpager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub

	}

	class NavigationAdapter extends ArrayAdapter<ParseObject>
	{
		private Context context;
		private List<ParseObject> parseList;

		public NavigationAdapter(Context _context, List<ParseObject> _parseList)
		{
			super(_context, R.layout.drawer_item, _parseList);
			context = _context;
			parseList = _parseList;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub

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
