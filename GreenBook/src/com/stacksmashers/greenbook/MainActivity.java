package com.stacksmashers.greenbook;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * called when the activity first created 
 */
public class MainActivity extends BaseActivity
{
	Button loginButton, registerButton;     // call loginbutton and register button from res 
	
	public final String TAG = "activity_main";
	
	RegisterActivity registerFragment;
	LoginActivity loginFragment;
	/**
	 * @param savedInstanceState
	 * @return void 
	 * call this method to do initial setup  
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)   
	{
		super.onCreate(savedInstanceState);  // create saveinstancestate 
		//setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.setDisplayShowTitleEnabled(true);
		
		Tab tab = actionBar.newTab().setText("Login").setTabListener(new HomeTabListener<LoginActivity>(this, "Login", LoginActivity.class));
		actionBar.addTab(tab);
		
		tab = actionBar.newTab().setText("Register").setTabListener(new HomeTabListener<RegisterActivity>(this, "Register", RegisterActivity.class));
		actionBar.addTab(tab);
		
		
		
		
		//loginButton = (Button)findViewById(R.id.loginButton);       // login button 
		//registerButton = (Button)findViewById(R.id.registerButton); //register button 
	}

	/**
	 * @param menu
	 * @return true or false 
	 * call this method to show the cotextmenu for the menu 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)   
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	/**
	 * @param view 
	 * @return void 
	 * called when view has been clicked. (login)
	 * 
	 */

	public void onClickRegisterActivityButton(View view)
	{
		
		registerFragment = (RegisterActivity)getFragmentManager().findFragmentByTag("Register");
		registerFragment.onClickRegisterActivityButton(view);
		
		
		
	}
	
	public void onClickLoginActivityButton(View view)
	{
		loginFragment = (LoginActivity)getFragmentManager().findFragmentByTag("Login");
		loginFragment.onClickLoginActivityButton(view);
	}
	
	
	public void onClickLoginMainButton(View view)   // to show the contextmenu for the view 
	{

		Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);  // intent login 
		startActivity(loginIntent);                  // login activity
			
	}
	
	/**
	 * @param view
	 * @return void 
	 * called when view has been clicked. (register) 
	 */
	public void onClickRegisterMainButton(View view)   
	{

		Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class); // intent register
		startActivity(registerIntent);                // register activity 
			
	}
	
	private class HomeTabListener<Tab extends Fragment> implements TabListener {
		
		private Fragment fragment;
		private final Activity activity;
		private final String tag;
		private final Class<Tab> clas;
		
		public HomeTabListener(Activity activity, String tag, Class<Tab> clas)
		{
			this.activity = activity;
			this.tag = tag;
			this.clas = clas;
			
		}

		@Override
		public void onTabReselected(android.app.ActionBar.Tab tab,
				FragmentTransaction ft)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(android.app.ActionBar.Tab tab,
				FragmentTransaction ft)
		{
			// TODO Auto-generated method stub
		
			if(fragment == null)
			{
				fragment = Fragment.instantiate(activity, clas.getName());
				ft.add(android.R.id.content, fragment, tag);
			}
			else{
				
				ft.attach(fragment);
			}
			
			
		}

		@Override
		public void onTabUnselected(android.app.ActionBar.Tab tab,
				FragmentTransaction ft)
		{
			// TODO Auto-generated method stub
			
			if(fragment != null)
				ft.detach(fragment);
			
		}
		
		
		
	}
	
	
}
