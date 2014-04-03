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
	/**
	 * Test a duplicate user by calling the method with values that 
	 * alreaddy exist in the database. 
	 * 
	 * If it recognizes that it is duplicate, the method would return 0. 
	 * 
	 */
	public void testDuplicateUser() {
TextView nameField = (TextView)activity.findViewById(R.id.register_name);
		
		
		
		
		if(fragment != null)
		{
			assertTrue("Handle Situation", 
			fragment.handleRegistration(true, "Ravish Chawla", "stack@smashers.com", "ss") == 0);
			
		}
		
	}
	
	/**
	 * Tests that the Dialog shows up when a new user is created. 
	 * It uses the same values used in the previous method. 
	 * But, after that, it checks to see the values of falseDialog and trueDialog. 
	 * falseDialog is when a duplicate user is encountered, and it is created. It should not be null, 
	 * Also, trueDialog would be null because the method would return before it is created. 
	 */
	public void testDialogShowOnDuplicateUser()
	{
		if(fragment!= null)
		{
			fragment.handleRegistration(true, "Ravish Chawla", "stack@smashers.com", "ss");
			assertTrue("dialogs", fragment.falseDialog != null && fragment.trueDialog == null);
		}
		
	}
	
	
	/**
	 * tests a new user by calling the method with values of a new user. 
	 * If it finds out that no user by this username exists, it would return a 1. 
	 */
	public void testNewUser()
	{
		
TextView nameField = (TextView)activity.findViewById(R.id.register_name);
		
		
		
		
		if(fragment != null)
		{
			assertTrue("Handle Situation", 
			fragment.handleRegistration(false, "Ravish Chawla", "stack@smashers.edu", "ss") == 1);
			
		}
		
		
		
	}

	/**
	 * tests that the dialog shows up correctly when a new user is created. 
	 * trueDialog is for new users, and falseDialog is for duplicate ones. 
	 * the method would skip over the enclosed declaration of falseDialog, and hence 
	 * it should be null. 
	 */
	public void testDialogShowOnNewUser()
	{
		if(fragment!= null)
		{
			fragment.handleRegistration(false, "Ravish Chawla", "stack@smashers.com", "ss");
			assertTrue("dialogs", fragment.falseDialog == null && fragment.trueDialog != null);
		}
		
	}
	
	/**
	 * tests that when the app starts, the correct tab is shown. 
	 * It should be 0 by default, and this just checks to see that it is what is shown. 
	 * It doesn't need to call the method. 
	 */
	public void testCorrectTabShow()
	{
		
		assertTrue("Test Tabs", activity.getActionBar().getSelectedNavigationIndex() == 0);
	}
	
	
	/**
	 * 
	 * tests that the tabs switch correctly. when the method is called with values of a new 
	 * user, the tabs are supposed to switch after the values are inserted into the database 
	 * This calls the method, which switches the tabs first
	 * , and after it returns, it checks that it has switched back.  
	 */
	public void testTabSwitching()
	{
		
		if(fragment != null)
		{
			
			activity.getActionBar().setSelectedNavigationItem(1);
			assertTrue("Handle situation", activity.getActionBar().getSelectedNavigationIndex() == 1);
			fragment.handleRegistration(false, "Ravish Chawla", "stack@smashers.com", "ss");
			assertTrue("Handle Situation",
					activity.getActionBar().getSelectedNavigationIndex() == 0);
					
					
		}
		
	}
	
	/**
	 * this method tests what happens when the variable main is null, which would 
	 * mean that the surrounding activity has not inflated correctly (this happens only when 
	 * the method resumes from a paused state)
	 * in this case, the tabs should not switch at all, and the index should stay 1. 
	 */
	public void testMainIsNull()
	{
		
		if(fragment != null)
		{
			fragment.main = null;
			fragment.handleRegistration(false, "Ravish Chawla", "stack@smashers.com", "ss");
			assertTrue("Check null", activity.getActionBar().getSelectedNavigationIndex() == 1);
		}
		
	}
	
	/**
	 * this tests the other side of the previous condition. if main is not null, the 
	 * surrounding activity has inflated correctly. in this case, the method should work properly,
	 * and the tabs should switch correctly and the index should be 0. 
	 */
	
	public void testMainIsNotNull()
	{
		if(fragment != null)
		{
			fragment.handleRegistration(false, "Ravish Chawla", "stack@smashers.com", "ss");
			assertTrue("Check not null", activity.getActionBar().getSelectedNavigationIndex() == 0);
		}
	}

	
	/**
	 * this tests that an image is saved properly. it sets the imageview to an image, 
	 * and checks that the method retuns the correct return code. 
	 */
	
	public void testSavingPicture()
	{
		
		if(fragment != null)
		{
		ImageView registerPic = (ImageView)activity.findViewById(R.id.register_pic);
		
		
		
		
		registerPic.setImageResource(R.drawable.avatar);
		
		assertTrue("Handle Situation", 
				fragment.handleRegistration(true, "Ravish Chawl", "stack@smashers.com", "ss") == 1);
		
		}
		
		
	}
	
	public void testNotSavingPicture()
	{
		
		if(fragment != null)
		{
		ImageView registerPic = (ImageView)activity.findViewById(R.id.register_pic);
		
		
		
		
		registerPic.setImageBitmap(null);
		
		assertTrue("Handle Situation", 
				fragment.handleRegistration(true, "Ravish Chawl", "stack@smashers.com", "ss") == 0);
		
		}
		
		
	}
	
	
	
	/**
	 * this tests that an email is sent correctly. 
	 * man is a references to the Notification Manager returned
	 * when a notification is put on the notification center (which 
	 * only happens when an email is sent out). 
	 * this checks that man is not null, which means that the email
	 * was sent. 
	 */
	public void testEmailSentCorrectly()
	{
		
		if(fragment != null)
		{
			fragment.handleRegistration(true, "Ravish Chawla", "stack@smashers.com", "ss");
			
			assertTrue("Notification Manager notnull", fragment.man != null);
		}
		
	}
	
	/**
	 * this is supposed to test that an email is not sent properly, 
	 * but there is no easy way to test it, because the email is sent out in a 
	 * background thread, and the only way it ever fails is when incorrect 
	 * parameters are passed to it, which just crashes the app. 
	 */
	public void testEmailNotSentCorrectly()
	{
		if(fragment != null)
		{
			assertTrue("Notification Manager notnull", fragment.man == null);
		}
	}
	
	/**
	 * this method just waits for the registerFragent to inflate, 
	 * and then returns it. if not waited, then all values would 
	 * be null and tests would fail. 
	 * @param tag
	 * @param timeout
	 * @return
	 */

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
