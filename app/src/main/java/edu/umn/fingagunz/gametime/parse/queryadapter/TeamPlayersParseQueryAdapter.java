package edu.umn.fingagunz.gametime.parse.queryadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import edu.umn.fingagunz.gametime.R;
import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.domain.Team;
import edu.umn.fingagunz.gametime.domain.TeamMember;

/**
 * Created by Jesse on 5/7/2015.
 */
public class TeamPlayersParseQueryAdapter extends ParseQueryAdapter<TeamMember> {

	public TeamPlayersParseQueryAdapter(Context context, final Team team) {
		super(context, new QueryFactory<TeamMember>() {
			@Override
			public ParseQuery<TeamMember> create() {
				ParseQuery<TeamMember> teamMemberQuery = ParseQuery.getQuery(TeamMember.class);
				teamMemberQuery.whereEqualTo("team", team);
				return teamMemberQuery;
			}
		});
	}

	@Override
	public View getItemView(TeamMember teamMember, View v, ViewGroup parent) {
		if (v == null) {
			v = View.inflate(getContext(), R.layout.fragment_team_detail_players_row, null);
		}

		super.getItemView(teamMember, v, parent);

		((TextView)v.findViewById(R.id.player_row_player_name)).setText(teamMember.getPlayer().getName());

		String gender = teamMember.getPlayer().getGender();
		gender = (gender == null) ? "?" : gender;

		ImageView genderImage = (ImageView)v.findViewById(R.id.player_row_gender_icon);
		switch (gender)
		{
			case "M":
				genderImage.setImageResource(R.mipmap.ic_gender_male_black_24dp);
				break;

			case "F":
				genderImage.setImageResource(R.mipmap.ic_gender_female_black_24dp);
				break;

			case "?":
				genderImage.setImageResource(R.mipmap.ic_gender_transgender_black_24dp);
				break;
		}

		return v;
	}
}
