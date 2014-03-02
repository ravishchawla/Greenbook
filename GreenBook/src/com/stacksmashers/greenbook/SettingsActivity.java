package com.stacksmashers.greenbook;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends BaseActivity
{

	private Button verify_email;
	private int user_id;
	private ListView list;
	private String[] setting_names = {"Verify"};
	private String[] settings_description = {"Verify your email, or resend the notification"};
	private int[] settings_icons = {R.drawable.ic_launcher};
	
	private final static int SETTINGS_VERIFY_EMAIL = 0x0;
	

	public SettingsActivity()
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

		setContentView(R.layout.activity_settings);

		Bundle extras = getIntent().getExtras();
		user_id = extras.getInt("User ID");
		
		list = (ListView)findViewById(R.id.settings_list);
		
		//ArrayList<String> items = (ArrayList<String>) Arrays.asList(setting_names);
		
		final CustomArrayAdapter adapter = new CustomArrayAdapter(this, setting_names);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id)
			{
				// TODO Auto-generated method stub
				
				if(position == SETTINGS_VERIFY_EMAIL)
				{
					verify(user_id);
					
				}
			
					
				
				
				
			}
			
			
			
		});

	
		
		
		}
	
	
	private class CustomArrayAdapter extends ArrayAdapter<String>
	{
		
			
			private final Context context;
			private final String[] values;
			private LayoutInflater inflater;
			
			
			public CustomArrayAdapter(Context context, String[] values)
			{
				super(context, R.layout.nice_list_layout, values);
				
				this.context = context;
				this.values = values;
				inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				
				
				View row = inflater.inflate(R.layout.nice_list_layout, parent, false);
				TextView text = (TextView)row.findViewById(R.id.nice_title);
				TextView description = (TextView)row.findViewById(R.id.nice_description);
				ImageView image = (ImageView)row.findViewById(R.id.icon);
				
				text.setText(setting_names[position]);
				description.setText(settings_description[position]);
				image.setImageResource(settings_icons[position]);
				
				return row;
				
			}
			
			
		
	}
	
	
	public void verify(final int user_id)
	{
		

		Cursor caeser = sqldbase.query(DBHelper.USER_TABLE,
				new String[] { DBHelper.USER_TYPE }, DBHelper.USERS_ID + " = '"
						+ user_id + "'", null, null, null, null);

		caeser.moveToFirst();
		if (caeser.getCount() != 0 && !caeser.getString(0).equals("auth")
				&& !caeser.getString(0).equals("admin"))
		{

			final String code = caeser.getString(0);

			View view = getLayoutInflater().inflate(R.layout.settings_verify,
					null);

			final AlertDialog dialog = new AlertDialog.Builder(this)
					.setTitle(
							"Please enter the code you received in your email")
					.setView(view).setPositiveButton("Resend", new DialogInterface.OnClickListener()
					{
						
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							// TODO Auto-generated method stub
							
							Cursor caeser = sqldbase.query(DBHelper.USER_TABLE, new String[]{DBHelper.USER_NAME, DBHelper.USER_EMAIL},  null, null, null, null, null);
							
							
							if(caeser.getCount() != 0)
							{
								caeser.moveToFirst();
								
								String name = caeser.getString(0);
								String email = caeser.getString(1);
								String code = codeEmail(email);
								
								String message = "Hello " + name + ",\n" +
										"Here's your code!" +
										"\n" +
										"\n" +
										"\n" +
										code +
										"\n" +
										"\n" +
										"--" +
										"The GreenBook Team";
										
										
										
										
								
								Mail mail = new Mail("no.reply.greenbook@gmail.com",
										"hello world");
								mail.setFrom("no.reply.greenbook");
								mail.setTo(email);
								mail.setSubject("GreenBook email verifcation");
								mail.setMessage(message);

								send(mail);
							}
							
							
							
						}
					}).create();

			dialog.show();

			final EditText textView = (EditText) view
					.findViewById(R.id.verify_email);

			textView.addTextChangedListener(new TextWatcher()
			{

				@Override
				public void afterTextChanged(Editable s)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count)
				{
					// TODO Auto-generated method stub

					if (textView.getText().toString().equals(code))
					{
						dialog.dismiss();
						Toast.makeText(getApplicationContext(), "Verified!",
								Toast.LENGTH_LONG).show();
						
						
						ContentValues brutus = new ContentValues();
						brutus.put(DBHelper.USER_TYPE, "auth");
						sqldbase.update(DBHelper.USER_TABLE, brutus, DBHelper.USERS_ID + " = '" + user_id + "'", null);
						
						
					}

				}

			});

		}
		else
			Toast.makeText(getApplicationContext(), "Email Alreaddy Verified", Toast.LENGTH_LONG).show();
		
	}

	

}
