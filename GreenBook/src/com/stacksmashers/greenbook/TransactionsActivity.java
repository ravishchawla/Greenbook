package com.stacksmashers.greenbook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TransactionsActivity extends BaseActivity implements
		ActionBar.TabListener
{

	private FragmentPagerAdapter pageAdapter;
	private ViewPager viewpager;
	private TransactionsFragment transactionFragment;
	private ActionBar actionBar;
	protected int userID;
	protected int accountID = -1;
	protected String accountName;
	public Cursor navCursor;
	public SimpleCursorAdapter navAdap;
	private ListView navList;
	private DrawerLayout navigationDrawer;
	private ActionBarDrawerToggle actionToggle;
	
	// private TabsPageAdapter tabsPageAdapter;

	int spendingMode;
	String SPENDING_TAG;
	String TRANSACTIONS_TAG;

	protected SpendingReportsFragment spendingReportsFragment;

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
		navCursor = DBDriver.GET_ALL_ACCOUNT_INFO(userID, DBHelper.ACCOUNT_ID);
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

		setContentView(R.layout.activity_transaction); // call setcontentview
		Bundle extras = getIntent().getExtras(); // get intent bundle extras

		userID = extras.getInt("User ID");
		viewpager = (ViewPager) findViewById(R.id.transactoins_viewpager);

		navCursor = DBDriver.GET_DEFAULT_CURRENCY(userID);
		navCursor.moveToFirst();
		Vars.DEF_CURRENT_ID = navCursor.getInt(0);
		navCursor = DBDriver.GET_CURRENCY(navCursor.getInt(0));
		navCursor.moveToFirst();
		Vars.DEF_CURRENCY_SYMBOL = navCursor.getString(2);
		Vars.DEF_CURRENCY_TICKER = navCursor.getString(1);
		// initializePaging();

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

			public void onDrawerClosed(View view)
			{
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View view)
			{
				invalidateOptionsMenu();
			}

		};

		navigationDrawer.setDrawerListener(actionToggle);

		String from[] = { DBHelper.ACCOUNT_NAME };
		int to[] = { R.id.drawer_textView };

		query();

		Log.i("TAG", "Cursor Adap = null: " + navCursor.getCount());
		Log.i("Trans", DatabaseUtils.dumpCursorToString(navCursor));
		if (navCursor.getCount() != 0)
		{
			navCursor.moveToFirst();
			navAdap = new SimpleCursorAdapter(
					getApplicationContext(), R.layout.drawer_item, navCursor, from,
					to);

			navList.setAdapter(navAdap);
		}

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

				accountID = (int) id;
				
				

				Cursor caeser = DBDriver.ACCOUNT_NAME_FROM_ID(accountID);
				String titleText = "Transactions";
				if (caeser.getCount() != 0)
				{
					caeser.moveToFirst();
					accountName = caeser.getString(0);

				}

				Toast.makeText(getApplicationContext(),
						"Switching Class to: " + accountName, Toast.LENGTH_LONG)
						.show();
				transactionsFragment.query(accountID, userID);
				transactionsFragment.refreshList();

				spendingFragment.query();
				spendingFragment.refreshList();
				spendingFragment.refreshGraph();

				navigationDrawer.closeDrawer(navList);

			}

		});

		// navList.setAdapter(mAdapter);

		Utility.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());

		Cursor caeser = DBDriver.ACCOUNT_NAME_FROM_ID(accountID);
		String titleText = "Transactions";
		if (caeser.getCount() != 0)
		{
			caeser.moveToFirst();
			accountName = caeser.getString(0);

		}

		// actionBar.addTab(tab);

		/*
		 * viewpager .setOnPageChangeListener(new
		 * ViewPager.SimpleOnPageChangeListener() {
		 * 
		 * /**
		 * 
		 * @param position
		 * 
		 * @return void we use this method when new page become selected
		 */
		/*
		 * @Override public void onPageSelected(int position) {
		 * getActionBar().setSelectedNavigationItem(position);
		 * 
		 * invalidateOptionsMenu();
		 * 
		 * }
		 * 
		 * });
		 */

		pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				// TODO Auto-generated method stub
				return 2;
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

				}

				return null;

				// TODO Auto-generated method stub

			}
		};
		viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int position)
			{
				// TODO Auto-generated method stub

				actionBar.setSelectedNavigationItem(position);

				if (position == 1)
				{
					Log.i("Transct", "calling onREsume()");
					pageAdapter.getItem(position).onResume();
				}

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
	//	actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		actionBar.addTab(actionBar.newTab().setText("Transactions")
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Spending Report")
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

		}, 1000);

	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
	
		query();
		navAdap.changeCursor(navCursor);
		navAdap.notifyDataSetChanged();
		
		super.onResume();
	}
	
	
	/**
	 * 
	 * @param menu
	 * @return boolean we use this method to create option menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		if(navigationDrawer.isDrawerOpen(navList))
			getMenuInflater().inflate(R.menu.menu_drawer, menu);
		
		else{
		
		// TODO Auto-generated method stub
		Log.i("Debug", "Create");
		if (getActionBar().getSelectedNavigationIndex() == 0)
			getMenuInflater().inflate(R.menu.menu_transactions, menu);

		else

			getMenuInflater().inflate(R.menu.menu_spending, menu);

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

		if(item.getItemId() == R.id.drawer_edit)
		{
			Intent accounts = new Intent(getApplicationContext(), AccountsFragment.class);
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
		
			if(accountID == -1)
				Toast.makeText(getApplicationContext(), "Please Select an account from the sidebar", Toast.LENGTH_LONG).show();
			
			else
				transactionsFragment.addTransaction();

			return super.onOptionsItemSelected(item);
		}

		SpendingReportsFragment spendingFragment = (SpendingReportsFragment) getSupportFragmentManager()
				.findFragmentByTag(SPENDING_TAG);

		if (item.getItemId() == R.id.spending_view_type)
		{

			if (item.isChecked())
			{
				item.setIcon(R.drawable.content_graph);

				spendingFragment.displayView(1);
				item.setChecked(false);
			}
			else
			{
				item.setIcon(R.drawable.content_list);

				spendingFragment.displayView(0);
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

	public String getSpendingReportTag()
	{
		return SPENDING_TAG;
	}

	public void setTransactionTag(String tag)
	{
		TRANSACTIONS_TAG = tag;
	}

	public String getTransactionTag()
	{
		return TRANSACTIONS_TAG;
	}

	/**
	 * @return void we use this method to initialize age
	 */
	private void initializePaging()
	{
		// TODO Auto-generated method stub

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this,
				TransactionsFragment.class.getName()));

		final List<Fragment> finalFragments = fragments;

		// get new fragmentpageradapter from pageadapter
		pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			/**
			 * @return int
			 */
			@Override
			public int getCount()
			{

				// TODO Auto-generated method stub
				return finalFragments.size();
			}

			/**
			 * @param pos
			 * @return fragment
			 * 
			 */
			@Override
			public Fragment getItem(int pos)
			{
				// TODO Auto-generated method stub
				return finalFragments.get(pos); // return final fragments
			}
		};

		viewpager.setAdapter(pageAdapter); // set adapter

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

}
