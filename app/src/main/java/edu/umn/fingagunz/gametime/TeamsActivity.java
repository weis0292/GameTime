package edu.umn.fingagunz.gametime;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;


public class TeamsActivity extends ListActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teams);

		SharedPreferences preferences = getSharedPreferences(getString(R.string.preferences_profile), Context.MODE_PRIVATE);
		String name = preferences.getString(getString(R.string.profile_name_key), "");
		if (TextUtils.isEmpty(name))
		{
			Intent intent = new Intent(this, ProfileActivity.class);
			startActivity(intent);
		}

//		Intent intent = new Intent(this, TeamDetailActivity.class);
//		startActivity(intent);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_teams, menu);
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
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
