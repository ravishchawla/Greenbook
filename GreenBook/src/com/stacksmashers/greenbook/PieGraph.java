package com.stacksmashers.greenbook;


import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.AbstractChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter.LengthFilter;
import android.util.Log;


public class PieGraph extends GraphicalView
{

	static int[] colors = {Color.GREEN, Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.LTGRAY, Color.YELLOW, Color.BLACK, Color.DKGRAY};
	
	public PieGraph(Context context, AbstractChart chart)
	{
		// TODO Auto-generated constructor stub
		super(context, chart);
	
	}

	public static GraphicalView getNewInstance(Context contxt, Map<String, Integer> items, int length)
	{
		return ChartFactory.getPieChartView(contxt, getDataSet(contxt, items), getRenderer(length-1));
		
		
		
		
		
		
	}
	
	
	private static DefaultRenderer getRenderer(int length)
	{
		DefaultRenderer renderer = new DefaultRenderer();
		
		for(int i = 0; i < length; i++)
		{
			SimpleSeriesRenderer simpleRenderer = new SimpleSeriesRenderer();
			simpleRenderer.setColor(colors[i]);
			renderer.addSeriesRenderer(simpleRenderer);
			
		}
		
		renderer.setShowLabels(true);
		renderer.setShowLegend(true);
		renderer.setLabelsTextSize(25);
		renderer.setZoomEnabled(false);
		renderer.setPanEnabled(false);
		renderer.setLabelsColor(Color.BLACK);
		
		return renderer;
	}
	
	
	private static CategorySeries getDataSet(Context context, Map<String, Integer> items)
	{
		CategorySeries series = new CategorySeries("Overall Spending");
		
		int totalSum = items.get("-1");
		
		items.remove("-1");
		for(Map.Entry<String, Integer> item : items.entrySet())
		{
			series.add((String)item.getKey(), 100*item.getValue()/(double)totalSum);
			Log.i("pie: ", ""+100*item.getValue()/(double)totalSum);
			
		}
		/*
		
		for(int i = 0; i < items.length; i++)
			series.add(values[i], items[i]);
		*/
		
		return series;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}

