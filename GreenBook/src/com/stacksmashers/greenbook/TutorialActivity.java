package com.stacksmashers.greenbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class TutorialActivity extends BaseActivity
{

	int length = 3;
	int pictures[] = new int[length];
	int position = 0;
	ImageView image;
	
	public TutorialActivity()
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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tutorial);

		pictures[0] = R.drawable.avatar;
		pictures[1] = R.drawable.common_signin_btn_icon_dark;
		pictures[2] = R.drawable.common_signin_btn_icon_light;
		
		image = (ImageView) findViewById(R.id.tutorial_image);
		image.setImageResource(pictures[position]);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub

		if (item.getItemId() == R.id.tutorial_left)
		{
			if (position > 0)
				position--;

			
		}

		else if (item.getItemId() == R.id.tutorial_right && (position < length -1))
		{
			
			position++;
			
		}
		
		else if (item.getItemId() == R.id.tutorial_right && (position == length -1))
		{
			
			AlertDialog dialog = 	new AlertDialog.Builder(this).setTitle("Exit Tutorial?").setPositiveButton("Yes", new OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					
					dialog.dismiss();
					finish();
					
				}
			}).setNegativeButton("No", new OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					dialog.dismiss();
					
				}
			}).show();
		
			
		}
		
		
		
		
		
		image.setImageResource(pictures[position]);

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO Auto-generated method stub

		getMenuInflater().inflate(R.menu.menu_tutorial, menu);

		return super.onCreateOptionsMenu(menu);

	}

}
