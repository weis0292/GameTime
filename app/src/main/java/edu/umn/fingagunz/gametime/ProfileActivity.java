package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.util.CurrentUserUtil;


public class ProfileActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		Intent intent = getIntent();
		String name = intent.getStringExtra(getString(R.string.profile_name_key));
		((EditText)findViewById(R.id.profile_name_edit)).setText(name);

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
			final String name = ((EditText)findViewById(R.id.profile_name_edit)).getText().toString();
			if (name.isEmpty())
			{
				new AlertDialog.Builder(this)
					.setTitle("You must enter your name for this app to actually work!")
					.setPositiveButton("OK", null)
					.show();
			}
			else
			{
				getSharedPreferences(getString(R.string.preferences_profile), Context.MODE_PRIVATE)
					.edit()
					.putString(getString(R.string.profile_name_key), name)
					.commit();

				try
				{
					// Save the name to the database.
					ParseQuery<Player> playerQuery = new ParseQuery<>(Player.class);
					playerQuery.whereEqualTo("playerName", name);
					List<Player> players = playerQuery.find();
					if (players.size() == 0)
					{
						Player player = new Player();
						player.setName(name);
						player.save();
					}
				} catch (ParseException e)
				{
					e.printStackTrace();
				}

				CurrentUserUtil.resetCurrentPlayer();

				// Looks like we've gotten a valid name,
				// let's get the heck outta here.
				setResult(RESULT_OK);
				finish();
			}

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
