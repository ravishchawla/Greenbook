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

/**
 * A class that generates a Pie Graph and returns that as a GraphicalView
 * 
 * @author Ravish Chawla
 */
public class PieGraph extends GraphicalView
{
	/**
	 * Instantiates a new pie graph.
	 * 
	 * @param context
	 *            the context
	 * @param chart
	 *            the chart
	 */
	public PieGraph(Context context, AbstractChart chart)
	{
		super(context, chart);
	}

	/**
	 * Gets a new instance of the Pie Graph, given data
	 * 
	 * @param contxt
	 *            - the current context of the application
	 * @param map
	 *            - a HashMap(Linked), of the values that need to be graphed
	 * @param sum
	 *            - the total sum of all the values
	 * @return GraphicalView - a View that can be placed in a Layout file
	 */
	public static GraphicalView getNewInstance(Context contxt,
			Map<String, Double> map, double sum)
	{

		return ChartFactory.getPieChartView(contxt,
				getDataSet(contxt, map, sum), getRenderer(map.size()));
	}

	/**
	 * A method used by the Pie Graph to render differnet values to differnt
	 * colors, and also set properties of the overall graph
	 * 
	 * @param length
	 *            - the total number of items in the graph
	 * @return DefaultRenderer - a renderer usable by the PieGraph
	 */
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
		renderer.setZoomEnabled(true);
		renderer.setPanEnabled(true);
		renderer.setLabelsColor(Color.BLACK);

		return renderer;
	}

	/**
	 * Returns the DataSet as an object readable by the PieGraph
	 * 
	 * @param context
	 *            - the current context of the application
	 * @param items
	 *            - a map of the items that need to be rendered into the graph
	 * @param sum
	 *            - the total sum of all items
	 * @return CategorySeries - a collection that can be used by the PieGraph as
	 *         data for the graph
	 */
	private static CategorySeries getDataSet(Context context,
			Map<String, Double> items, double sum)
	{
		CategorySeries series = new CategorySeries("Overall Spending");
		Log.i("Pie", "" + items.size());

		for (Entry<String, Double> item : items.entrySet())
		{
			series.add(item.getKey(), 100 * item.getValue() / sum);
			Log.i("pie: ", "" + 100 * item.getValue() / sum);
			Log.i("pie: ", "" + sum);
		}

		return series;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{

	}

}
