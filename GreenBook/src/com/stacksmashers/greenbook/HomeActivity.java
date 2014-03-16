package com.stacksmashers.greenbook;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
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

// this class make sure everything about homeactivity 
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

	/**
	 * @param savedinstancestate
	 * @param void 
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);    // create savedinstancestate 

		setContentView(R.layout.activity_home); // call activity home from layout 

		viewpager = (ViewPager) findViewById(R.id.viewpager);   

		initializePaging();

		actionBar = getActionBar();    // get action bar 
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		actionBar.setDisplayShowTitleEnabled(true);   //set display show title enabled 

		
		
		Bundle extras = getIntent().getExtras();  // getintent 

		userType = extras.getString("Account Type");      // get string account type 
		accountsUser = extras.getString("Account User");  // get string account user 
		
		
		Cursor csr = sqldbase.query(DBHelper.USER_TABLE,
				new String[] { DBHelper.USERS_ID }, DBHelper.USER_EMAIL
						+ " = '" + accountsUser + "'", null, null, null, null);
		// use sqldbase for query 

		if (csr.getCount() != 0)
		{
			csr.moveToFirst();
			userID = csr.getInt(0);          // get int csr 
			Log.i("User ID: ", "" + userID);
		}

		Tab tab = actionBar      // action bar 
				.newTab()
				.setText("Accounts")
				.setTabListener(         
						new SimpleTabListener<AccountsFragment>(this,
								"Accounts", AccountsFragment.class, 0,
								viewpager));   // set tablistener 
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText("Transactions")
				.setTabListener(
						new SimpleTabListener<TransactionsFragment>(this,
								"Transactions", TransactionsFragment.class, 1,
								viewpager));

		actionBar.addTab(tab);   // add tab for actionbar 

		viewpager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
				{

	 /**
	  * we use this method to select the page
	  * @param position
	  * @return void 
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
 * we can create optionmenu by using this method 
 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		
		// TODO Auto-generated method stub
		Log.i("Debug", "Create");
		if (getActionBar().getSelectedNavigationIndex() == 0)
			// get actionbar and get selected navigation index = 0 
			getMenuInflater().inflate(R.menu.menu_accounts, menu);
           // getmenuinflater 
		else
		{
			getMenuInflater().inflate(R.menu.menu_transactions, menu);

			
			
			
			
			//submenu.add("Stranger").setIcon(R.drawable.content_forgot);

		}

		return super.onCreateOptionsMenu(menu);  // return oncreateoprtion menu 
	}

/**
 * @param item
 * @return boolean
 * we use this method to select the option item 
 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub

		if (viewpager.getCurrentItem() == 0)
		{

		// get supportfragementmanager from accounts fragmemnt 
			accountsFragment = (AccountsFragment) getSupportFragmentManager()
					.findFragmentByTag("Accounts");
			accountsFragment.addAccount();

		}
		else
		{
			//get supportfragmentmanager from transaction fragement 
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
			public int getCount()     // get count 
			{

				// TODO Auto-generated method stub
				return finalFragments.size();  // return final fragment size 
			}

			/**
			 * @param pos
			 * @return fragment 
			 * this method get fragment getitem 
			 */
			@Override
			public Fragment getItem(int pos)
			{
				// TODO Auto-generated method stub
				return finalFragments.get(pos);
			}
		};

		viewpager.setAdapter(pageAdapter);   // setadapter from viewpager 

	}

}
