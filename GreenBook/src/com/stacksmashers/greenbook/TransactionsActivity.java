package com.stacksmashers.greenbook;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class TransactionsActivity extends BaseActivity implements ActionBar.TabListener
{

	private FragmentPagerAdapter pageAdapter;
	private ViewPager viewpager;
	private AccountsFragment accountsFragment;
	private TransactionsFragment transactionFragment;
	private ActionBar actionBar;
	protected int userID;
	protected int accountID;
	protected String accountName;
    //private TabsPageAdapter tabsPageAdapter;
	protected SimpleDateFormat dateFormat;
	int spendingMode;
	String SPENDING_TAG;
	
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

		setContentView(R.layout.activity_transaction);  // call setcontentview 

		viewpager = (ViewPager) findViewById(R.id.transactoins_viewpager);

	//	initializePaging();

		actionBar = getActionBar();   // get action bar 
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);   
		actionBar.setDisplayShowTitleEnabled(true);  // enabled display show title 

		Bundle extras = getIntent().getExtras(); // get intent bundle extras 

		userID = extras.getInt("User ID");
		accountID = extras.getInt("Account ID");
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());		
		
		Cursor caeser = sqldbase.query(DBHelper.ACCOUNT_TABLE, new String[]{DBHelper.ACCOUNT_NAME}, DBHelper.ACCOUNT_ID + " = '" + accountID + " '", null, null, null, null);
		String titleText = "Transactions";
		if(caeser.getCount() != 0)
		{
			caeser.moveToFirst();
			accountName = caeser.getString(0);
			
			
			
		}
		

				
					
	//	actionBar.addTab(tab);

		/*viewpager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
				{

	  /**
	   * @param position
	   * @return void 
	   * we use this method when new page become selected 
	   */
		/*			@Override
					public void onPageSelected(int position)
					{
						getActionBar().setSelectedNavigationItem(position);

						invalidateOptionsMenu();

					}

				});
		
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
			switch(index)
			{
				case 0:
					return transactionFragment = new TransactionsFragment();
					
				case 1:
					spendingReportsFragment = new SpendingReportsFragment();
					SPENDING_TAG = spendingReportsFragment.getTag();
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
			
			if(position == 1)
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
	actionBar.setHomeButtonEnabled(false);
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	
		
	
	actionBar.addTab(actionBar.newTab().setText("Transactions").setTabListener(this));
	actionBar.addTab(actionBar.newTab().setText("Spending Report").setTabListener(this));
				

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
		if (getActionBar().getSelectedNavigationIndex() == 0)
			getMenuInflater().inflate(R.menu.menu_transactions, menu);

		else
		
			getMenuInflater().inflate(R.menu.menu_spending, menu);

			
			
			
			
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
			
			
			transactionFragment.addTransaction();
		}

		
		
		if(getActionBar().getSelectedNavigationIndex() == 1)
		{
			spendingReportsFragment = (SpendingReportsFragment)getSupportFragmentManager().findFragmentByTag("Spending");
		}
		
		if(item.getItemId() == R.id.spending_list_view)
			spendingReportsFragment.updateData(null, null, 1);
		else if (item.getItemId() == R.id.spending_graph_view)
			spendingReportsFragment.updateData(null, null, 0);
			
		
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
