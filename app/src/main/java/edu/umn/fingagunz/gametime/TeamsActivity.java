package edu.umn.fingagunz.gametime;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.ParseObject;

import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.util.CurrentUserUtil;

// This class shows the teams for a selected profile.
public class TeamsActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teams);

		String name = getProfileName();
		if (TextUtils.isEmpty(name)) {
			navigateToProfileActivity();
		} else {
			// This code is taken from here: https://www.parse.com/docs/android_guide#ui-queryadapter
			// It will need to move to handle the case where someone enters their name the first
			// time entering the app.
			final ListActivity activity = this;

			Player player = CurrentUserUtil.getCurrentPlayer(this);
			if (player != null) {
				TeamsParseQueryAdapter adapter = new TeamsParseQueryAdapter(activity, player);
				setListAdapter(adapter);
			}
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		ParseObject team = (ParseObject) v.getTag();
		Intent intent = new Intent(this, TeamDetailActivity.class);
		intent.putExtra("TeamObjectId", team.getObjectId());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_teams, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_profile) {
			navigateToProfileActivity();
			return true;
		} else if (id == R.id.action_add_team) {
			Intent intent = new Intent(this, AddEditTeamActivity.class);
			intent.putExtra("playerId", CurrentUserUtil.getCurrentPlayer(this).getObjectId());
			startActivity(intent);
		} else if (id == R.id.action_upcoming_games) {
			Intent intent = new Intent(this, UpcomingGamesActivity.class);
			intent.putExtra("playerId", CurrentUserUtil.getCurrentPlayer(this).getObjectId());
			startActivity(intent);
		}

		return super.onOptionsItemSelected(item);
	}

	private void navigateToProfileActivity() {
		Intent intent = new Intent(this, ProfileActivity.class);
		intent.putExtra(getString(R.string.profile_name_key), getProfileName());
		startActivity(intent);
	}

	private String getProfileName() {
		SharedPreferences preferences = getSharedPreferences(getString(R.string.preferences_profile), Context.MODE_PRIVATE);
		return preferences.getString(getString(R.string.profile_name_key), "");
	}

	@Override
	protected void onResume() {
		super.onResume();

		setListAdapter(new TeamsParseQueryAdapter(this, CurrentUserUtil.getCurrentPlayer(this)));
	}
}
