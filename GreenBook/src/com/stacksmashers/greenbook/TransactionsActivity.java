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

public class TransactionsActivity extends BaseActivity
{

	private FragmentPagerAdapter pageAdapter;
	private ViewPager viewpager;
	private AccountsFragment accountsFragment;
	private TransactionsFragment transactionFragment;
	private ActionBar actionBar;
	protected int userID;
	protected int accountID;
	protected String accountName;
	 
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
/**
 * @param savedinstancestate
 * @return void
 * we call this method to initalize this activity 
 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);  // initalize savedinstancestate 

		setContentView(R.layout.activity_transactions_r);  // call setcontentview 

		viewpager = (ViewPager) findViewById(R.id.transactoins_viewpager);

		initializePaging();

		actionBar = getActionBar();   // get action bar 
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);   
		actionBar.setDisplayShowTitleEnabled(true);  // enabled display show title 

		Bundle extras = getIntent().getExtras(); // get intent bundle extras 

		userID = extras.getInt("User ID");
		accountID = extras.getInt("Account ID");
		
		
		
		Cursor caeser = sqldbase.query(DBHelper.ACCOUNT_TABLE, new String[]{DBHelper.ACCOUNT_NAME}, DBHelper.ACCOUNT_ID + " = '" + accountID + " '", null, null, null, null);
		String titleText = "Transactions";
		if(caeser.getCount() != 0)
		{
			caeser.moveToFirst();
			accountName = caeser.getString(0);
			
			
			
		}
		
				Tab tab = actionBar
				.newTab()
				.setText(accountName)    // set account name 
				.setTabListener(         // tab li
						new SimpleTabListener<TransactionsFragment>(this,
								"Transactions", TransactionsFragment.class, 0,
								viewpager));

		actionBar.addTab(tab);

		viewpager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
				{

	  /**
	   * @param position
	   * @return void 
	   * we use this method when new page become selected 
	   */
					@Override
					public void onPageSelected(int position)
					{
						getActionBar().setSelectedNavigationItem(position);

						invalidateOptionsMenu();

					}

				});

	}

/**
 * @param menu 
 * @return boolean 
 * we use this method to create option menu  
 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		
		// TODO Auto-generated method stub
		Log.i("Debug", "Create");
		//if (getActionBar().getSelectedNavigationIndex() == 0)
			//getMenuInflater().inflate(R.menu.menu_accounts, menu);

	//	else
		//{
			getMenuInflater().inflate(R.menu.menu_transactions, menu);

			
			
			
			
			//submenu.add("Stranger").setIcon(R.drawable.content_forgot);

		//}

		return super.onCreateOptionsMenu(menu);
	}

/**
 * @return boolean
 * @param iten
 * we use this method to select items 
 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub

		if (viewpager.getCurrentItem() == 0)
		{
			transactionFragment = (TransactionsFragment) getSupportFragmentManager()
					.findFragmentByTag("Transactions");
			transactionFragment.addTransaction();
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * @return void
	 * we use this method to initialize age 
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
				return finalFragments.get(pos);  // return final fragments 
			}
		};

		viewpager.setAdapter(pageAdapter);   // set adapter 

	}

}
