package com.stacksmashers.greenbook;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Utility
{

	public Utility()
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

	public static void setListViewHeightBasedOnChildren(ListView listView)
	{
		ListAdapter listAdapter = listView.getAdapter();
		
		if(listAdapter == null)
				return;
		
		int totalHeight = 0;
		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
		
		for(int i = 0; i < listAdapter.getCount(); i++)
		{
			View listItem = listAdapter.getView(i,  null,  listView);
			listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += listItem.getMeasuredHeight();
			
			
		}
		
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() -1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
	
}
