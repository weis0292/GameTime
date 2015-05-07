package edu.umn.fingagunz.gametime;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.parse.ParseException;
import com.parse.ParseQuery;

import edu.umn.fingagunz.gametime.domain.Team;


public class TeamDetailActivity
		extends Activity
		implements OnFragmentInteractionListener {

	private Team selectedTeam;


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

		ParseQuery<Team> teamQuery = ParseQuery.getQuery(Team.class);
		Team team = null;
		try {
			team = teamQuery.get(teamObjectId);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		setSelectedTeam(team);
	}

	@Override
	public void onFragmentInteraction(Uri uri) {

	}

	public Team getSelectedTeam() {
		return selectedTeam;
	}

	public void setSelectedTeam(Team team) {
		this.selectedTeam = team;
	}

}
