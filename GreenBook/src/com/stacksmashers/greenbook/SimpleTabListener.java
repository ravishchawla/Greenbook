package com.stacksmashers.greenbook;

import android.app.ActionBar.TabListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

	public class SimpleTabListener<Tab extends Fragment> implements TabListener
	{

		private Fragment fragment;
		private final FragmentActivity activity;
		private final String tag;
		private final Class<Tab> clas;
		private FragmentTransaction fTr;
		private ViewPager pager;
		private int id;
		

		public SimpleTabListener(FragmentActivity activity, String tag, Class<Tab> clas, int id, ViewPager pager)
		{
			this.activity = activity;
			this.tag = tag;
			this.clas = clas;
			this.id = id;
			this.pager = pager;
			
			
			
		}


	/**
	 * called this method when tab is already selected 
	 * @return void
	 * @param tab
	 * @param ft 
	 */
		
		@Override
		public void onTabReselected(android.app.ActionBar.Tab tab,
				android.app.FragmentTransaction ft)
		{
			// TODO Auto-generated method stub
			
		}


		/**
		 * called this method when tab is enters the selected state  
		 * @return void
		 * @param tab
		 * @param ft 
		 */
			
		@Override
		public void onTabSelected(android.app.ActionBar.Tab tab,
				android.app.FragmentTransaction ft)
		{
			// TODO Auto-generated method stub
			
			if (fragment == null)
			{
			
				fTr = activity.getSupportFragmentManager().beginTransaction();
				
				fragment = Fragment.instantiate(activity, clas.getName());
				fTr.add(android.R.id.content, fragment, tag);
				//fTr.add(fragment, tag);
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
		 * called this method when tab exit the state 
		 * @return void
		 * @param tab
		 * @param ft 
		 */
			
		@Override
		public void onTabUnselected(android.app.ActionBar.Tab tab,
				android.app.FragmentTransaction ft)
		{
			// TODO Auto-generated method stub

			fTr = activity.getSupportFragmentManager().beginTransaction();
			
			if (fragment != null)
			{
				fTr.detach(fragment);
				fTr.commit();        
			}
			

			
			
		}

		/*
		 *		 */

	}
