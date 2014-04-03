package com.stacksmashers.greenbook;

import android.app.ActionBar.TabListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

	// TODO: Auto-generated Javadoc
/**
	 * The listener interface for receiving simpleTab events.
	 * The class that is interested in processing a simpleTab
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addSimpleTabListener<code> method. When
	 * the simpleTab event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @param <Tab> the generic type
	 * @see SimpleTabEvent
	 */
	public class SimpleTabListener<Tab extends Fragment> implements TabListener
	{
		/** The fragment. */
		private Fragment fragment;
		
		/** The activity. */
		private final FragmentActivity activity;
		
		/** The tag. */
		private final String tag;
		
		/** The clas. */
		private final Class<Tab> clas;
		
		/** The tr. */
		private FragmentTransaction fTr;
		
		/** The pager. */
		private ViewPager pager;
		
		/** The id. */
		private int id;
	
		/**
		 * Instantiates a new simple tab listener.
		 *
		 * @param activity - the activity the tab belongs to
		 * @param tag - the tag of the fragment
		 * @param clas - the class of the fragment
		 * @param id - the id of the fragment
		 * @param pager - the viewpager used with the tab
		 */
		public SimpleTabListener(FragmentActivity activity, String tag, Class<Tab> clas, int id, ViewPager pager)
		{
			this.activity = activity;
			this.tag = tag;
			this.clas = clas;
			this.id = id;
			this.pager = pager;
		}

	/**
	 * called when the currently selected method is reselected
	 *
	 * @param tab - the tab reselected
	 * @param ft - FragmentTransaction from the App's context
	 * @return void
	 */
		@Override
		public void onTabReselected(android.app.ActionBar.Tab tab,
				android.app.FragmentTransaction ft)
		{	
		}

		/**
		 * called when a deselected tab is selected
		 *
		 * @param tab - the tab selected
		 * @param ft - FragmentTransaction from the App's context
		 * @return void
		 */
		@Override
		public void onTabSelected(android.app.ActionBar.Tab tab,
				android.app.FragmentTransaction ft)
		{	
			if (fragment == null)
			{	
				fTr = activity.getSupportFragmentManager().beginTransaction();	
				fragment = Fragment.instantiate(activity, clas.getName());
				fTr.add(android.R.id.content, fragment, tag);
				fTr.commit();
			}
			else
			{
				fTr = activity.getSupportFragmentManager().beginTransaction();
				if(pager != null)
					pager.setCurrentItem(id);
				activity.invalidateOptionsMenu();
				fTr.attach(fragment);
				fTr.commit();
			}
		}

		/**
		 * called when a selected tab is deselected
		 *
		 * @param tab - the tab deselected
		 * @param ft - fragmentTransaction from the App's context
		 * @return void
		 */
		@Override
		public void onTabUnselected(android.app.ActionBar.Tab tab,
				android.app.FragmentTransaction ft)
		{
			fTr = activity.getSupportFragmentManager().beginTransaction();	
			if (fragment != null)
			{
				fTr.detach(fragment);
				fTr.commit();        
			}
		}
	}
