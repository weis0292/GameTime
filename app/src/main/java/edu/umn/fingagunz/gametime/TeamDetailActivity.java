package edu.umn.fingagunz.gametime;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import edu.umn.fingagunz.gametime.domain.Team;
import edu.umn.fingagunz.gametime.parse.queryadapter.TeamsParseQueryAdapter;


public class TeamDetailActivity
		extends Activity
		implements OnFragmentInteractionListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_detail);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);

		ActionBar.Tab gamesTab =
				actionBar
						.newTab()
						.setIcon(R.mipmap.ic_calendar_black_36dp)
						.setTabListener(new TeamDetailTabListener<>(this, "Games", TeamDetailGamesFragment.class));
		actionBar.addTab(gamesTab);

		ActionBar.Tab playersTab =
				actionBar
						.newTab()
						.setIcon(R.mipmap.ic_account_multiple_black_36dp)
						.setTabListener(new TeamDetailTabListener<>(this, "Players", TeamDetailPlayersFragment.class));
		actionBar.addTab(playersTab);

		Intent intent = getIntent();
		String teamObjectId = intent.getStringExtra("TeamObjectId");

		ParseQuery<ParseObject> query = ParseQuery.getQuery(Team.PARSE_OBJECT_NAME);
		ParseObject parseObject = null;
		try {
			 parseObject = query.get(teamObjectId);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Team team = new Team(parseObject);

		team.getTeamName();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_team_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFragmentInteraction(Uri uri) {
	}
}
