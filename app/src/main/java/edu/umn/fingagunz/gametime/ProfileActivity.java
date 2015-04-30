package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class ProfileActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_accept)
		{
			String name = ((EditText)findViewById(R.id.profile_name_edit)).getText().toString();
			if (name.isEmpty())
			{
				new AlertDialog.Builder(this)
					.setTitle("You must enter a name for this app to actually work!")
					.setPositiveButton("OK", null)
					.show();
			}
			else
			{
				getPreferences(Context.MODE_PRIVATE)
					.edit()
					.putString(getString(R.string.profile_name_key), name)
					.commit();
			}

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
