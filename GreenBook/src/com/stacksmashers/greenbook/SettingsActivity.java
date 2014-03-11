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

/**
 * 
 * this method make sure about settings activity 
 *
 */

public class SettingsActivity extends BaseActivity
{

	private Button verify_email;
	private int user_id;
	private ListView list;
	private String[] setting_names = {"Verify"};
	private String[] settings_description = {"Verify your email, or resend the notification"};
	private int[] settings_icons = {R.drawable.ic_launcher};
	
	private final static int SETTINGS_VERIFY_EMAIL = 0x0;
	

	// settings activity 
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

	/**
	 * @param savedinstancestate
	 * @return void
	 * this method create savedinstancestate 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_settings);   // setcontentview 

		Bundle extras = getIntent().getExtras();   // getintent from bundle extras 
		user_id = extras.getInt("User ID");
		
		list = (ListView)findViewById(R.id.settings_list);  //find view id 
		
		//ArrayList<String> items = (ArrayList<String>) Arrays.asList(setting_names);
		
		// call new customerarrayadapater 
		final CustomArrayAdapter adapter = new CustomArrayAdapter(this, setting_names);
		list.setAdapter(adapter);

	
		list.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{

			/**
			 * @param parent
			 * @param view
			 * @param position
			 * @param id
			 * @return void
			 * 
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id)
			{
				// TODO Auto-generated method stub
				
				if(position == SETTINGS_VERIFY_EMAIL)
				{
					verify(user_id);       // varify user id 
					
				}
			
					
				
				
				
			}
			
			
			
		});

	
		
		
		}
	

	/**
	 * 
	 * this class make sure about array adapter 
	 *
	 */
	private class CustomArrayAdapter extends ArrayAdapter<String>
	{
		
			
			private final Context context;
			private final String[] values;
			private LayoutInflater inflater;
			
			/**
			 * @param context
			 * @param values
			 * 
			 */
			public CustomArrayAdapter(Context context, String[] values)
			{
				super(context, R.layout.nice_list_layout, values);
				
				this.context = context;
				this.values = values;
				inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					
			}
			
			/**
			 * @param position
			 * @param convertview
			 * @param parent 
			 * @return views
			 * we can view different things from this methid 
			 * 
			 */
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				
				
				View row = inflater.inflate(R.layout.nice_list_layout, parent, false);
				TextView text = (TextView)row.findViewById(R.id.transaction_title);
				TextView description = (TextView)row.findViewById(R.id.transactions_total_balance);
				ImageView image = (ImageView)row.findViewById(R.id.transactions_total_icon);
				
				text.setText(setting_names[position]);
				description.setText(settings_description[position]);
				image.setImageResource(settings_icons[position]);
				
				return row;
				
			}
			
			
		
	}

	/**
	 * @param user_id
	 * @return void 
	 */
	
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
						
						/**
						 * @param dialog
						 * @param which 
						 */
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							// TODO Auto-generated method stub
							
							Cursor caeser = sqldbase.query(DBHelper.USER_TABLE, new String[]{DBHelper.USER_NAME, DBHelper.USER_EMAIL},  null, null, null, null, null);
							
							
							if(caeser.getCount() != 0)
							{
								caeser.moveToFirst();
								
								String name = caeser.getString(0);  // get string name 
								String email = caeser.getString(1); // get string email 
								String code = codeEmail(email);    // get string code 
								
								// string messege 
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
										
										
										
								// get new messege 		
								
								Mail mail = new Mail("no.reply.greenbook@gmail.com",
										"hello world");
								mail.setFrom("no.reply.greenbook");
								mail.setTo(email);    // set to email 
								mail.setSubject("GreenBook email verifcation");  // varify 
								mail.setMessage(message);  // set messege 

								send(mail);
							}
							
							
							
						}
					}).create();     // create 

			dialog.show();

			final EditText textView = (EditText) view
					.findViewById(R.id.verify_email);

			textView.addTextChangedListener(new TextWatcher()
			{

				/**
				 * @param s
				 * @return void
				 */
				@Override
				public void afterTextChanged(Editable s)
				{
					// TODO Auto-generated method stub

				}

				/**
				 * @param s
				 * @param start
				 * @param count
				 * @param after
				 * @reutrn void 
				 * 
				 */
				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after)
				{
					// TODO Auto-generated method stub

				}

				/**
				 * @param s
				 * @param start
				 * @param before
				 * @param count 
				 */
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
						
						
						ContentValues brutus = new ContentValues(); // get new contentvalyes from brutus 
						brutus.put(DBHelper.USER_TYPE, "auth");
						sqldbase.update(DBHelper.USER_TABLE, brutus, DBHelper.USERS_ID + " = '" + user_id + "'", null);
						
						
					}

				}

			});

		}
		else
			Toast.makeText(getApplicationContext(), "Email Alreaddy Verified", Toast.LENGTH_LONG).show();
		// show messege that email is already verified 
	}

	

}
