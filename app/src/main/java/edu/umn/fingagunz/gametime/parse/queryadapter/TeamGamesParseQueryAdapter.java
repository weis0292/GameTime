package edu.umn.fingagunz.gametime.parse.queryadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import edu.umn.fingagunz.gametime.R;
import edu.umn.fingagunz.gametime.domain.Game;
import edu.umn.fingagunz.gametime.domain.Team;

/**
 * Created by Jesse on 5/7/2015.
 */
public class TeamGamesParseQueryAdapter extends ParseQueryAdapter<Game> {

	public TeamGamesParseQueryAdapter(Context context, final Team team) {
		super(context, new QueryFactory<Game>() {
			@Override
			public ParseQuery<Game> create() {
				ParseQuery<Game> gameQuery = ParseQuery.getQuery(Game.class);
				gameQuery.whereEqualTo("team", team);

				return gameQuery;
			}
		});

		setTextKey("gameLocationDescription");
	}

	@Override
	public View getItemView(Game game, View v, ViewGroup parent) {
		if (v == null) {
			v = View.inflate(getContext(), R.layout.fragment_team_detail_games_row, null);
		}

		super.getItemView(game, v, parent);

		v.setTag(game);
		((TextView) v.findViewById(R.id.team_detail_games_row_location_description)).setText(game.getLocationDescription());

		return v;
	}
}
