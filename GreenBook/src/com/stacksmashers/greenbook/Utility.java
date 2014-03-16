package com.stacksmashers.greenbook;

import java.text.SimpleDateFormat;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Utility
{
	private static String[] colors = { "#000000", "#00FF00", "#0000FF",
			"#FF0000", "#01FFFE", "#FFA6FE", "#FFDB66", "#006401", "#010067",
			"#95003A", "#007DB5", "#FF00F6", "#FFEEE8", "#774D00", "#90FB92",
			"#0076FF", "#D5FF00", "#FF937E", "#6A826C", "#FF029D", "#FE8900",
			"#7A4782", "#7E2DD2", "#85A900", "#FF0056", "#A42400", "#00AE7E",
			"#683D3B", "#BDC6FF", "#263400", "#BDD393", "#00B917", "#9E008E",
			"#001544", "#C28C9F", "#FF74A3", "#01D0FF", "#004754", "#E56FFE",
			"#788231", "#0E4CA1", "#91D0CB", "#BE9970", "#968AE8", "#BB8800",
			"#43002C", "#DEFF74", "#00FFC6", "#FFE502", "#620E00", "#008F9C",
			"#98FF52", "#7544B1", "#B500FF", "#00FF78", "#FF6E41", "#005F39",
			"#6B6882", "#5FAD4E", "#A75740", "#A5FFD2", "#FFB167", "#009BFF",
			"#E85EBE" };

	public static SimpleDateFormat dateFormat;
	public static SimpleDateFormat longDateFormat;
	
	
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

	public static int getColor(int position)
	{
		Log.i("Util", "coloring: " + position + " /" + colors[position]);
		return Color.parseColor(colors[position]);
	}

	public static void setListViewHeightBasedOnChildren(ListView listView)
	{
		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null)
			return;

		int totalHeight = 0;
		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
				MeasureSpec.AT_MOST);

		for (int i = 0; i < listAdapter.getCount(); i++)
		{
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += listItem.getMeasuredHeight();

		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

}
