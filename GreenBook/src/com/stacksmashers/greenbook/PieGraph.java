package com.stacksmashers.greenbook;

import java.util.Map;
import java.util.Map.Entry;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.AbstractChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

public class PieGraph extends GraphicalView
{


	
	
	public PieGraph(Context context, AbstractChart chart)
	{
		// TODO Auto-generated constructor stub
		super(context, chart);



	}

	public static GraphicalView getNewInstance(Context contxt)
	{

		
		
		return ChartFactory.getPieChartView(contxt, getDataSet(contxt, Vars.transactionParseMap),
				getRenderer(Vars.transactionParseMap.size()));

		
		
		
	}

	private static DefaultRenderer getRenderer(int length)
	{
		DefaultRenderer renderer = new DefaultRenderer();

		for (int i = 0; i < length; i++)
		{
			SimpleSeriesRenderer simpleRenderer = new SimpleSeriesRenderer();
			simpleRenderer.setColor(Utility.getColor(i));
			Log.i("Pie", "Colorss");
			renderer.addSeriesRenderer(simpleRenderer);

		}

		renderer.setLabelsTextSize(25);
		renderer.setShowLegend(false);
		//renderer.setDisplayValues(true);
		renderer.setZoomEnabled(true);
		renderer.setPanEnabled(true);
		renderer.setLabelsColor(Color.BLACK);

		return renderer;
	}

	private static CategorySeries getDataSet(Context context,
			Map<String, Double> items)
	{
		CategorySeries series = new CategorySeries("Overall Spending");

		
		Log.i("Pie", "" + items.size());

		for (Entry<String, Double> item : items.entrySet())
		{
			
			
			series.add(item.getKey(), 100 * item.getValue()
					/ Vars.transactionTotalSum);
			Log.i("pie: ", "" + 100 * item.getValue() / Vars.transactionTotalSum);

		}
		/*
		 * 
		 * for(int i = 0; i < items.length; i++) series.add(values[i],
		 * items[i]);
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
