package com.stacksmashers.greenbook;

import java.util.List;
import java.util.Vector;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class HomeActivity extends BaseActivity
{

	private FragmentPagerAdapter pageAdapter;
	private ViewPager viewpager;
	private AccountsFragment accountsFragment;
	private TransactionsFragment transactionFragment;
	private ActionBar actionBar;
	protected String userType;
	protected String accountsUser;
	protected int userID;

	public HomeActivity()
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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);

		viewpager = (ViewPager) findViewById(R.id.viewpager);

		initializePaging();

		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Bundle extras = getIntent().getExtras();

		userType = extras.getString("Account Type");
		accountsUser = extras.getString("Account User");

		Cursor csr = sqldbase.query(DBHelper.USER_TABLE,
				new String[] { DBHelper.USERS_ID }, DBHelper.USER_EMAIL
						+ " = '" + accountsUser + "'", null, null, null, null);

		if (csr.getCount() != 0)
		{
			csr.moveToFirst();
			userID = csr.getInt(0);
			Log.i("User ID: ", "" + userID);
		}

		Tab tab = actionBar
				.newTab()
				.setText("Accounts")
				.setTabListener(
						new SimpleTabListener<AccountsFragment>(this,
								"Accounts", AccountsFragment.class, 0,
								viewpager));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText("Transactions")
				.setTabListener(
						new SimpleTabListener<TransactionsFragment>(this,
								"Transactions", TransactionsFragment.class, 1,
								viewpager));

		actionBar.addTab(tab);

		viewpager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
				{

					@Override
					public void onPageSelected(int position)
					{
						getActionBar().setSelectedNavigationItem(position);

						invalidateOptionsMenu();

					}

				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		
		// TODO Auto-generated method stub
		Log.i("Debug", "Create");
		if (getActionBar().getSelectedNavigationIndex() == 0)
			getMenuInflater().inflate(R.menu.menu_accounts, menu);

		else
		{
			getMenuInflater().inflate(R.menu.menu_transactions, menu);

			
			
			
			
			//submenu.add("Stranger").setIcon(R.drawable.content_forgot);

		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub

		if (viewpager.getCurrentItem() == 0)
		{

			accountsFragment = (AccountsFragment) getSupportFragmentManager()
					.findFragmentByTag("Accounts");
			accountsFragment.addAccount();

		}
		else
		{
			transactionFragment = (TransactionsFragment) getSupportFragmentManager()
					.findFragmentByTag("Transactions");
			transactionFragment.addTransaction();
		}

		return super.onOptionsItemSelected(item);
	}

	private void initializePaging()
	{
		// TODO Auto-generated method stub

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this,
				AccountsFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,
				TransactionsFragment.class.getName()));

		final List<Fragment> finalFragments = fragments;

		pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{

				// TODO Auto-generated method stub
				return finalFragments.size();
			}

			@Override
			public Fragment getItem(int pos)
			{
				// TODO Auto-generated method stub
				return finalFragments.get(pos);
			}
		};

		viewpager.setAdapter(pageAdapter);

	}

}
