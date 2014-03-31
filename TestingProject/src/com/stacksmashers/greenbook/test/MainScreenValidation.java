package com.stacksmashers.greenbook.test;

import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.TextView;

import com.stacksmashers.greenbook.MainActivity;
import com.stacksmashers.greenbook.R;
import com.stacksmashers.greenbook.RegisterFragment;

public class MainScreenValidation extends
		ActivityInstrumentationTestCase2<MainActivity>
{

	MainActivity activity;
	RegisterFragment fragment;
	
	public MainScreenValidation()
	{
		super("com.stacksmashers.greenbook.MainActivity", MainActivity.class);
	}

	protected void setUp() throws Exception
	{
		super.setUp();

		activity = getActivity();
		
		fragment = (RegisterFragment)waitForFragment("Register", 500);
	
	}
	
	public void testDuplicateUser() {
TextView nameField = (TextView)activity.findViewById(R.id.register_name);
		
		
		
		
		if(fragment != null)
		{
			assertTrue("Handle Situation", 
			fragment.handlesituation(true, "Ravish Chawla", "stack@smashers.com", "ss") == 0);
			
		}
		
	}
	
	public void testDialogShowOnDuplicateUser()
	{
		if(fragment!= null)
		{
			fragment.handlesituation(true, "Ravish Chawla", "stack@smashers.com", "ss");
			assertTrue("dialogs", fragment.falseDialog != null && fragment.trueDialog == null);
		}
		
	}
	
	public void testNewUser()
	{
		
TextView nameField = (TextView)activity.findViewById(R.id.register_name);
		
		
		
		
		if(fragment != null)
		{
			assertTrue("Handle Situation", 
			fragment.handlesituation(false, "Ravish Chawla", "stack@smashers.edu", "ss") == 1);
			
		}
		
		
		
	}

	public void testDialogShowOnNewUser()
	{
		if(fragment!= null)
		{
			fragment.handlesituation(false, "Ravish Chawla", "stack@smashers.com", "ss");
			assertTrue("dialogs", fragment.falseDialog == null && fragment.trueDialog != null);
		}
		
	}
	
	
	public void testCorrectTabShow()
	{
		
		assertTrue("Test Tabs", activity.getActionBar().getSelectedNavigationIndex() == 0);
	}
	
	public void testTabSwitching()
	{
		
		if(fragment != null)
		{
			
			activity.getActionBar().setSelectedNavigationItem(1);
			fragment.handlesituation(false, "Ravish Chawla", "stack@smashers.com", "ss");
			assertTrue("Handle Situation",
					activity.getActionBar().getSelectedNavigationIndex() == 0);
					
					
		}
		
	}
	
	
	
	
	
	public void testSavingPicture()
	{
		
		if(fragment != null)
		{
		ImageView registerPic = (ImageView)activity.findViewById(R.id.register_pic);
		
		
		
		
		registerPic.setImageResource(R.drawable.avatar);
		
		assertTrue("Handle Situation", 
				fragment.handlesituation(true, "Ravish Chawl", "stack@smashers.com", "ss") == 1);
		
		}
		
		
	}
	
	
	
	
	public void testEmailSentCorrectly()
	{
		
		if(fragment != null)
		{
			fragment.handlesituation(true, "Ravish Chawla", "stack@smashers.com", "ss");
			
			assertTrue("Notification Manager notnull", fragment.man != null);
		}
		
	}
	

	protected android.support.v4.app.Fragment waitForFragment(String tag, int timeout)
	{
		long time = SystemClock.uptimeMillis()	+ timeout;
		while(SystemClock.uptimeMillis() <= time)
			{
				android.support.v4.app.Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
				
				if(fragment != null)
					return fragment;
			}
		
		return null;
	}
	
	
}
