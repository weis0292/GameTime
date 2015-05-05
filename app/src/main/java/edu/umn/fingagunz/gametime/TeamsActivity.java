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
import android.view.View;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.List;


public class TeamsActivity extends ListActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teams);

		String name = getProfileName();
		if (TextUtils.isEmpty(name))
		{
			navigateToProfileActivity();
		}
		else
		{
			// This code is taken from here: https://www.parse.com/docs/android_guide#ui-queryadapter
			// It will need to move to handle the case where someone enters their name the first
			// time entering the app.
			final ListActivity activity = this;
			ParseQuery<ParseObject> query = new ParseQuery<>("Player");
			query.whereEqualTo("playerName", getProfileName());
			query.findInBackground(new FindCallback<ParseObject>()
			{
				@Override
				public void done(List<ParseObject> list, ParseException e)
				{
					ParseObject first = list.get(0);
					setListAdapter(new TeamsParseQueryAdapter(activity, first));
				}
			});
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
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
		if (id == R.id.action_profile)
		{
			navigateToProfileActivity();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void navigateToProfileActivity()
	{
		Intent intent = new Intent(this, ProfileActivity.class);
		intent.putExtra(getString(R.string.profile_name_key), getProfileName());
		startActivity(intent);
	}

	private String getProfileName()
	{
		SharedPreferences preferences = getSharedPreferences(getString(R.string.preferences_profile), Context.MODE_PRIVATE);
		return preferences.getString(getString(R.string.profile_name_key), "");
	}
}
