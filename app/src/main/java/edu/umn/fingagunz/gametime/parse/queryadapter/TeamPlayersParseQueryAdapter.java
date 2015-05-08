package edu.umn.fingagunz.gametime.parse.queryadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import edu.umn.fingagunz.gametime.R;
import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.domain.Team;

/**
 * Created by Jesse on 5/7/2015.
 */
public class TeamPlayersParseQueryAdapter extends ParseQueryAdapter<Player> {

	public TeamPlayersParseQueryAdapter(Context context, final Team team) {
		super(context, new QueryFactory<Player>() {
			@Override
			public ParseQuery<Player> create() {
				ParseQuery<Player> playerQuery = ParseQuery.getQuery(Player.class);
				playerQuery.whereEqualTo("team", team);

				return playerQuery;
			}
		});
	}

	@Override
	public View getItemView(Player player, View v, ViewGroup parent) {
		if (v == null) {
			v = View.inflate(getContext(), R.layout.fragment_team_detail_players_row, null);
		}

		super.getItemView(player, v, parent);
	}
}
